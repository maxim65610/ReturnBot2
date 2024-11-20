package io.proj3ct.ReturnBot1;


import java.util.HashMap;
import java.util.Map;

/**
 * Класс LogicForChangeDataUsers обрабатывает логику изменения данных пользователей.
 * Хранит состояния пользователей и управляет процессом изменения их данных, таких как имя,
 * фамилия, класс и адрес электронной почты.
 */
public class LogicForChangeDataUsers {
    private Map<Long, String> userStatesForChangeData = new HashMap<>();
    private UsersData usersData = new UsersData();
    private DatabaseConnection databaseConnection = new DatabaseConnection();

    // Дефолтный конструктор
    public LogicForChangeDataUsers() {

    }
    // Конструктор с параметрами для тестирования
    public LogicForChangeDataUsers(Map<Long, String> userStatesForChangeData, UsersData usersData, DatabaseConnection databaseConnection) {
        this.userStatesForChangeData = userStatesForChangeData;
        this.usersData = usersData;
        this.databaseConnection = databaseConnection;
    }

    /**
     * Возвращает текущее состояние пользователя по идентификатору.
     *
     * @param chatID Идентификатор чата пользователя.
     * @return Строка с состоянием пользователя или "0", если состояний нет.
     */
    public String getUserStatesForChangeData(Long chatID) {
        return userStatesForChangeData.getOrDefault(chatID, "0");
    }
    /**
     * Обрабатывает сообщения пользователей и управляет состоянием изменения данных.
     *
     * @param messageText  Текст сообщения пользователя.
     * @param userId      Идентификатор пользователя.
     * @param emailSender  Объект для отправки электронной почты.
     * @return Ответ пользователю в зависимости от текущего состояния.
     */
    public String worksWithChangeData(String messageText, Long userId, EmailSender emailSender) {
        String currentState = userStatesForChangeData.get(userId);
        if ("/userDataChange".equals(messageText)) {
            userStatesForChangeData.put(userId, "awaiting_response");
        } else if ("awaiting_response".equals(currentState)) {
            if (messageText.equals("/userDataChangeName")) {
                userStatesForChangeData.remove(userId);
                userStatesForChangeData.put(userId, "awaiting_name");
                return MessageConstants.ENTER_NAME;
            } else if (messageText.equals("/userDataChangeSurname")) {
                userStatesForChangeData.remove(userId);
                userStatesForChangeData.put(userId, "awaiting_surname");
                return MessageConstants.ENTER_SURNAME;
            } else if (messageText.equals("/userDataChangeClass")) {
                userStatesForChangeData.remove(userId);
                userStatesForChangeData.put(userId, "awaiting_class");
                return MessageConstants.ENTER_CLASS;
            } else if (messageText.equals("/userDataChangeMail")) {
                userStatesForChangeData.remove(userId);
                userStatesForChangeData.put(userId, "awaiting_mail");
                return MessageConstants.ENTER_MAIL;
            }
        } else if ("awaiting_name".equals(currentState)) {
            usersData.changeName(userId, databaseConnection, messageText);
            userStatesForChangeData.remove(userId);
            return MessageConstants.SUCCESSFUL_NAME;
        } else if ("awaiting_surname".equals(currentState)) {
            usersData.changeSurname(userId, databaseConnection, messageText);
            userStatesForChangeData.remove(userId);
            return MessageConstants.SUCCESSFUL_SURNAME;
        } else if ("awaiting_class".equals(currentState)) {
            try {
                int classNumber = Integer.parseInt(messageText);
                if (classNumber <= 11 && classNumber >= 1) {
                    usersData.changeClass(userId, databaseConnection, messageText);
                    userStatesForChangeData.remove(userId);
                    return MessageConstants.SUCCESSFUL_CLASS;
                } else {
                    return MessageConstants.UN_SUCCESSFUL_CLASS;
                }
            } catch (NumberFormatException e) {
                return MessageConstants.UN_SUCCESSFUL_CLASS;
            }
        } else if ("awaiting_mail".equals(currentState)) {
            if (emailSender.isValidEmail(messageText)) {
                usersData.changeMail(userId, databaseConnection, messageText);
                userStatesForChangeData.remove(userId);
                return MessageConstants.SUCCESSFUL_MAIL;
            } else {
                return MessageConstants.NOT_CORRECT_MAIL_COMMAND_RESPONSE;
            }
        }
        return MessageConstants.CHANGEDATA_COMMAND_RESPONSE;
    }
}
