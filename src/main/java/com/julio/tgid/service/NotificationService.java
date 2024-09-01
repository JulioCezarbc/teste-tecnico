package com.julio.tgid.service;

import com.julio.tgid.DTO.TransactionResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private JavaMailSender mailSender;

    public void notifyCompany(TransactionResponseDTO response){
        String callbackURL = "https://webhook.site/08f19a8f-36c4-4435-a70e-95e62a142edf";
        try{
            restTemplate.postForObject(callbackURL,response,String.class);
        }catch (Exception e){
            System.out.println("Offline notification service: " + e.getMessage());
        }
    }
    public void notifyClient(TransactionResponseDTO response){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(response.emailClient());
        mailMessage.setSubject("Success transaction");
        mailMessage.setText(buildEmailContent(response));
        mailSender.send(mailMessage);
    }
    private String buildEmailContent(TransactionResponseDTO responseDTO){
        return "Transaction Details:\n" +
                "Cpf: " + responseDTO.cpf() + "\n" +
                "Email: " + responseDTO.emailClient() + "\n" +
                "Cnpj: " + responseDTO.cnpj() + "\n" +
                "Company: " + responseDTO.nameCompany() + "\n" +
                "Amount: " + responseDTO.amount() + "\n" +
                "System Fee: " + responseDTO.systemFee() + "\n" +
                "Final Amount: " + responseDTO.finalAmount() + "\n" +
                "Transaction Type: " + responseDTO.type() + "\n" +
                "Timestamp: " + responseDTO.timestamp();
    }
}
