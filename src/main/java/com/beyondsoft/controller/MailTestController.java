package com.beyondsoft.controller;

import com.beyondsoft.service.MailSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailTestController {
    @Autowired
    private MailSendService mailSendService;
    @GetMapping("/test/mail")
    public String testMail(){
        return "ok";
    }
}
