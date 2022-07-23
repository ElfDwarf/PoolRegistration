package com.example.poolregistration.service;

import com.example.poolregistration.exceptions.NoAvailableQuotaException;
import com.example.poolregistration.exceptions.NotFoundException;
import com.example.poolregistration.model.constraints.DayConstraints;
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
    private final DayConstraints normalDayConstraints;
    private final DayConstraints holidayConstraints;

    private final DateTimeFormatter timeFormatter;
    private final DateTimeFormatter dateTimeFormatter;

    private final int HOURS_IN_DAY = 24;

    public ReserveService(ClientsRepository clientsRepository, OrdersRepository ordersRepository) {
        this.clientsRepository = clientsRepository;
        this.ordersRepository = ordersRepository;
        normalDayConstraints = new DayConstraints(8, 20, 10);
        holidayConstraints = new DayConstraints(10, 18, 10);
        timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    }

    public List<OrdersByDateResponse> getReservedByDate(String dateString) {
        LocalDate date = LocalDate.parse(dateString);
        List<Integer> reservedByDate = getReservedByDateInner(date);
        DayConstraints constraints = getDayConstraints(date);

        return Streams.mapWithIndex(reservedByDate.stream(),
                        (count, idx) -> new OrdersByDateResponse(LocalTime.of((int) idx, 0).format(timeFormatter), count))
                .filter((order) -> order.getCount() > 0)
                .collect(Collectors.toList());
    }

    public List<OrdersByDateResponse> getAvailableByDate(String dateString) {
        LocalDate date = LocalDate.parse(dateString);
        List<Integer> reservedByDate = getReservedByDateInner(date);
        DayConstraints constraints = getDayConstraints(date);

        return Streams.mapWithIndex(reservedByDate.stream(),
                        (count, idx) -> new OrdersByDateResponse(LocalTime.of((int) idx, 0).format(timeFormatter), constraints.getMaxClientsPerTime() - count))
                .limit(HOURS_IN_DAY - constraints.getEndTime())
                .skip(constraints.getStartTime())
                .filter((order) -> order.getCount() > 0)
                .collect(Collectors.toList());
    }

    private List<Integer> getReservedByDateInner(LocalDate date) {
        List<PoolOrder> ordersByDate = ordersRepository.findAllByReserveDate(date);
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
        LocalDate localDate = localDateTime.toLocalDate();
        LocalTime localTime = localDateTime.toLocalTime();

        if(!checkAvailability(localDate, localTime, duration)) {
            throw new NoAvailableQuotaException("All quota places are reserved");
        }

//        Optional<PoolOrder> clientOrder = orders.stream().filter((poolOrder -> poolOrder.getClient() == client)).findFirst();
//        if (clientOrder.isEmpty()) {
//
//        }
        return null;
    }

    private boolean checkAvailability(LocalDate localDate, LocalTime localTime, int duration) {
        List<PoolOrder> orders = ordersRepository.findAllByReserveDate(localDate);
        List<Integer> reserved = getReservedByDateInner(localDate);
        DayConstraints constraints = getDayConstraints(localDate);
        long countAvailable = reserved.stream()
                .limit(Math.min(localTime.getHour() + duration - 1, constraints.getEndTime()))
                .skip(Math.max(localTime.getHour(), constraints.getStartTime()))
                .filter((reservedCount) -> reservedCount < constraints.getMaxClientsPerTime()).count();
        return  countAvailable == duration;
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

    private DayConstraints getDayConstraints(LocalDate date) {
        return normalDayConstraints;
    }
}
