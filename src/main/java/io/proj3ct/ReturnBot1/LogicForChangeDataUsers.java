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
    private final TextForMessage textForMessage = new TextForMessage();
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
    private String worksWithChangeData(String messageText, Long userId, EmailSender emailSender) {
        String currentState = userStatesForChangeData.get(userId);
        if ("/userDataChange".equals(messageText)) {
            userStatesForChangeData.put(userId, "awaiting_response");
        } else if ("awaiting_response".equals(currentState)) {
            if (messageText.equals("/userDataChangeName")) {
                userStatesForChangeData.remove(userId);
                userStatesForChangeData.put(userId, "awaiting_name");
                return textForMessage.handleMessage("name");
            } else if (messageText.equals("/userDataChangeSurname")) {
                userStatesForChangeData.remove(userId);
                userStatesForChangeData.put(userId, "awaiting_surname");
                return textForMessage.handleMessage("surname");
            } else if (messageText.equals("/userDataChangeClass")) {
                userStatesForChangeData.remove(userId);
                userStatesForChangeData.put(userId, "awaiting_class");
                return textForMessage.handleMessage("class");
            } else if (messageText.equals("/userDataChangeMail")) {
                userStatesForChangeData.remove(userId);
                userStatesForChangeData.put(userId, "awaiting_mail");
                return textForMessage.handleMessage("mail");
            }
        } else if ("awaiting_name".equals(currentState)) {
            usersData.changeName(userId, databaseConnection, messageText);
            userStatesForChangeData.remove(userId);
            return textForMessage.handleMessage("successful_name");
        } else if ("awaiting_surname".equals(currentState)) {
            usersData.changeSurname(userId, databaseConnection, messageText);
            userStatesForChangeData.remove(userId);
            return textForMessage.handleMessage("successful_surname");
        } else if ("awaiting_class".equals(currentState)) {
            String schoolClass = messageText;
            try {
                int classNumber = Integer.parseInt(schoolClass);
                if (classNumber <= 11 && classNumber >= 1) {
                    usersData.changeClass(userId, databaseConnection, messageText);
                    userStatesForChangeData.remove(userId);
                    return textForMessage.handleMessage("successful_class");
                } else {
                    return textForMessage.handleMessage("clas_bad");
                }
            } catch (NumberFormatException e) {
                return textForMessage.handleMessage("clas_bad");
            }
        } else if ("awaiting_mail".equals(currentState)) {
            String mail = messageText;
            if (emailSender.isValidEmail(mail)) {
                usersData.changeMail(userId, databaseConnection, messageText);
                userStatesForChangeData.remove(userId);
                return textForMessage.handleMessage("successful_mail");
            } else {
                return textForMessage.handleMessage("notСorrectMail");
            }
        }
        return textForMessage.handleMessage("userDataChange");
    }
    /**
     *  Вызывает worksWithChangeData
     */
    public String getWorksWithChangeData(String messageText, Long userId, EmailSender emailSender){
        return worksWithChangeData( messageText,  userId,  emailSender);
    }
}
