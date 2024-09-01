package com.julio.tgid.service;

import com.julio.tgid.DTO.CompanyDTO;
import com.julio.tgid.domain.Company;
import com.julio.tgid.repository.CompanyRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository repository;

    public List<CompanyDTO> findAll(){
        List<Company> companies = repository.findAll();
        return companies.stream().map(company -> new CompanyDTO(company.getCnpj(),company.getName(),company.getSystemFee(),company.getBalance())).toList();
    }
    public CompanyDTO findById(UUID id){
        Company company = repository.findById(id).orElseThrow();
        return new CompanyDTO(company.getCnpj(),company.getName(),company.getSystemFee(),company.getBalance());
    }
    public CompanyDTO findByCnpj(String cnpj){
        Company company = repository.findByCnpj(cnpj).orElseThrow();
        return new CompanyDTO(company.getCnpj(),company.getName(),company.getSystemFee(),company.getBalance());
    }
    @Transactional
    public CompanyDTO saveCompany(CompanyDTO company){
        Optional<Company> comp = repository.findByCnpj(company.cnpj());
        if (comp.isPresent() ) {
            throw new IllegalArgumentException();
        }

        Company c1 = new Company();
        c1.setCnpj(company.cnpj());
        c1.setName(company.name());
        c1.setSystemFee(company.systemFee());
        c1.setBalance(company.balance());
        repository.save(c1);

        return new CompanyDTO(c1.getCnpj(),c1.getName(),c1.getSystemFee(),c1.getBalance());
    }
    @Transactional
    public void deleteCompany (UUID id){
        if (!repository.existsById(id)){
            throw new IllegalArgumentException();
        }
        repository.deleteById(id);
    }
    @Transactional
    public void deleteCompanyByCnpj (String cnpj){
        Company c1 = repository.findByCnpj(cnpj).orElseThrow();
        repository.delete(c1);
    }

}
