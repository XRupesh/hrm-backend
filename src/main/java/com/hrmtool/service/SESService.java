package com.hrmtool.service;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.SendTemplatedEmailRequest;
import com.google.gson.JsonObject;
import com.hrmtool.dto.EmailDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SESService {

    private final AmazonSimpleEmailService simpleEmailService;

    public String sendEmail(EmailDetails emailDetails) {

        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom("rupeshgaudel3@gmail.com");
            simpleMailMessage.setTo(emailDetails.getToEmail());
            simpleMailMessage.setSubject("Registration Confirmation");
            simpleMailMessage.setText(emailDetails.getBody());

            Destination destination = new Destination();
            List<String> toAddresses = new ArrayList<>();
            String[] email = simpleMailMessage.getTo();
            Collections.addAll(toAddresses,Objects.requireNonNull(email));
            destination.setToAddresses(toAddresses);

            SendTemplatedEmailRequest templatedEmailRequest = new SendTemplatedEmailRequest();
            templatedEmailRequest.withDestination(destination);
            templatedEmailRequest.withTemplate("MyTemp");
            JsonObject templateData = new JsonObject();
            templateData.addProperty("name", emailDetails.getFirstName());
            templatedEmailRequest.withTemplateData(templateData.toString());
            templatedEmailRequest.withSource(simpleMailMessage.getFrom());
            simpleEmailService.sendTemplatedEmail(templatedEmailRequest);
        } catch (Exception e) {
            throw new RuntimeException("Exception: " + e.getMessage());
        }
        return "Email sent successfully";
    }
}
