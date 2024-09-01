package com.julio.tgid.service;

import com.julio.tgid.DTO.CompanyDTO;
import com.julio.tgid.domain.Company;
import com.julio.tgid.exception.AlreadyInUse;
import com.julio.tgid.exception.ObjectNotFound;
import com.julio.tgid.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CompanyServiceTest {

    @InjectMocks
    private CompanyService companyService;

    @Mock
    private CompanyRepository repository;

    private Company company;
    private CompanyDTO companyDTO;
    private UUID companyId;
    private String companyCnpj;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        companyId = UUID.randomUUID();
        companyCnpj = "12345678901234";

        company = new Company();
        company.setCnpj(companyCnpj);
        company.setName("Test Company");
        company.setSystemFee(BigDecimal.valueOf(10.00));
        company.setBalance(BigDecimal.valueOf(500.00));
        company.setId(companyId);

        companyDTO = new CompanyDTO(companyCnpj, "Test Company", company.getSystemFee(), company.getBalance());
    }

    @Test
    void testFindAll() {
        when(repository.findAll()).thenReturn(List.of(company));

        List<CompanyDTO> result = companyService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(companyDTO, result.get(0));
    }

    @Test
    void testFindById() {
        when(repository.findById(companyId)).thenReturn(Optional.of(company));

        CompanyDTO result = companyService.findById(companyId);

        assertNotNull(result);
        assertEquals(companyDTO, result);
    }

    @Test
    void testFindByIdThrowsException() {
        when(repository.findById(companyId)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFound.class, () -> companyService.findById(companyId));
    }

    @Test
    void testFindByCnpj() {
        when(repository.findByCnpj(companyCnpj)).thenReturn(Optional.of(company));

        CompanyDTO result = companyService.findByCnpj(companyCnpj);

        assertNotNull(result);
        assertEquals(companyDTO, result);
    }

    @Test
    void testFindByCnpjThrowsException() {
        when(repository.findByCnpj(companyCnpj)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFound.class, () -> companyService.findByCnpj(companyCnpj));
    }

    @Test
    void testSaveCompany() {
        when(repository.findByCnpj(companyCnpj)).thenReturn(Optional.empty());
        when(repository.save(any(Company.class))).thenReturn(company);

        CompanyDTO result = companyService.saveCompany(companyDTO);

        assertNotNull(result);
        assertEquals(companyDTO, result);
    }

    @Test
    void testSaveCompanyThrowsException() {
        when(repository.findByCnpj(companyCnpj)).thenReturn(Optional.of(company));

        assertThrows(AlreadyInUse.class, () -> companyService.saveCompany(companyDTO));
    }

    @Test
    void testDeleteCompany() {
        when(repository.existsById(companyId)).thenReturn(true);

        companyService.deleteCompany(companyId);

        verify(repository, times(1)).deleteById(companyId);
    }

    @Test
    void testDeleteCompanyThrowsException() {
        when(repository.existsById(companyId)).thenReturn(false);

        assertThrows(ObjectNotFound.class, () -> companyService.deleteCompany(companyId));
    }

    @Test
    void testDeleteCompanyByCnpj() {
        when(repository.findByCnpj(companyCnpj)).thenReturn(Optional.of(company));

        companyService.deleteCompanyByCnpj(companyCnpj);

        verify(repository, times(1)).delete(company);
    }

    @Test
    void testDeleteCompanyByCnpjThrowsException() {
        when(repository.findByCnpj(companyCnpj)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFound.class, () -> companyService.deleteCompanyByCnpj(companyCnpj));
    }
}
