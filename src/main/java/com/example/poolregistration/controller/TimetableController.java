package com.example.poolregistration.controller;

import com.example.poolregistration.exceptions.ClientRegisteredException;
import com.example.poolregistration.exceptions.NoAvailableQuotaException;
import com.example.poolregistration.exceptions.NotFoundException;
import com.example.poolregistration.model.dao.PoolOrder;
import com.example.poolregistration.model.request.CancelRequest;
import com.example.poolregistration.model.request.OrderRequest;
import com.example.poolregistration.model.response.ExceptionResponse;
import com.example.poolregistration.model.response.OrderIdResponse;
import com.example.poolregistration.model.response.OrdersByDateResponse;
import com.example.poolregistration.service.ReserveService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
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

    @GetMapping("/get")
    public List<PoolOrder> getFullOrdersByDate(@RequestParam("date") String date) {
        return reserveService.getFullOrdersByDate(date);
    }

    @GetMapping("/available")
    public List<OrdersByDateResponse> getAvailableOrdersByDate(@RequestParam("date") String date) {
        return reserveService.getAvailableByDate(date);
    }

    @PostMapping("/reserve")
    public OrderIdResponse reserve(@RequestBody OrderRequest orderRequest) {
        return new OrderIdResponse(reserveService.reserve(orderRequest.getClientId(), orderRequest.getDatetime(), orderRequest.getDuration()));
    }

    @PostMapping("/cancel")
    public void cancelOrder(@RequestBody CancelRequest cancelRequest) {
        reserveService.cancel(cancelRequest.getClientId(), cancelRequest.getOrderId());
    }

    @ExceptionHandler({NoAvailableQuotaException.class, DateTimeParseException.class, ClientRegisteredException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleNoAvailableQuotaException(Exception exception) {
        return new ExceptionResponse(exception.getMessage(), LocalDateTime.now().toString());
    }

    @ExceptionHandler({NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleNotFoundException(NotFoundException exception) {
        return new ExceptionResponse(exception.getMessage(), LocalDateTime.now().toString());
    }
}
