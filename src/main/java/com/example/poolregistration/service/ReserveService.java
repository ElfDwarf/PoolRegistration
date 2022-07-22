package com.example.poolregistration.service;

import com.example.poolregistration.model.constraints.DayConstraints;
import com.example.poolregistration.model.response.OrdersByDateResponse;
import com.example.poolregistration.repository.ClientsRepository;
import com.example.poolregistration.repository.OrdersRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReserveService {
    private final ClientsRepository clientsRepository;
    private final OrdersRepository ordersRepository;
    private final DayConstraints normalDayConstraints;
    private final DayConstraints holidayConstraints;

    public ReserveService(ClientsRepository clientsRepository, OrdersRepository ordersRepository) {
        this.clientsRepository = clientsRepository;
        this.ordersRepository = ordersRepository;
        normalDayConstraints = new DayConstraints(8, 20, 10);
        holidayConstraints = new DayConstraints(10, 18, 10);
    }

    public List<OrdersByDateResponse> getReservedByDate(String date) {
        return null;
    }

    public List<OrdersByDateResponse> getAvailableByDatge(String date) {
        return null;
    }

    public Long reserve(long clientId, String datetime) {
        return null;
    }

    public Long cancel(long clientId, String orderId) {
        return null;
    }
}
