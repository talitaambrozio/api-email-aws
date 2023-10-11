package com.gft.email.controller;

import com.gft.email.model.Email;
import com.gft.email.service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
@AllArgsConstructor
@RequestMapping("api/email")
public class EmailController {

    private EmailService emailService;

    @PostMapping
    public void sendEmail(@RequestBody Email email) throws MessagingException {
        emailService.send(email);
    }
}
