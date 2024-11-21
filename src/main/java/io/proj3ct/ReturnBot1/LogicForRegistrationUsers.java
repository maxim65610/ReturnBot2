package io.proj3ct.ReturnBot1;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс для обработки логики пользователей при регистрации.
 */
public class LogicForRegistrationUsers {
    private DataUsersForRegistration dataUsersForRegistration = new DataUsersForRegistration();
    private final Map<Long, String> userStatesForRegistration = new HashMap<>();
    private UsersData usersData = new UsersData();
    private DatabaseConnection databaseConnection = new DatabaseConnection();
    private DatebaseTables datebaseTables = new DatebaseTables(databaseConnection);
    /**
     * Дефолтный конструктор
     */
    public LogicForRegistrationUsers() {
    }
    /**
     * Конструктор с параметрами для тестирования
     */
    public LogicForRegistrationUsers(UsersData usersData, DatabaseConnection databaseConnection,
                                     DataUsersForRegistration dataUsersForRegistration) {
        this.usersData = usersData;
        this.databaseConnection = databaseConnection;
        this.datebaseTables = new DatebaseTables(databaseConnection);
        this.dataUsersForRegistration = dataUsersForRegistration;
    }
    /**
     * Получает объект подключения к базе данных.
     * @return объект DatabaseConnection
     */
    public DatabaseConnection getDatabaseConnection() {
        return databaseConnection;
    }
    public String getUserStatesForRegistration(Long chatID) {
        return userStatesForRegistration.getOrDefault(chatID, "0");
    }
    private String checkValidClassUser(Long userId, String messageText){
        try {
            int classNumber = Integer.parseInt(messageText);
            if (classNumber <= 11 && classNumber >= 1) {
                dataUsersForRegistration.setSchoolClassUser(userId, messageText);
                userStatesForRegistration.remove(userId);
                userStatesForRegistration.put(userId, "awaiting_mailUser ");
                return MessageConstants.ENTER_MAIL;
            } else {
                return MessageConstants.UN_SUCCESSFUL_CLASS;
            }
        } catch (NumberFormatException e) {
            return MessageConstants.UN_SUCCESSFUL_CLASS;
        }
    }
    private String checkValidEmailUser(Long userId, String messageText, EmailSender emailSender){
        if (emailSender.isValidEmail(messageText)) {
            dataUsersForRegistration.setMailUser(userId, messageText);
            userStatesForRegistration.remove(userId);
            usersData.insertData(userId, dataUsersForRegistration, databaseConnection);
            return MessageConstants.SUCCESSFUL_REGISTRATION_COMMAND_RESPONSE;
        } else {
            return MessageConstants.NOT_CORRECT_MAIL_COMMAND_RESPONSE;
        }
    }
    /**
     * Обрабатывает логику регистрации пользователя на основе полученного обновления.
     *
     * @param messageText текст сообщения от пользователя
     * @param userId идентификатор пользователя
     * @param emailSender объект для отправки электронной почты
     * @param logicForRegistrationUsers объект логики и данных для регистрации
     * @return ответ пользователю в зависимости от состояния регистрации
     */
    public String worksWithRegistration(String messageText, Long userId, EmailSender emailSender,
                                        LogicForRegistrationUsers logicForRegistrationUsers) {
        String currentState = userStatesForRegistration.get(userId);
        if ("/authorization".equals(messageText)) {
            datebaseTables.createRegistrationDataTable();
            if (usersData.checkUserIdExistsInRegistrationDataTable(userId,
                    logicForRegistrationUsers.getDatabaseConnection())) {
                return MessageConstants.AUTHORISATION_COMMAND_RESPONSE;
            }
            userStatesForRegistration.put(userId, "awaiting_nameUser ");
        } else if ("awaiting_nameUser ".equals(currentState)) {
            dataUsersForRegistration.setNameUser(userId, messageText);
            userStatesForRegistration.remove(userId);
            userStatesForRegistration.put(userId, "awaiting_surnameUser ");
            return MessageConstants.ENTER_SURNAME;
        } else if ("awaiting_surnameUser ".equals(currentState)) {
            dataUsersForRegistration.setSurnameUser(userId, messageText);
            userStatesForRegistration.remove(userId);
            userStatesForRegistration.put(userId, "awaiting_schoolClassUser ");
            return MessageConstants.ENTER_CLASS;
        } else if ("awaiting_schoolClassUser ".equals(currentState)) {
            return checkValidClassUser(userId, messageText);
        } else if ("awaiting_mailUser ".equals(currentState)) {
            return checkValidEmailUser(userId, messageText, emailSender);
        }
        return MessageConstants.REGISTRATION_COMMAND_RESPONSE;
    }
}
