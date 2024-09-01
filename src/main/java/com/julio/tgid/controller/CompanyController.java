package com.julio.tgid.controller;

import com.julio.tgid.DTO.ClientDTO;
import com.julio.tgid.DTO.CompanyDTO;
import com.julio.tgid.service.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "FindAll", description = "This method fetches a list of all companies")
    @GetMapping
    public ResponseEntity<List<CompanyDTO>> findAll(){
        List<CompanyDTO> companies = service.findAll();
        return ResponseEntity.ok().body(companies);
    }
    @Operation(summary = "FindById", description = "This method fetches a company by the id")
    @GetMapping("/{id}")
    public ResponseEntity<CompanyDTO> findById(@PathVariable UUID id){
        CompanyDTO companies = service.findById(id);
        return ResponseEntity.ok().body(companies);
    }
    @Operation(summary = "FindByCnpj", description = "This method searches for a company by CNPJ")
    @GetMapping("/cnpj")
    public ResponseEntity<CompanyDTO> findByCpf(@RequestParam String cnpj){
        CompanyDTO companies = service.findByCnpj(cnpj);
        return ResponseEntity.ok().body(companies);
    }
    @Operation(summary = "Create a new company", description = "This method creates a new company")
    @PostMapping
    public ResponseEntity<CompanyDTO> saveClient(@RequestBody CompanyDTO company){
        company = service.saveCompany(company);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(company.cnpj()).toUri();
        return ResponseEntity.created(uri).build();
    }
    @Operation(summary = "Delete a company by ID", description = "This method deletes the company identified by the ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable UUID id){
        service.deleteCompany(id);
        return ResponseEntity.noContent().build();
    }
    @Operation(summary = "Delete a company by CNPJ", description = "This method deletes the company identified by the CNPJ")
    @DeleteMapping("/cnpj/{cnpj}")
    public ResponseEntity<Void> deleteByCpf (@PathVariable String cnpj){
        service.deleteCompanyByCnpj(cnpj);
        return ResponseEntity.noContent().build();
    }
}
