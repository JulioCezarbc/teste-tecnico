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

import static java.util.stream.Collectors.toList;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;


    public List<ClientDTO> findAll(){
        List<Client> clients = repository.findAll();
        return clients.stream().map(client -> new ClientDTO(client.getFirstName().concat(client.getLastName()),client.getCpf(),
                client.getEmail(),client.getBalance())).toList();
    }
    public ClientDTO findById(UUID id){
        Client client = repository.findById(id).orElseThrow();
        return new ClientDTO(client.getFirstName().concat(client.getLastName()),client.getCpf(),
                client.getEmail(),client.getBalance());
    }
    public ClientDTO findByCpf(String cpf){
        Client client = repository.findByCpf(cpf).orElseThrow();
        return new ClientDTO(client.getFirstName().concat(client.getLastName()),client.getCpf(),
                client.getEmail(),client.getBalance());
    }
    @Transactional
    public void saveClient(Client client){
        repository.save(client);
    }
    @Transactional
    public void deleteClient (UUID id){
        if (!repository.existsById(id)){
            throw new IllegalArgumentException();
        }
        repository.deleteById(id);
    }

}
