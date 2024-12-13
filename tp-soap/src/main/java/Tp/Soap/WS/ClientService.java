package Tp.Soap.WS;
import Tp.Soap.entites.Client;
import Tp.Soap.repositories.ClientRepository;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@WebService
@Component
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;
    @WebMethod
    public Client createClient(Client client) {
        return clientRepository.save(client);
    }
    @WebMethod
    public Client getClientById(Long id) {
        return clientRepository.findById(Math.toIntExact(id))
                .orElseThrow(() -> new EntityNotFoundException("Client not found "));
    }
    @WebMethod
    public List<Client> getAllClients() {
        return (List<Client>) clientRepository.findAll();
    }
    @WebMethod
    public Client updateClient(Long id, Client updatedClient) {
        Client client = getClientById(id);
        client.setLastname(updatedClient.getLastname());
        client.setFirstname(updatedClient.getFirstname());
        client.setEmail(updatedClient.getEmail());
        client.setTele(updatedClient.getTele());
        return clientRepository.save(client);
    }
    @WebMethod
    public void deleteClient(Long id) {
        clientRepository.deleteById(Math.toIntExact(id));
    }
}
