package io.proj3ct.ReturnBot1;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс EmailLogic обрабатывает логику взаимодействия с пользователями по электронной почте.
 * Хранит состояния пользователей и их электронные адреса, а также управляет процессом
 * получения вопросов от пользователей.
 */
public class EmailLogic {
    // Хранит состояния пользователей
    private Map<Long, String> userStatesForMail = new HashMap<>();
    // Хранит электронные адреса пользователей
    private Map<Long, String> userMails = new HashMap<>();
    /**
     * Возвращает текущее состояние пользователя по идентификатору.
     *
     * @param chatID Идентификатор чата пользователя.
     * @return Строка с состоянием пользователя или "0", если состояний нет.
     */
    public String getUserStatesForEmail(Long chatID){
        if (userStatesForMail.isEmpty()) {
            return ("0");
        }
        else {
            return (userStatesForMail.get(chatID));
        }
    }
    /**
     * Возвращает ответ на команду вопроса.
     *
     * @return строка с ответом на команду вопроса.
     */
    private String questionCommandReceived() {
        return CommonMessageConstants.QUESTION_COMMAND_RESPONSE;
    }
    /**
     * Обрабатывает сообщения пользователей и управляет состоянием.
     *
     * @param update Объект, содержащий обновления сообщений.
     * @param messageText Текст сообщения пользователя.
     * @param userId Идентификатор пользователя.
     * @param emailSender Объект для отправки электронной почты.
     * @return Ответ пользователю в зависимости от текущего состояния.
     */
    public String worksWithMail(Update update, String messageText, Long userId, EmailSender emailSender) {
        String currentState = userStatesForMail.get(userId);
        if ("/question".equals(messageText)) {
            userStatesForMail.put(userId, "awaiting_email");
        } else if ("awaiting_email".equals(currentState)) {
            String anwserhandleEmailInput;
            String mailUser = update.getMessage().getText();
            if(emailSender.isValidEmail(mailUser)){
                userMails.put(userId, mailUser);
                userStatesForMail.put(userId, "awaiting_question");
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
            userStatesForMail.remove(userId);
            userMails.remove(userId);
            return "Ваш вопрос отправлен";
        }
        return questionCommandReceived();
    }

}

