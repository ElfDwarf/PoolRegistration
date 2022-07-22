package com.example.poolregistration.controller;

import com.example.poolregistration.model.dao.PoolClient;
import com.example.poolregistration.service.ClientsService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController(value = "api/v0/pool/client")
public class ClientController {

    private final ClientsService clientsService;

    public ClientController(ClientsService clientsService) {
        this.clientsService = clientsService;
    }

    @GetMapping("all")
    public List<PoolClient> getClients() {
        return clientsService.getClients();
    }


    @GetMapping("get")
    public PoolClient getClient(long id) {
        return clientsService.getClient(id);
    }


    @PostMapping("add")
    @ResponseStatus(HttpStatus.CREATED)
    public PoolClient addClient(@RequestBody PoolClient client) {
        return clientsService.addClient(client);
    }

    @PostMapping("update")
    public PoolClient updateClient(@RequestBody PoolClient client) {
        return clientsService.updateClient(client);
    }
}
