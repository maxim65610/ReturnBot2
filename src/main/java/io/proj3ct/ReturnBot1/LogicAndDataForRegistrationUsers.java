package io.proj3ct.ReturnBot1;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс для обработки логики и хранения данных пользователей.
 */
public class LogicAndDataForRegistrationUsers {
    private final Map<Long, String> nameUser  = new HashMap<>();
    private final Map<Long, String> surnameUser  = new HashMap<>();
    private final Map<Long, String> schoolClassUser  = new HashMap<>();
    private final Map<Long, String> mailUser  = new HashMap<>();
    private final Map<Long, String> userStatesForRegistration = new HashMap<>();
    private UsersData usersData = new UsersData();
    private DatabaseConnection databaseConnection = new DatabaseConnection();
    private final DatebaseTables datebaseTables = new DatebaseTables(databaseConnection);
    /**
     * Получает объект подключения к базе данных.
     * @return объект DatabaseConnection
     */
    public DatabaseConnection getDatabaseConnection() {return databaseConnection;}
    /**
     * Получает имя пользователя по его идентификатору чата.
     *
     * @param chatID идентификатор чата пользователя
     * @return имя пользователя
     */
    public String getNameUser (Long chatID) {return nameUser .get(chatID);}
    /**
     * Получает фамилию пользователя по его идентификатору чата.
     *
     * @param chatID идентификатор чата пользователя
     * @return фамилия пользователя
     */
    public String getSurnameUser (Long chatID) {return surnameUser .get(chatID);}
    /**
     * Получает класс пользователя по его идентификатору чата.
     *
     * @param chatID идентификатор чата пользователя
     * @return класс пользователя
     */
    public String getSchoolClassUser (Long chatID) {return schoolClassUser .get(chatID);}
    /**
     * Получает электронную почту пользователя по его идентификатору чата.
     *
     * @param chatID идентификатор чата пользователя
     * @return электронная почта пользователя
     */
    public String getMailUser (Long chatID) {return mailUser .get(chatID);}
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
    public void setDatabaseConnection(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    /**
     * Устанавливает объект setUserStateForRegistration(для тестов).
     */
    public void setUserStateForRegistration(Long userId, String state) {
        userStatesForRegistration.put(userId, state);
    }
    /**
     * Получает состояние пользователя для регистрации по его идентификатору чата.
     *
     * @param chatID идентификатор чата пользователя
     * @return состояние пользователя
     */
    public String getUserStatesForRegistration(Long chatID) {
        return userStatesForRegistration.getOrDefault(chatID, "0");
    }
    /**
     * Обрабатывает логику регистрации пользователя на основе полученного обновления.
     *
     * @param messageText текст сообщения от пользователя
     * @param userId идентификатор пользователя
     * @param emailSender объект для отправки электронной почты
     * @param logicAndDataForRegistrationUsers объект логики и данных для регистрации
     * @return ответ пользователю в зависимости от состояния регистрации
     */
    public String worksWithRegistration(String messageText, Long userId, EmailSender emailSender,
                                        LogicAndDataForRegistrationUsers logicAndDataForRegistrationUsers) {
        String currentState = userStatesForRegistration.get(userId);
        if ("/authorization".equals(messageText)) {
            datebaseTables.createRegistrationDataTable();
            if (usersData.checkUserIdExistsInRegistrationDataTable(userId,
                    logicAndDataForRegistrationUsers.getDatabaseConnection())) {
                return MessageConstants.AUTHORISATION_COMMAND_RESPONSE;
            }
            userStatesForRegistration.put(userId, "awaiting_nameUser ");
        } else if ("awaiting_nameUser ".equals(currentState)) {
            nameUser .put(userId, messageText);
            userStatesForRegistration.remove(userId);
            userStatesForRegistration.put(userId, "awaiting_surnameUser ");
            return MessageConstants.ENTER_NAME;
        } else if ("awaiting_surnameUser ".equals(currentState)) {
            surnameUser .put(userId, messageText);
            userStatesForRegistration.remove(userId);
            userStatesForRegistration.put(userId, "awaiting_schoolClassUser ");
            return MessageConstants.ENTER_CLASS;
        } else if ("awaiting_schoolClassUser ".equals(currentState)) {
            try {
                int classNumber = Integer.parseInt(messageText);
                if (classNumber <= 11 && classNumber >= 1) {
                    schoolClassUser.put(userId, messageText);
                    userStatesForRegistration.remove(userId);
                    userStatesForRegistration.put(userId, "awaiting_mailUser ");
                    return MessageConstants.ENTER_MAIL;
                } else {
                    return MessageConstants.UN_SUCCESSFUL_CLASS;
                }
            } catch (NumberFormatException e) {
                return MessageConstants.UN_SUCCESSFUL_CLASS;
            }
        } else if ("awaiting_mailUser ".equals(currentState)) {
            if (emailSender.isValidEmail(messageText)) {
                mailUser .put(userId, messageText);
                userStatesForRegistration.remove(userId);
                usersData.insertData(userId, this, databaseConnection);
                return MessageConstants.SUCCESSFUL_REGISTRATION_COMMAND_RESPONSE;
            } else {
                mailUser .remove(userId, messageText);
                return MessageConstants.NOT_CORRECT_MAIL_COMMAND_RESPONSE;
            }
        }
        return MessageConstants.REGISTRATION_COMMAND_RESPONSE;
    }
}
