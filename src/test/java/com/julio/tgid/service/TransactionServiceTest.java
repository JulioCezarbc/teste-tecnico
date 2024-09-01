package com.julio.tgid.service;

import com.julio.tgid.DTO.TransactionDTO;
import com.julio.tgid.DTO.TransactionResponseDTO;
import com.julio.tgid.domain.Client;
import com.julio.tgid.domain.Company;
import com.julio.tgid.domain.Transaction;
import com.julio.tgid.domain.enumerated.TransactionType;
import com.julio.tgid.exception.ObjectNotFound;
import com.julio.tgid.repository.ClientRepository;
import com.julio.tgid.repository.CompanyRepository;
import com.julio.tgid.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;
    @Mock
    private TransactionRepository repository;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private CompanyRepository companyRepository;
    @Mock
    private NotificationService notificationService;

    private Client client;
    private Company company;
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        client = new Client();
        client.setCpf("12345678900");
        client.setEmail("client@example.com");
        company = new Company();
        company.setCnpj("12345678000195");
        company.setName("Company Name");
        company.setSystemFee(BigDecimal.valueOf(10.00));
        company.setBalance(BigDecimal.valueOf(500.00));

    }

    @Test
    void testTransactionDepositSuccess(){
        TransactionDTO transactionDTO = new TransactionDTO(client.getCpf(), company.getCnpj(), BigDecimal.valueOf(100.00), TransactionType.DEPOSIT);

        when(clientRepository.findByCpf(client.getCpf())).thenReturn(Optional.of(client));
        when(companyRepository.findByCnpj(company.getCnpj())).thenReturn(Optional.of(company));
        when(repository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TransactionResponseDTO response = transactionService.transaction(transactionDTO);

        assertNotNull(response);
        assertEquals("12345678900", response.cpf());
        assertEquals("12345678000195", response.cnpj());
        assertEquals(BigDecimal.valueOf(100.00), response.amount());
        assertEquals(BigDecimal.valueOf(10.00), response.systemFee());
        assertEquals(BigDecimal.valueOf(90.00), response.finalAmount());
        assertEquals(TransactionType.DEPOSIT, response.type());

        // Verify that notifications were sent
        verify(notificationService, times(1)).notifyCompany(response);
        verify(notificationService, times(1)).notifyClient(response);


    }
    @Test
    void testTransactionWithdrawalSuccess() {
        TransactionDTO transactionDTO = new TransactionDTO(client.getCpf(), company.getCnpj(), BigDecimal.valueOf(100.00), TransactionType.WITHDRAWAL);

        when(clientRepository.findByCpf(client.getCpf())).thenReturn(Optional.of(client));
        when(companyRepository.findByCnpj(company.getCnpj())).thenReturn(Optional.of(company));
        when(repository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TransactionResponseDTO response = transactionService.transaction(transactionDTO);

        assertNotNull(response);
        assertEquals("12345678900", response.cpf());
        assertEquals("12345678000195", response.cnpj());
        assertEquals(BigDecimal.valueOf(100.00), response.amount());
        assertEquals(BigDecimal.valueOf(10.00), response.systemFee());
        assertEquals(BigDecimal.valueOf(90.00), response.finalAmount());
        assertEquals(TransactionType.WITHDRAWAL, response.type());

        verify(notificationService, times(1)).notifyCompany(response);
        verify(notificationService, times(1)).notifyClient(response);
    }

    @Test
    void testTransactionWithdrawalInsufficientBalance() {
        TransactionDTO transactionDTO = new TransactionDTO(client.getCpf(), company.getCnpj(), BigDecimal.valueOf(600.00), TransactionType.WITHDRAWAL);

        when(clientRepository.findByCpf(client.getCpf())).thenReturn(Optional.of(client));
        when(companyRepository.findByCnpj(company.getCnpj())).thenReturn(Optional.of(company));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            transactionService.transaction(transactionDTO);
        });

        assertEquals("Insufficient balance in the company", exception.getMessage());

        verify(notificationService, times(0)).notifyCompany(any());
        verify(notificationService, times(0)).notifyClient(any());
    }

    @Test
    void testClientNotFound() {
        TransactionDTO transactionDTO = new TransactionDTO("invalidCpf", company.getCnpj(), BigDecimal.valueOf(100.00), TransactionType.DEPOSIT);

        when(clientRepository.findByCpf("invalidCpf")).thenReturn(Optional.empty());
        when(companyRepository.findByCnpj(company.getCnpj())).thenReturn(Optional.of(company));

        Exception exception = assertThrows(ObjectNotFound.class, () -> {
            transactionService.transaction(transactionDTO);
        });

        assertEquals("Client not found", exception.getMessage());

        verify(notificationService, times(0)).notifyCompany(any());
        verify(notificationService, times(0)).notifyClient(any());
    }

    @Test
    void testCompanyNotFound() {
        TransactionDTO transactionDTO = new TransactionDTO(client.getCpf(), "invalidCnpj", BigDecimal.valueOf(100.00), TransactionType.DEPOSIT);

        when(clientRepository.findByCpf(client.getCpf())).thenReturn(Optional.of(client));
        when(companyRepository.findByCnpj("invalidCnpj")).thenReturn(Optional.empty());

        Exception exception = assertThrows(ObjectNotFound.class, () -> {
            transactionService.transaction(transactionDTO);
        });

        assertEquals("Company not found", exception.getMessage());

        verify(notificationService, times(0)).notifyCompany(any());
        verify(notificationService, times(0)).notifyClient(any());
    }
}