package io.proj3ct.ReturnBot1.mail;

import javax.mail.*;
import javax.mail.internet.*;

import java.util.Properties;
import javax.mail.internet.InternetAddress;
/**
 * Отправляет электронные письма с использованием SMTP.
 * Этот класс предоставляет методы для проверки корректности адреса электронной почты
 * и отправки электронных писем с заданными параметрами.
 */
public class EmailSender {
    private String username;
    private String password;
    /**
     * Конструктор класса EmailSender.
     * @param username Имя пользователя для аутентификации на SMTP сервере.
     * @param password Пароль для аутентификации на SMTP сервере.
     */
    public EmailSender(String username, String password) {
        this.username = username;
        this.password = password;
    }
    /**
     * Проверяет корректность заданного адреса электронной почты.
     * @param email Адрес электронной почты для проверки.
     * @return true, если адрес корректен, иначе false.
     */
    public boolean isValidEmail(String email){
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    /**
     * Получает имя пользователя для аутентификации.
     * @return Имя пользователя.
     */
    public String getUsername(){
        return username;
    }
    /**
     * Отправляет электронное письмо на заданный адрес.
     * @param recipient Адрес электронной почты получателя.
     * @param subject Тема письма.
     * @param body Текст письма.
     */
    public void sendEmail(String recipient, String subject, String body) {
        // Настройки для SMTP сервера
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        // Получение сессии с аутентификацией
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Создание сообщения
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);
            message.setText(body);
            // Отправка сообщения
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}