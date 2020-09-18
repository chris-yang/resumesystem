package com.beyondsoft.service;

public interface MailSendService {
    void sendMail(String subject,String to,String text);
    void sendMail(String subject,String to,String cc,String text);
}
