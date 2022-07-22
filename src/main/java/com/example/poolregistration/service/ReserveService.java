package com.example.poolregistration.service;

import com.example.poolregistration.model.constraints.DayConstraints;
import com.example.poolregistration.model.dao.PoolOrder;
import com.example.poolregistration.model.response.OrdersByDateResponse;
import com.example.poolregistration.repository.ClientsRepository;
import com.example.poolregistration.repository.OrdersRepository;
import com.google.common.collect.Streams;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    private final int HOURS_IN_DAY = 24;

    public ReserveService(ClientsRepository clientsRepository, OrdersRepository ordersRepository) {
        this.clientsRepository = clientsRepository;
        this.ordersRepository = ordersRepository;
        normalDayConstraints = new DayConstraints(8, 20, 10);
        holidayConstraints = new DayConstraints(10, 18, 10);
        timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    }

    public List<OrdersByDateResponse> getReservedByDate(String dateString) {
        List<Integer> reservedByDate = getReservedByDateInner(dateString);
        DayConstraints constraints = getDayConstraints(dateString);

        return Streams.mapWithIndex(reservedByDate.stream(),
                        (count, idx) -> new OrdersByDateResponse(LocalTime.of((int) idx, 0).format(timeFormatter), count))
                .filter((order)->order.getCount() > 0)
                .collect(Collectors.toList());
    }

    public List<OrdersByDateResponse> getAvailableByDate(String dateString) {
        List<Integer> reservedByDate = getReservedByDateInner(dateString);
        DayConstraints constraints = getDayConstraints(dateString);

        return Streams.mapWithIndex(reservedByDate.stream(),
                        (count, idx) -> new OrdersByDateResponse(LocalTime.of((int) idx, 0).format(timeFormatter), constraints.getMaxClientsPerTime() - count))
                .limit(HOURS_IN_DAY - constraints.getEndTime())
                .skip(constraints.getStartTime())
                .filter((order)->order.getCount() > 0)
                .collect(Collectors.toList());
    }

    private List<Integer> getReservedByDateInner(String dateString) {
        LocalDate date = LocalDate.parse(dateString);
        List<PoolOrder> ordersByDate = ordersRepository.findAllByReserveDate(date);
        ArrayList<Integer> result = new ArrayList<>(Collections.nCopies(HOURS_IN_DAY, 0));
        ordersByDate.forEach(order-> {
            int duration = order.getDuration();
            for(int i = 0; i < duration; i++) {
                int curTime = order.getReserveTime().getHour() + i;
                result.set(curTime, result.get(curTime) + 1);
            }
        });
        return result;
    }

    public Long reserve(long clientId, String datetime) {
        return null;
    }

    public Long cancel(long clientId, String orderId) {
        return null;
    }

    private DayConstraints getDayConstraints(String dateString) {
        return normalDayConstraints;
    }
}
