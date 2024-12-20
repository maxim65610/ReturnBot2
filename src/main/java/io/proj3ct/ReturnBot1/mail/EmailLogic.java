package io.proj3ct.ReturnBot1.mail;

import io.proj3ct.ReturnBot1.baseClasses.MessageConstants;
import io.proj3ct.ReturnBot1.registration.UsersData;
import io.proj3ct.ReturnBot1.datebase.DatabaseConnection;

import java.util.HashMap;
import java.util.Map;

/**
 * Обрабатывает логику взаимодействия с пользователями при работе с /question(функция отправки сообщения
 * от пользователя на почту).
 * Хранит состояния пользователей и их электронные адреса, а также управляет процессом
 * получения вопросов от пользователей.
 */
public class EmailLogic {
    /**
     * Хранит состояния пользователей для отправки электронных писем.
     */
    private final Map<Long, String> userStatesForMail = new HashMap<>();
    /**
     * Хранит электронные адреса пользователей.
     */
    private final Map<Long, String> userMails = new HashMap<>();
    /**
     * Объект для работы с данными пользователей.
     */
    private UsersData usersData;
    /**
     * Дефолтный конструктор, инициализирующий объект UsersData.
     */
    public EmailLogic() {
        this.usersData = new UsersData();
    }
    /**
     * Конструктор с параметрами для тестирования.
     *
     * @param usersData объект для работы с данными пользователей
     */
    public EmailLogic(UsersData usersData) {
        this.usersData = usersData;
    }
    /**
     * Возвращает текущее состояние пользователя по идентификатору.
     *
     * @param chatID Идентификатор чата пользователя.
     * @return Строка с состоянием пользователя или "0", если состояний нет.
     */
    public String getUserStatesForEmail(Long chatID) {
        return userStatesForMail.getOrDefault(chatID, "0");
    }
    /**
     * Обрабатывает сообщения пользователей и управляет состоянием.
     *
     * @param messageText  Текст сообщения пользователя.
     * @param userId      Идентификатор пользователя.
     * @param emailSender  Объект для отправки электронной почты.
     * @return Ответ пользователю в зависимости от текущего состояния.
     */
    public String worksWithMail(String messageText, Long userId, EmailSender emailSender,
                                 DatabaseConnection databaseConnection) {
        String currentState = userStatesForMail.get(userId);
        if ("/question".equals(messageText)) {
            if (!usersData.checkUserIdExistsInRegistrationDataTable(userId, databaseConnection)) {
                return MessageConstants.NOT_AVAILABLE;
            }
            userStatesForMail.put(userId, "awaiting_question");
        } else if ("awaiting_question".equals(currentState)) {
            if (usersData.checkUserIdExistsInRegistrationDataTable(userId,databaseConnection)) {
                String mailUser = usersData.getUserMail(userId, databaseConnection);

            emailSender.sendEmail(emailSender.getUsername(), "Вопрос от абитуриента " + mailUser, messageText);
            userStatesForMail.remove(userId);
            userMails.remove(userId);

            return MessageConstants.MAIL_SEND;
            }
            else{
                return MessageConstants.NO_REGISTRATION;
            }
        }
        return MessageConstants.QUESTION_COMMAND_RESPONSE;
    }
}
