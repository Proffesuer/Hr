package com.ogoma.hr_core.boundaries.project_management.services;

import com.ogoma.hr_core.boundaries.access_control.entities.User;
import com.ogoma.hr_core.boundaries.project_management.entities.Client;
import com.ogoma.hr_core.boundaries.project_management.models.ClientProjection;
import com.ogoma.hr_core.boundaries.project_management.models.ClientViewModel;
import com.ogoma.hr_core.boundaries.project_management.repositories.ClientsRepository;
import com.ogoma.hr_core.models.requests.PagedDataRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {
    private final ClientsRepository clientsRepository;

    public ClientService(ClientsRepository clientsRepository) {
        this.clientsRepository = clientsRepository;
    }

    public Client createClient(Client client) {
        client = this.clientsRepository.save(client);
        return client;
    }

    public Page<ClientViewModel> getClients(PagedDataRequest pagedDataRequest) {
        Page<ClientViewModel> clients = this.clientsRepository.findAllClients(pagedDataRequest.toPageable());
        return clients;

    }


    public Optional<ClientProjection> getClientById(Long clientId) {
        Optional<ClientProjection> client = this.clientsRepository.findClientById(clientId);
        return client;
    }

    public void removeClient(Long clientId) {
        this.clientsRepository.deleteById(clientId);
    }

    public Optional<Client> updateClient(Long clientId, Client client) {
        Optional<Client> optionalClient =
                this.clientsRepository.findById(clientId);
        optionalClient.ifPresent(c -> {
            c.setCompanyName(client.getCompanyName());
            c.setDescription(client.getDescription());
            User user = c.getUser();
            user.setEmail(client.getUser().getEmail());
            user.setFirstName(client.getUser().getFirstName());
            user.setLastName(client.getUser().getLastName());
            user.setPhone(client.getUser().getPhone());
            this.clientsRepository.save(c);
        });
        return optionalClient;
    }
}
