package com.example.poolregistration.controller;

import com.example.poolregistration.exceptions.NotFoundException;
import com.example.poolregistration.model.dao.PoolClient;
import com.example.poolregistration.model.response.BasicClientDataResponse;
import com.example.poolregistration.service.ClientsService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v0/pool/client")
public class ClientController {

    private final ClientsService clientsService;

    public ClientController(ClientsService clientsService) {
        this.clientsService = clientsService;
    }

    @Operation(summary = "Получение списка зарегистрированных клиентов")
    @GetMapping("/all")
    public List<BasicClientDataResponse> getClients() {
        return clientsService.getClients();
    }


    @Operation(summary = "Получение клиента по id")
    @GetMapping("/get")
    public ResponseEntity<PoolClient> getClient(@RequestParam("id") long id) {
        return ResponseEntity.of(clientsService.getClient(id));
    }
    @Operation(summary = "Поиск клиентов по фио(name) и дате посещения(date)")
    @GetMapping("/search")
    public List<PoolClient> getClient(@RequestParam(name = "name", required = false) String name, @RequestParam(name = "date", required = false) String date) {
        return clientsService.getClient(name, date);
    }

    @Operation(summary = "Добавление нового клиента")
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public PoolClient addClient(@RequestBody PoolClient client) {
        return clientsService.addClient(client);
    }

    @Operation(summary = "Изменение данных для клиента")
    @PostMapping("/update")
    public ResponseEntity<PoolClient> updateClient(@RequestBody PoolClient client) {
        try {
            return ResponseEntity.ok(clientsService.updateClient(client));
        }
        catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
