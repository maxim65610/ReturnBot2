package io.proj3ct.ReturnBot1.registration;

import io.proj3ct.ReturnBot1.baseClasses.MessageConstants;
import io.proj3ct.ReturnBot1.datebase.DatabaseConnection;
import io.proj3ct.ReturnBot1.datebase.DatebaseTables;
import io.proj3ct.ReturnBot1.mail.EmailSender;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс для обработки логики пользователей при регистрации.
 */
public class LogicForRegistrationUsers {
    // Хранит состояния пользователей для регистрации
    private final Map<Long, String> userStatesForRegistration = new HashMap<>();
    // Объект для хранения данных пользователей
    private DataUsersForRegistration dataUsersForRegistration = new DataUsersForRegistration();
    // Объект для работы с данными пользователей
    private UsersData usersData = new UsersData();
    // Объект для подключения к базе данных
    private DatabaseConnection databaseConnection = new DatabaseConnection();
    // Объект для работы с таблицами базы данных
    private DatebaseTables datebaseTables = new DatebaseTables(databaseConnection);
    /**
     * Дефолтный конструктор.
     */
    public LogicForRegistrationUsers() {
    }
    /**
     * Конструктор с параметрами для тестирования.
     *
     * @param usersData объект для работы с данными пользователей
     * @param databaseConnection объект подключения к базе данных
     * @param dataUsersForRegistration объект для хранения данных пользователей
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
     *
     * @return объект DatabaseConnection
     */
    public DatabaseConnection getDatabaseConnection() {
        return databaseConnection;
    }
    /**
     * Получает состояние регистрации пользователя по его идентификатору.
     *
     * @param chatID уникальный идентификатор чата пользователя
     * @return состояние регистрации пользователя или "0", если состояние не найдено
     */
    public String getUserStatesForRegistration(Long chatID) {
        return userStatesForRegistration.getOrDefault(chatID, "0");
    }
    /**
     * Проверяет, является ли класс пользователя допустимым.
     *
     * @param userId идентификатор пользователя
     * @param messageText текст сообщения, содержащий класс
     * @return сообщение о результате проверки
     */
    private String checkValidClassUser (Long userId, String messageText) {
        try {
            int classNumber = Integer.parseInt(messageText);
            if (classNumber <= 11 && classNumber >= 1) {
                dataUsersForRegistration.setSchoolClassUser (userId, messageText);
                userStatesForRegistration.remove(userId);
                userStatesForRegistration.put(userId, "awaiting_mailUser  ");
                return MessageConstants.ENTER_MAIL;
            } else {
                return MessageConstants.UN_SUCCESSFUL_CLASS;
            }
        } catch (NumberFormatException e) {
            return MessageConstants.UN_SUCCESSFUL_CLASS;
        }
    }
    /**
     * Проверяет, является ли адрес электронной почты пользователя допустимым.
     *
     * @param userId идентификатор пользователя
     * @param messageText текст сообщения, содержащий адрес электронной почты
     * @param emailSender объект для отправки электронной почты
     * @return сообщение о результате проверки
     */
    private String checkValidEmailUser (Long userId, String messageText, EmailSender emailSender) {
        if (emailSender.isValidEmail(messageText)) {
            dataUsersForRegistration.setMailUser (userId, messageText);
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
            userStatesForRegistration.put(userId, "awaiting_nameUser  ");
        } else if ("awaiting_nameUser  ".equals(currentState)) {
            dataUsersForRegistration.setNameUser (userId, messageText);
            userStatesForRegistration.remove(userId);
            userStatesForRegistration.put(userId, "awaiting_surnameUser  ");
            return MessageConstants.ENTER_SURNAME;
        } else if ("awaiting_surnameUser  ".equals(currentState)) {
            dataUsersForRegistration.setSurnameUser (userId, messageText);
            userStatesForRegistration.remove(userId);
            userStatesForRegistration.put(userId, "awaiting_schoolClassUser  ");
            return MessageConstants.ENTER_CLASS;
        } else if ("awaiting_schoolClassUser  ".equals(currentState)) {
            return checkValidClassUser (userId, messageText);
        } else if ("awaiting_mailUser  ".equals(currentState)) {
            return checkValidEmailUser (userId, messageText, emailSender);
        }
        return MessageConstants.REGISTRATION_COMMAND_RESPONSE;
    }
}
