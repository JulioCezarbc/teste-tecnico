package com.julio.tgid.controller;

import com.julio.tgid.DTO.ClientDTO;
import com.julio.tgid.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/clients")
public class ClientController {
    @Autowired
    private ClientService service;

    @Operation(summary = "FindAll", description = "This method fetches a list of all client")
    @GetMapping
    public ResponseEntity<List<ClientDTO>> findAll(){
        List<ClientDTO> clients = service.findAll();
        return ResponseEntity.ok().body(clients);
    }
    @Operation(summary = "FindById", description = "This method fetches of client by the id")
    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> findById(@PathVariable UUID id){
        ClientDTO client = service.findById(id);
        return ResponseEntity.ok().body(client);
    }
    @Operation(summary = "FindByCpf", description = "This method searches for a client by CPF")
    @GetMapping("/cpf")
    public ResponseEntity<ClientDTO> findByCpf(@RequestParam String cpf){
        ClientDTO client = service.findByCpf(cpf);
        return ResponseEntity.ok().body(client);
    }
    @Operation(summary = "Create a new client ", description = "This method creates a client")
    @PostMapping
    public ResponseEntity<ClientDTO> saveClient(@RequestBody ClientDTO client){
        client = service.saveClient(client);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(client.cpf()).toUri();
        return ResponseEntity.created(uri).build();
    }
    @Operation(summary = "Deletes a client account", description = "This method removes the client identified by the ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable UUID id){
        service.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
    @Operation(summary = "Deletes a client account", description = "This method removes the client identified by the CPF")
    @DeleteMapping("/cpf/{cpf}")
    public ResponseEntity<Void> deleteByCpf (@PathVariable String cpf){
        service.deleteClientByCpf(cpf);
        return ResponseEntity.noContent().build();
    }


}
