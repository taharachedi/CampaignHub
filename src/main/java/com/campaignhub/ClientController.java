
package com.campaignhub;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientRepository repository;

    public ClientController(ClientRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Client> getAllClients() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Client getClient(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                    new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Client introuvable"
                    )
                );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Client createClient(
            @Valid @RequestBody Client client) {
        return repository.save(client);
    }


    @GetMapping("/a-relancer")
    public List<Client> getClientsARelancer() {
    return repository
        .findByActifTrueAndJoursDepuisDernierContactGreaterThanEqual(30);
    }

}
