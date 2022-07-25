package com.example.poolregistration.service;

import com.example.poolregistration.exceptions.NotFoundException;
import com.example.poolregistration.model.dao.PoolClient;
import com.example.poolregistration.model.dao.PoolOrder;
import com.example.poolregistration.model.response.BasicClientDataResponse;
import com.example.poolregistration.repository.ClientsRepository;
import com.example.poolregistration.repository.OrdersRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientsService {

    private final ClientsRepository clientsRepository;

    private final OrdersRepository ordersRepository;

    public ClientsService(ClientsRepository clientsRepository, OrdersRepository ordersRepository) {
        this.clientsRepository = clientsRepository;
        this.ordersRepository = ordersRepository;
    }

    public PoolClient updateClient(PoolClient client) {
        if (clientsRepository.existsById(client.getId()))
            return clientsRepository.save(client);
        else throw new NotFoundException("client with id " + client.getId() + " not found");
    }

    public PoolClient addClient(PoolClient client) {
        return clientsRepository.save(client);
    }

    public Optional<PoolClient> getClient(long id) {
        return clientsRepository.findById(id);
    }

    public List<BasicClientDataResponse> getClients() {
        List<PoolClient> clients = clientsRepository.findAll();
        return clients.stream().map(client -> new BasicClientDataResponse(client.getId(), client.getName())).collect(Collectors.toList());
    }

    public List<PoolClient> getClient(String name, String date) {
        if (date != null) {
            LocalDate localDate = LocalDate.parse(date);
            List<PoolOrder> orders = ordersRepository.findAllByReserveDate(localDate);
            return orders.stream().map(PoolOrder::getClient).distinct()
                    .filter(poolClient -> name == null || name.equals(poolClient.getName())).collect(Collectors.toList());
        }
        if (name != null) {
            return clientsRepository.findAllByName(name);
        }
        return new ArrayList<>();
    }
}
