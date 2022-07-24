package com.example.poolregistration.controller;

import com.example.poolregistration.model.request.CancelRequest;
import com.example.poolregistration.model.request.OrderRequest;
import com.example.poolregistration.model.response.OrdersByDateResponse;
import com.example.poolregistration.service.ReserveService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v0/pool/timetable")
public class TimetableController {

    private final ReserveService reserveService;

    public TimetableController(ReserveService reserveService) {
        this.reserveService = reserveService;
    }

    @GetMapping("/all")
    public List<OrdersByDateResponse> getReservedOrdersByDate(@RequestParam("date") String date) {
        return reserveService.getReservedByDate(date);
    }

    @GetMapping("/available")
    public List<OrdersByDateResponse> getAvailableOrdersByDate(@RequestParam("date") String date) {
        return reserveService.getAvailableByDate(date);
    }

    @PostMapping("/reserve")
    public String reserve(@RequestBody OrderRequest orderRequest) {
        return reserveService.reserve(orderRequest.getClientId(), orderRequest.getDatetime(), orderRequest.getDuration());
    }

    @PostMapping("/cancel")
    public void cancelOrder(@RequestBody CancelRequest cancelRequest) {
        reserveService.cancel(cancelRequest.getClientId(), cancelRequest.getOrderId());
    }
}
