package io.proj3ct.ReturnBot1;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

public class EmailLogic {
    // Хранит электронные адреса пользователей
    private  Map<Long, String> userMails = new HashMap<>();
    private String questionCommandReceived() {
        return MessageConstants.QUESTION_COMMAND_RESPONSE;
    }
    public String worksWithMail(Update update, String messageText, Long userId, String currentState, Map<Long, String> userStates, Map<Long, String> userMails, EmailSender emailSender) {

        if ("/question".equals(messageText)) {
            userStates.put(userId, "awaiting_email");;
        } else if ("awaiting_email".equals(currentState)) {
            String anwserhandleEmailInput;
            String mailUser = update.getMessage().getText();
            if(emailSender.isValidEmail(mailUser)){
                userMails.put(userId, mailUser);
                userStates.put(userId, "awaiting_question");
                anwserhandleEmailInput = "Почта указана корректно, напишите ваш вопрос";
            } else {
                userMails.remove(userId, mailUser);
                anwserhandleEmailInput = "Адрес электронной почты был указан неправильно отправьте его ещё раз";
            }
            return anwserhandleEmailInput;

        } else if ("awaiting_question".equals(currentState)) {
            String question = update.getMessage().getText();
            String mailUser = userMails.get(userId);
            emailSender.sendEmail(emailSender.getUsername(), "Вопрос от абитуриента " + mailUser, question);
            userStates.remove(userId);
            userMails.remove(userId);
            return "Ваш вопрос отправлен";
        }
        return questionCommandReceived();
    }
}
