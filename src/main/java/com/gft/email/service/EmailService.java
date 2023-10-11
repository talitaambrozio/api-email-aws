package com.gft.email.service;

import com.gft.email.model.Email;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;

import javax.mail.MessagingException;

@AllArgsConstructor
@Service
public class EmailService {

    @Bean
    public SesClient sesClient(){
        return SesClient.builder()
                .credentialsProvider(DefaultCredentialsProvider.create())
                .region(Region.US_EAST_1)
                .build();
    }

    public void send(Email email) throws MessagingException {

        Destination destination = Destination.builder()
                .toAddresses(email.getTo())
                .build();

        Content content = Content.builder()
                .data(email.getText())
                .build();

        Content sub = Content.builder()
                .data(email.getSubject())
                .build();

        Body body = Body.builder()
                .html(content)
                .build();

        Message msg = Message.builder()
                .subject(sub)
                .body(body)
                .build();

        SendEmailRequest emailRequest = SendEmailRequest.builder()
                .destination(destination)
                .message(msg)
                .source(email.getFrom())
                .build();

        try {
            System.out.println("Attempting to send an email through Amazon SES " + "using the AWS SDK for Java...");
            sesClient().sendEmail(emailRequest);
            System.out.println(emailRequest.toString());

        } catch (SesException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }
}