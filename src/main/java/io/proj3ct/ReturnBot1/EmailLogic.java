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
    private DatabaseConnection databaseConnection = new DatabaseConnection();
    private UsersData usersData = new UsersData();
    /**
     * Возвращает объект подключения к базе данных.
     *
     * @return Объект DatabaseConnection, используемый для взаимодействия с базой данных.
     */
    public DatabaseConnection getDatabaseConnection() {
        return databaseConnection;
    }
    /**
     * Возвращает текущее состояние пользователя по идентификатору.
     *
     * @param chatID Идентификатор чата пользователя.
     * @return Строка с состоянием пользователя или "0", если состояний нет.
     */
    public String getUserStatesForEmail(Long chatID) {
        if (userStatesForMail.isEmpty()) {
            return ("0");
        } else {
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
     * @param messageText  Текст сообщения пользователя.
     * @param userId      Идентификатор пользователя.
     * @param emailSender  Объект для отправки электронной почты.
     * @param emailLogic   Объект EmailLogic, используемый для управления состоянием.
     * @return Ответ пользователю в зависимости от текущего состояния.
     */
    public String worksWithMail(String messageText, Long userId, EmailSender emailSender, EmailLogic emailLogic) {
        String currentState = userStatesForMail.get(userId);
        if ("/question".equals(messageText)) {
            if (!usersData.checkUserIdExistsInRegistrationDataTable(userId,
                    emailLogic.getDatabaseConnection())) {
                return "Эта функция недоступна, пока вы не зарегистрируетесь";
            }
            userStatesForMail.put(userId, "awaiting_question");
        } else if ("awaiting_question".equals(currentState)) {
            String mailUser = usersData.getUserMail(userId, databaseConnection);
            emailSender.sendEmail(emailSender.getUsername(), "Вопрос от абитуриента " + mailUser, messageText);
            userStatesForMail.remove(userId);
            userMails.remove(userId);
            return "Ваш вопрос отправлен";
        }
        return questionCommandReceived();
    }
}
