package com.hrmtool.service;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.AmazonSimpleEmailServiceException;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.SendTemplatedEmailRequest;
import com.amazonaws.services.simpleemail.model.SendTemplatedEmailResult;
import com.google.gson.JsonObject;
import com.hrmtool.persistance.dto.EmailDetails;
import com.hrmtool.persistance.dto.EmailResponse;
import com.hrmtool.globalHandler.InvalidTokenException;
import com.hrmtool.globalHandler.TemplateException;
import com.hrmtool.utils.AWSErrorCode;
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

    public EmailResponse sendEmail(EmailDetails emailDetails) {

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
            SendTemplatedEmailResult response = simpleEmailService.sendTemplatedEmail(templatedEmailRequest);
            if (response != null && response.getSdkHttpMetadata().getHttpStatusCode() == 200) {
                return EmailResponse.builder().statusCode(response.getSdkHttpMetadata().getHttpStatusCode())
                        .messageId(response.getMessageId())
                        .message("Email sent successfully").build();
            }
        } catch (AmazonSimpleEmailServiceException e) {
           if (e.getStatusCode() == 400 && e.getErrorCode().equals(AWSErrorCode.TEMPLATE_DOES_NOT_EXIST.getErrorCode())) {
                throw new TemplateException(e.getErrorMessage(), e);
            } else if (e.getStatusCode() == 403 && e.getErrorCode().equals(AWSErrorCode.INVALID_SECURITY_TOKEN.getErrorCode())){
               throw new InvalidTokenException(e.getErrorMessage(), e);
           } else
               throw new AmazonSimpleEmailServiceException("Exception raised : " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Exception raised : " + e.getMessage());
        }
        return null;
    }

}
