package com.julio.tgid.service;

import com.julio.tgid.DTO.TransactionDTO;
import com.julio.tgid.DTO.TransactionResponseDTO;
import com.julio.tgid.domain.Client;
import com.julio.tgid.domain.Company;
import com.julio.tgid.domain.Transaction;
import com.julio.tgid.domain.enumerated.TransactionType;
import com.julio.tgid.repository.ClientRepository;
import com.julio.tgid.repository.CompanyRepository;
import com.julio.tgid.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository repository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private NotificationService notificationService;

    @Transactional
    public TransactionResponseDTO transaction(TransactionDTO transactionDTO){
        Client client = clientRepository.findByCpf(transactionDTO.cpf()).orElseThrow();
        Company company = companyRepository.findByCnpj(transactionDTO.cnpj()).orElseThrow();

        BigDecimal systemFee = company.getSystemFee();
        BigDecimal finalAmount = transactionDTO.amount().subtract(systemFee);

        if (transactionDTO.type() == TransactionType.WITHDRAWAL && finalAmount.compareTo(company.getBalance()) > 0){
            throw new IllegalArgumentException("Insufficient balance in the company");
        }
        if (transactionDTO.type() == TransactionType.WITHDRAWAL){
            company.setBalance(company.getBalance().subtract(finalAmount));
        } else if (transactionDTO.type() == TransactionType.DEPOSIT) {
            company.setBalance(company.getBalance().add(finalAmount));
        }
        companyRepository.save(company);
        LocalDateTime timestamp = LocalDateTime.now();

        Transaction transaction = new Transaction();
        transaction.setClient(client);
        transaction.setCompany(company);
        transaction.setAmount(transactionDTO.amount());
        transaction.setSystemFee(company.getSystemFee());
        transaction.setFinalAmount(finalAmount);
        transaction.setType(transactionDTO.type());
        transaction.setTimestamp(timestamp);
        repository.save(transaction);

        notificationService.notifyCompany(transform(transaction));
        notificationService.notifyClient(transform(transaction));

        return transform(transaction);
    }

    private TransactionResponseDTO transform(Transaction transaction)  {
        return new TransactionResponseDTO(transaction.getClient().getCpf(),transaction.getClient().getEmail(),transaction.getCompany().getCnpj(),transaction.getCompany().getName(),
                transaction.getAmount(),transaction.getSystemFee(),transaction.getFinalAmount(),transaction.getType(),transaction.getTimestamp());
    }

}
