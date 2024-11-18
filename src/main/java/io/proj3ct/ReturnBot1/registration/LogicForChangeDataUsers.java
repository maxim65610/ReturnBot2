package io.proj3ct.ReturnBot1.registration;


import io.proj3ct.ReturnBot1.datebase.DatabaseConnection;
import io.proj3ct.ReturnBot1.baseClasses.TextForMessage;
import io.proj3ct.ReturnBot1.mail.EmailSender;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс LogicForChangeDataUsers обрабатывает логику изменения данных пользователей.
 * Хранит состояния пользователей и управляет процессом изменения их данных, таких как имя,
 * фамилия, класс и адрес электронной почты.
 */
public class LogicForChangeDataUsers {
    private final Map<Long, String> userStatesForChangeData = new HashMap<>();
    private UsersData usersData = new UsersData();
    private DatabaseConnection databaseConnection = new DatabaseConnection();
    private TextForMessage textForMessage = new TextForMessage();
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
     * Устанавливает объект UsersData(для тестов).
     *
     * @param usersData объект, отвечающий за управление данными пользователей.
     */
    public void setUsersData(UsersData usersData) {this.usersData = usersData;}
    /**
     * Устанавливает объект DatabaseConnection(для тестов).
     *
     * @param databaseConnection объект, представляющий соединение с базой данных.
     */
    public void setDatabaseConnection(DatabaseConnection databaseConnection) {this.databaseConnection = databaseConnection;}
    /**
     * Устанавливает объект TextForMessage(для тестов).
     *
     * @param textForMessage объект, отвечающий за формирование текстовых сообщений для пользователя.
     */
    public void setTextForMessage(TextForMessage textForMessage) {
        this.textForMessage = textForMessage;
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
        if ("/userdatachange".equals(messageText)) {
            userStatesForChangeData.put(userId, "awaiting_response");
        } else if ("awaiting_response".equals(currentState)) {
            if (messageText.equals("/userDataChangeName")) {
                userStatesForChangeData.remove(userId);
                userStatesForChangeData.put(userId, "awaiting_name");
                return textForMessage.setTheText("name");
            } else if (messageText.equals("/userDataChangeSurname")) {
                userStatesForChangeData.remove(userId);
                userStatesForChangeData.put(userId, "awaiting_surname");
                return textForMessage.setTheText("surname");
            } else if (messageText.equals("/userDataChangeClass")) {
                userStatesForChangeData.remove(userId);
                userStatesForChangeData.put(userId, "awaiting_class");
                return textForMessage.setTheText("class");
            } else if (messageText.equals("/userDataChangeMail")) {
                userStatesForChangeData.remove(userId);
                userStatesForChangeData.put(userId, "awaiting_mail");
                return textForMessage.setTheText("io/proj3ct/ReturnBot1/mail");
            }
        } else if ("awaiting_name".equals(currentState)) {
            usersData.changeName(userId, databaseConnection, messageText);
            userStatesForChangeData.remove(userId);
            return textForMessage.setTheText("successful_name");
        } else if ("awaiting_surname".equals(currentState)) {
            usersData.changeSurname(userId, databaseConnection, messageText);
            userStatesForChangeData.remove(userId);
            return textForMessage.setTheText("successful_surname");
        } else if ("awaiting_class".equals(currentState)) {
            String schoolClass = messageText;
            try {
                int classNumber = Integer.parseInt(schoolClass);
                if (classNumber <= 11 && classNumber >= 1) {
                    usersData.changeClass(userId, databaseConnection, messageText);
                    userStatesForChangeData.remove(userId);
                    return textForMessage.setTheText("successful_class");
                } else {
                    return textForMessage.setTheText("clas_bad");
                }
            } catch (NumberFormatException e) {
                return textForMessage.setTheText("clas_bad");
            }
        } else if ("awaiting_mail".equals(currentState)) {
            String mail = messageText;
            if (emailSender.isValidEmail(mail)) {
                usersData.changeMail(userId, databaseConnection, messageText);
                userStatesForChangeData.remove(userId);
                return textForMessage.setTheText("successful_mail");
            } else {
                return textForMessage.setTheText("notСorrectMail");
            }
        }
        return textForMessage.setTheText("userdatachange");
    }
}
