package com.NWT_KTS_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailingService {

    @Autowired
    private JavaMailSender mailSender;
    

    public void sendAccDeletionMail(String target) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(target);
        email.setSubject("Brisanje naloga uspešno");
        email.setText("Vaš zahtev za brisanje naloga je prihvaćen!\r\nŽao nam je što nam odlazite.\r\nPoz");
        mailSender.send(email);
    }

    public void sendRegRejectMail(String target, String explanation) {

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(target);
        email.setSubject("Obaveštenje o registraciji na Triangular Drive");
        email.setText("Na žalost\r\n, Vaš zahtev za registraciju je odbijen iz navedenih razloga:\r\n" + explanation);
        mailSender.send(email);
    }

    public void sendRegAcceptMail(String target) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(target);
        email.setSubject("Obaveštenje o registraciji na Triangular Drive");
        email.setText("Čestitamo,\r\n Vaš zahtev za registraciju je prihvaćen!\r\nDobro došli u Triangular Drive!!");
        mailSender.send(email);
    }

    public void sendRegAuthMail(String target, String confNum) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(target);
        email.setSubject("Verifikacioni broj za Triangular Drive");
        email.setText("Poštovani,\r\n Vaš verifikacioni broj je " + confNum + "." +
                "\r\nDobro došli u Triangular Drive!");
        mailSender.send(email);
    }

    public void sendComplaintAnswer(String target, String answer) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(target);
        email.setSubject("Obaveštenje o žalbi na Triangular Drive");
        email.setText(answer);
        mailSender.send(email);
    }

    public void sendDeletionNotificationMail(String target) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(target);
        email.setSubject("Obaveštenje - Triangular Drive");
        email.setText("Poštovani,\r\nAdministrator sistema je odlučio da vam obriše acc, cuz duhhh.\r\nCya Never Loserrr,\r\nPoz");
        mailSender.send(email);
    }

    public void sendSucessfulReservation(String target, String mail_body) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(target);
        email.setSubject("Obaveštenje o izvršenoj rezervaciji na Triangular Drive");
        email.setText(mail_body);
        mailSender.send(email);
    }


}
