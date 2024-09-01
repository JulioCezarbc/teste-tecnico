package com.julio.tgid.controller;

import com.julio.tgid.DTO.ClientDTO;
import com.julio.tgid.DTO.CompanyDTO;
import com.julio.tgid.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    @Autowired
    private CompanyService service;

    @GetMapping
    public ResponseEntity<List<CompanyDTO>> findAll(){
        List<CompanyDTO> companies = service.findAll();
        return ResponseEntity.ok().body(companies);
    }
    @GetMapping("/{id}")
    public ResponseEntity<CompanyDTO> findById(@PathVariable UUID id){
        CompanyDTO companies = service.findById(id);
        return ResponseEntity.ok().body(companies);
    }
    @GetMapping("/cnpj")
    public ResponseEntity<CompanyDTO> findByCpf(@RequestParam String cnpj){
        CompanyDTO companies = service.findByCnpj(cnpj);
        return ResponseEntity.ok().body(companies);
    }
    @PostMapping
    public ResponseEntity<CompanyDTO> saveClient(@RequestBody CompanyDTO company){
        company = service.saveCompany(company);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(company.cnpj()).toUri();
        return ResponseEntity.created(uri).build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable UUID id){
        service.deleteCompany(id);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/cnpj/{cnpj}")
    public ResponseEntity<Void> deleteByCpf (@PathVariable String cnpj){
        service.deleteCompanyByCnpj(cnpj);
        return ResponseEntity.noContent().build();
    }
}
