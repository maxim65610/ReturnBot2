package io.proj3ct.ReturnBot1;


import java.util.HashMap;
import java.util.Map;

/**
 * Класс LogicForChangeDataUsers обрабатывает логику изменения данных пользователей.
 * Хранит состояния пользователей и управляет процессом изменения их данных, таких как имя,
 * фамилия, класс и адрес электронной почты.
 */
public class LogicForChangeDataUsers {
    // Хранит состояния пользователей для изменения данных
    private final Map<Long, String> userStatesForChangeData = new HashMap<>();
    private final UsersData usersData = new UsersData();
    private final DatabaseConnection databaseConnection = new DatabaseConnection();
    /**
     * Возвращает текущее состояние пользователя по идентификатору.
     *
     * @param chatID Идентификатор чата пользователя.
     * @return Строка с состоянием пользователя или "0", если состояний нет.
     */
    public String getUserStatesForChangeData(Long chatID) {
        if (userStatesForChangeData.isEmpty()) {
            return ("0");
        } else {
            return (userStatesForChangeData.get(chatID));
        }
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
                return "Пожалуйста, отправьте свое имя";
            } else if (messageText.equals("/userDataChangeSurname")) {
                userStatesForChangeData.remove(userId);
                userStatesForChangeData.put(userId, "awaiting_surname");
                return "Пожалуйста, отправьте свою фамилию";
            } else if (messageText.equals("/userDataChangeClass")) {
                userStatesForChangeData.remove(userId);
                userStatesForChangeData.put(userId, "awaiting_class");
                return "Пожалуйста, отправьте свой класс";
            } else if (messageText.equals("/userDataChangeMail")) {
                userStatesForChangeData.remove(userId);
                userStatesForChangeData.put(userId, "awaiting_mail");
                return "Пожалуйста, отправьте свою почту";
            }
        } else if ("awaiting_name".equals(currentState)) {
            usersData.changeName(userId, databaseConnection, messageText);
            userStatesForChangeData.remove(userId);
            return "Имя успешно изменено";
        } else if ("awaiting_surname".equals(currentState)) {
            usersData.changeSurname(userId, databaseConnection, messageText);
            userStatesForChangeData.remove(userId);
            return "Фамилия успешно изменена";
        } else if ("awaiting_class".equals(currentState)) {
            String schoolClass = messageText;
            try {
                int classNumber = Integer.parseInt(schoolClass);
                if (classNumber <= 11 && classNumber >= 1) {
                    usersData.changeClass(userId, databaseConnection, messageText);
                    userStatesForChangeData.remove(userId);
                    return "Класс успешно изменен";
                } else {
                    return MessageConstants.UN_SUCCESSFUL_CLASS;
                }
            } catch (NumberFormatException e) {
                return MessageConstants.UN_SUCCESSFUL_CLASS;
            }
        } else if ("awaiting_mail".equals(currentState)) {
            String mail = messageText;
            if (emailSender.isValidEmail(mail)) {
                usersData.changeMail(userId, databaseConnection, messageText);
                userStatesForChangeData.remove(userId);
                return "Почта успешно изменена";
            } else {
                return MessageConstants.NOT_CORRECT_MAIL_COMMAND_RESPONSE;
            }
        }
        return MessageConstants.CHANGEDATA_COMMAND_RESPONSE;
    }
}
