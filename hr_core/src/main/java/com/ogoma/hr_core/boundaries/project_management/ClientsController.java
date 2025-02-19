package com.ogoma.hr_core.boundaries.project_management;

import com.ogoma.hr_core.boundaries.project_management.entities.Client;
import com.ogoma.hr_core.boundaries.project_management.models.ClientProjection;
import com.ogoma.hr_core.boundaries.project_management.models.ClientViewModel;
import com.ogoma.hr_core.boundaries.project_management.services.ClientService;
import com.ogoma.hr_core.models.requests.PagedDataRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class ClientsController {
    private final ClientService clientService;

    public ClientsController(ClientService clientService) {
        this.clientService = clientService;
    }

    @RequestMapping(value = "api/clients", method = RequestMethod.GET)
    public ResponseEntity<?> getClients(PagedDataRequest pagedDataRequest) {
        Page<ClientViewModel> clients =
                this.clientService.getClients(pagedDataRequest);
        return ResponseEntity.ok(clients);
    }

    @RequestMapping(value = "api/clients/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getClientsById(@PathVariable("id") Long clientId) {
        Optional<ClientProjection> client =
                this.clientService.getClientById(clientId);
        return ResponseEntity.of(client);
    }

    @RequestMapping(value = "api/clients", method = RequestMethod.POST)
    public ResponseEntity<?> saveClient(@RequestBody @Valid Client client) {
        client = this.clientService.createClient(client);
        return ResponseEntity.ok(client);
    } 
    @RequestMapping(value = "api/clients/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateClient(@PathVariable()Long id,@RequestBody @Valid Client client) {
        Optional<Client> updatedClient = this.clientService.updateClient(id,client);
        return ResponseEntity.of(updatedClient);
    }

    @RequestMapping(value = "api/clients/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeClient(@PathVariable("id") Long clientId) {
        this.clientService.removeClient(clientId);
        return ResponseEntity.ok("Client successfully removed");
    }
}
