package org.micro.notification.controller;

import org.micro.model.Response;
import org.micro.model.UserRegularDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;

@Validated
@RestController
@RequestMapping(value = "/mail", produces = MediaType.APPLICATION_JSON_VALUE)
public class NotificationController {

    public static final String USER_LIST_MESSAGE = "The input user list cannot be empty or more than 100 records.";
    public static final String ADD_RESPONSE = "The emails have been sent successfully.";
    public static final String MAIL_SUBJECT = "Welcome.";
    public static final String MAIL_FROM = "admin@micro.org";
    public static final String MAIL_BODY = "Thank %s for choosing us.";

    private final JavaMailSender javaMailSender;

    @Autowired
    public NotificationController(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @PostMapping
    public ResponseEntity<Response> send(@Size(min = 1, max = 100, message = USER_LIST_MESSAGE)
                                             @RequestBody final List<@Valid UserRegularDTO> users) {
        SimpleMailMessage mailMessage;
        for(UserRegularDTO user: users) {
            mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());
            mailMessage.setFrom(MAIL_FROM);
            mailMessage.setSubject(MAIL_SUBJECT);
            mailMessage.setText(String.format(MAIL_BODY,user.getName()));
            javaMailSender.send(mailMessage);
        }
        return ResponseEntity.ok(new Response(ADD_RESPONSE));
    }

}
