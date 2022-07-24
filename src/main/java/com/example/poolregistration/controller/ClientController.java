package com.example.poolregistration.controller;

import com.example.poolregistration.exceptions.NotFoundException;
import com.example.poolregistration.model.dao.PoolClient;
import com.example.poolregistration.model.response.BasicClientDataResponse;
import com.example.poolregistration.service.ClientsService;
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

    @GetMapping("/all")
    public List<BasicClientDataResponse> getClients() {
        return clientsService.getClients();
    }


    @GetMapping("/get")
    public ResponseEntity<PoolClient> getClient(@RequestParam("id") long id) {
        return ResponseEntity.of(clientsService.getClient(id));
    }

    @GetMapping("/search")
    public List<PoolClient> getClient(@RequestParam(name = "name", required = false) String name, @RequestParam(name = "date", required = false) String date) {
        return clientsService.getClient(name, date);
    }


    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public PoolClient addClient(@RequestBody PoolClient client) {
        return clientsService.addClient(client);
    }

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
