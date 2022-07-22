package com.example.poolregistration.service;

import com.example.poolregistration.exceptions.ClientNotFoundException;
import com.example.poolregistration.model.dao.PoolClient;
import com.example.poolregistration.model.response.BasicClientDataResponse;
import com.example.poolregistration.repository.ClientsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientsService {

    private final ClientsRepository clientsRepository;

    public ClientsService(ClientsRepository clientsRepository) {
        this.clientsRepository = clientsRepository;
    }

    public PoolClient updateClient(PoolClient client) {
        if(clientsRepository.existsById(client.getId()))
            return clientsRepository.save(client);
        else throw new ClientNotFoundException();
    }

    public PoolClient addClient(PoolClient client) {
        return clientsRepository.save(client);
    }

    public Optional<PoolClient> getClient(long id) {
        return clientsRepository.findById(id);
    }

    public List<BasicClientDataResponse> getClients() {
        List<PoolClient> clients = clientsRepository.findAll();
        return clients.stream().map(client->new BasicClientDataResponse(client.getId(), client.getName())).collect(Collectors.toList());
    }
}
