package com.julio.tgid.service;

import com.julio.tgid.DTO.ClientDTO;
import com.julio.tgid.domain.Client;
import com.julio.tgid.repository.ClientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    public List<ClientDTO> findAll(){
        List<Client> clients = repository.findAll();
        return clients.stream().map(client -> new ClientDTO(client.getFirstName(), client.getLastName(), client.getCpf(),
                client.getEmail())).toList();
    }
    public ClientDTO findById(UUID id){
        Client client = repository.findById(id).orElseThrow();
        return new ClientDTO(client.getFirstName(),client.getLastName(),client.getCpf(),
                client.getEmail());
    }
    public ClientDTO findByCpf(String cpf){
        Client client = repository.findByCpf(cpf).orElseThrow();
        return new ClientDTO(client.getFirstName(),client.getLastName(),client.getCpf(),
                client.getEmail());
    }
    @Transactional
    public ClientDTO saveClient(ClientDTO client){
        Optional<Client> user = repository.findByCpf(client.cpf());
        if (user.isPresent() ) {
            throw new IllegalArgumentException();
        }

        Client c1 = new Client();
        c1.setFirstName(client.firstName());
        c1.setLastName(client.lastName());
        c1.setCpf(client.cpf());
        c1.setEmail(client.email());
        repository.save(c1);

        return new ClientDTO(c1.getFirstName(),c1.getLastName(), c1.getCpf(), c1.getEmail());

    }
    @Transactional
    public void deleteClient (UUID id){
        if (!repository.existsById(id)){
            throw new IllegalArgumentException();
        }
        repository.deleteById(id);
    }
    @Transactional
    public void deleteClientByCpf (String cpf){
        Client c1 = repository.findByCpf(cpf).orElseThrow();
        repository.delete(c1);
    }

}
