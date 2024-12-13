package org.example.services;

import jakarta.persistence.EntityNotFoundException;
import org.example.entities.Client;
import org.example.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    public Client csaveClient(Client client) {
        return clientRepository.save(client);
    }

    public Client getClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client not found: " ));
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client updateClient(Long id, Client updatedClient) {
        Client client = getClientById(id);
        client.setLastname(updatedClient.getLastname());
        client.setFirstname(updatedClient.getFirstname());
        client.setEmail(updatedClient.getEmail());
        client.setTele(updatedClient.getTele());
        return clientRepository.save(client);
    }

    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }
}
