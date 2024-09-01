package com.julio.tgid.controller;

import com.julio.tgid.DTO.TransactionDTO;
import com.julio.tgid.DTO.TransactionResponseDTO;
import com.julio.tgid.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private TransactionService service;

    @Operation(summary = "Deposit", description = "This method processes a deposit transaction")
    @PostMapping("/deposit")
    public ResponseEntity<TransactionResponseDTO> deposit(@RequestBody TransactionDTO transaction){
        TransactionResponseDTO response = service.transaction(transaction);
        return ResponseEntity.ok().body(response);
    }
    @Operation(summary = "Withdrawal", description = "This method processes a withdrawal transaction")
    @PostMapping("/withdrawal")
    public ResponseEntity<TransactionResponseDTO> withdrawal(@RequestBody TransactionDTO transaction){
        TransactionResponseDTO response = service.transaction(transaction);
        return ResponseEntity.ok().body(response);
    }
}
