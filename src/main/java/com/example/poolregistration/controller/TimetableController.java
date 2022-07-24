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
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Получение количества забронированных мест по часам на день")
    @GetMapping("/all")
    public List<OrdersByDateResponse> getReservedOrdersByDate(@RequestParam("date") String date) {
        return reserveService.getReservedByDate(date);
    }

    @Operation(summary = "Получение полного списка брони на день")
    @GetMapping("/get")
    public List<PoolOrder> getFullOrdersByDate(@RequestParam("date") String date) {
        return reserveService.getFullOrdersByDate(date);
    }

    @Operation(summary = "Получение списка доступных мест для бронирования")
    @GetMapping("/available")
    public List<OrdersByDateResponse> getAvailableOrdersByDate(@RequestParam("date") String date) {
        return reserveService.getAvailableByDate(date);
    }

    @Operation(summary = "Бронирование места")
    @PostMapping("/reserve")
    public OrderIdResponse reserve(@RequestBody OrderRequest orderRequest) {
        return new OrderIdResponse(reserveService.reserve(orderRequest.getClientId(), orderRequest.getDatetime(), orderRequest.getDuration()));
    }

    @Operation(summary = "Отмена брони для клиента")
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
