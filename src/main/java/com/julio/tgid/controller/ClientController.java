package com.julio.tgid.controller;

import com.julio.tgid.DTO.ClientDTO;
import com.julio.tgid.service.ClientService;
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

    @GetMapping
    public ResponseEntity<List<ClientDTO>> findAll(){
        List<ClientDTO> clients = service.findAll();
        return ResponseEntity.ok().body(clients);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> findById(@PathVariable UUID id){
        ClientDTO client = service.findById(id);
        return ResponseEntity.ok().body(client);
    }
    @GetMapping("/cpf")
    public ResponseEntity<ClientDTO> findByCpf(@RequestParam String cpf){
        ClientDTO client = service.findByCpf(cpf);
        return ResponseEntity.ok().body(client);
    }
    @PostMapping
    public ResponseEntity<ClientDTO> saveClient(@RequestBody ClientDTO client){
        client = service.saveClient(client);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(client.cpf()).toUri();
        return ResponseEntity.created(uri).build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable UUID id){
        service.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/cpf/{cpf}")
    public ResponseEntity<Void> deleteByCpf (@PathVariable String cpf){
        service.deleteClientByCpf(cpf);
        return ResponseEntity.noContent().build();
    }


}
