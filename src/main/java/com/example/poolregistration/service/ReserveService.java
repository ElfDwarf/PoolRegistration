package com.example.poolregistration.service;

import com.example.poolregistration.exceptions.NoAvailableQuotaException;
import com.example.poolregistration.exceptions.NotFoundException;
import com.example.poolregistration.helper.DateHelper;
import com.example.poolregistration.model.constraints.DateConstraints;
import com.example.poolregistration.model.dao.PoolClient;
import com.example.poolregistration.model.dao.PoolOrder;
import com.example.poolregistration.model.response.OrdersByDateResponse;
import com.example.poolregistration.repository.ClientsRepository;
import com.example.poolregistration.repository.OrdersRepository;
import com.google.common.collect.Streams;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReserveService {
    private final ClientsRepository clientsRepository;
    private final OrdersRepository ordersRepository;

    private final DateTimeFormatter timeFormatter;
    private final DateTimeFormatter dateTimeFormatter;

    public ReserveService(ClientsRepository clientsRepository, OrdersRepository ordersRepository) {
        this.clientsRepository = clientsRepository;
        this.ordersRepository = ordersRepository;
        timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    }

    public List<OrdersByDateResponse> getReservedByDate(String dateString) {
        LocalDate date = LocalDate.parse(dateString);
        List<Integer> reservedByDate = getReservedByDateInner(date);

        return Streams.mapWithIndex(reservedByDate.stream(),
                        (count, idx) -> new OrdersByDateResponse(LocalTime.of((int) idx, 0).format(timeFormatter), count))
                .filter((order) -> order.getCount() > 0)
                .collect(Collectors.toList());
    }

    public List<OrdersByDateResponse> getAvailableByDate(String dateString) {
        LocalDate date = LocalDate.parse(dateString);
        if (date.isBefore(LocalDate.now()))
            return new ArrayList<>();

        List<Integer> reservedByDate = getReservedByDateInner(date);
        DateConstraints constraints = getDateConstraints(date);
        int startTime = date.isEqual(LocalDate.now())
                ? Math.max(LocalTime.now().getHour() + 1, constraints.getStartTime())
                : constraints.getStartTime();

        return Streams.mapWithIndex(reservedByDate.stream(),
                        (count, idx) -> new OrdersByDateResponse(LocalTime.of((int) idx, 0).format(timeFormatter), constraints.getMaxClientsPerTime() - count))
                .limit(constraints.getEndTime())
                .skip(startTime)
                .filter((order) -> order.getCount() > 0)
                .collect(Collectors.toList());
    }

    private List<Integer> getReservedByDateInner(LocalDate date) {
        List<PoolOrder> ordersByDate = ordersRepository.findAllByReserveDate(date);
        int HOURS_IN_DAY = 24;
        ArrayList<Integer> result = new ArrayList<>(Collections.nCopies(HOURS_IN_DAY, 0));
        ordersByDate.forEach(order -> {
            int duration = order.getDuration();
            for (int i = 0; i < duration; i++) {
                int curTime = order.getReserveTime().getHour() + i;
                result.set(curTime, result.get(curTime) + 1);
            }
        });
        return result;
    }

    public synchronized String reserve(long clientId, String datetime, int duration) throws NoAvailableQuotaException {
        PoolClient client = getClient(clientId);

        LocalDateTime localDateTime = LocalDateTime.from(dateTimeFormatter.parse(datetime));
        if (localDateTime.isBefore(LocalDateTime.now())) {
            throw new NoAvailableQuotaException("All quota places are reserved");
        }

        LocalDate localDate = localDateTime.toLocalDate();
        LocalTime localTime = localDateTime.toLocalTime();
        if (!checkAvailability(localDate, localTime, duration)) {
            throw new NoAvailableQuotaException("All quota places are reserved");
        }

        List<PoolOrder> orders = ordersRepository.findAllByReserveDate(localDate);

        Optional<PoolOrder> optionalClientOrder = orders.stream().filter((poolOrder -> poolOrder.getClient() == client)).findFirst();
        PoolOrder order = new PoolOrder(client, localDate, localTime, duration);
        if (optionalClientOrder.isEmpty()) {
            ordersRepository.save(order);
            return order.getId();
        }

        PoolOrder clientOrder = optionalClientOrder.get();
        int orderTime = localTime.getHour();
        int clientTime = clientOrder.getReserveTime().getHour();
        int clientDuration = clientOrder.getDuration();

        if (clientTime == orderTime + duration) {
            clientOrder.setDuration(clientDuration + duration);
            clientOrder.setReserveTime(clientOrder.getReserveTime().minusHours(duration));
            ordersRepository.save(clientOrder);
            return clientOrder.getId();
        }
        if (clientTime == orderTime - clientDuration) {
            clientOrder.setDuration(clientDuration + duration);
            ordersRepository.save(clientOrder);
            return clientOrder.getId();
        }
        throw new RuntimeException("Client with id " + clientOrder.getClient().getId() + " already registered");
    }

    private boolean checkAvailability(LocalDate localDate, LocalTime localTime, int duration) {
        List<Integer> reserved = getReservedByDateInner(localDate);
        DateConstraints constraints = getDateConstraints(localDate);
        long countAvailable = reserved.stream()
                .limit(Math.min(localTime.getHour() + duration, constraints.getEndTime()))
                .skip(Math.max(localTime.getHour(), constraints.getStartTime()))
                .filter((reservedCount) -> reservedCount < constraints.getMaxClientsPerTime()).count();
        return countAvailable == duration;
    }

    private PoolClient getClient(long clientId) throws NotFoundException {
        return clientsRepository.findById(clientId)
                .orElseThrow(() -> new NotFoundException("Client with id " + clientId + " not found"));
    }

    public void cancel(long clientId, String orderId) throws NotFoundException {
        Optional<PoolOrder> order = ordersRepository.findById(orderId);
        if (order.orElseThrow(() -> new NotFoundException("Order with id " + orderId + " not found"))
                .getClient().getId() == clientId) {
            ordersRepository.delete(order.get());
        } else throw new NotFoundException("Client with id " + clientId + " have no provided order");
    }

    private DateConstraints getDateConstraints(LocalDate date) {
        return DateHelper.getConstraints(date);
    }

    public List<PoolOrder> getFullOrdersByDate(String date) {
        return ordersRepository.findAllByReserveDate(LocalDate.parse(date));
    }
}
