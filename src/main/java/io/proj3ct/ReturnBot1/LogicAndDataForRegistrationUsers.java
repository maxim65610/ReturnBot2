package io.proj3ct.ReturnBot1;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс для обработки логики и данных регистрации пользователей.
 */
public class LogicAndDataForRegistrationUsers {
    private final Map<Long, String> nameUser  = new HashMap<>();
    private final Map<Long, String> surnameUser  = new HashMap<>();
    private final Map<Long, String> schoolClassUser  = new HashMap<>();
    private final Map<Long, String> mailUser  = new HashMap<>();
    private final Map<Long, String> userStatesForRegistration = new HashMap<>();
    private final UsersData usersData = new UsersData();
    private final DatabaseConnection databaseConnection = new DatabaseConnection();
    private final DatebaseTables datebaseTables = new DatebaseTables(databaseConnection);
    /**
     * Получает объект подключения к базе данных.
     * @return объект DatabaseConnection
     */
    public DatabaseConnection getDatabaseConnection() {return databaseConnection;}
    /**
     * Получает имя пользователя по его идентификатору чата.
     * @param chatID идентификатор чата пользователя
     * @return имя пользователя
     */
    public String getNameUser (Long chatID) {return nameUser .get(chatID);}
    /**
     * Получает фамилию пользователя по его идентификатору чата.
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
     * Получает состояние пользователя для регистрации по его идентификатору чата.
     *
     * @param chatID идентификатор чата пользователя
     * @return состояние пользователя
     */
    public String getUserStatesForRegistration(Long chatID) {
        if (userStatesForRegistration.isEmpty()) {
            return ("0");
        } else {
            return (userStatesForRegistration.get(chatID));
        }
    }
    /**
     * Обрабатывает логику регистрации пользователя на основе полученного обновления.
     *
     * @param update объект Update от Telegram API
     * @param messageText текст сообщения от пользователя
     * @param userId идентификатор пользователя
     * @param emailSender объект для отправки электронной почты
     * @param logicAndDataForRegistrationUsers объект логики и данных для регистрации
     * @return ответ пользователю в зависимости от состояния регистрации
     */
    public String worksWithRegistration(Update update, String messageText, Long userId, EmailSender emailSender,
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

            String name = update.getMessage().getText();
            nameUser .put(userId, name);
            userStatesForRegistration.remove(userId);
            userStatesForRegistration.put(userId, "awaiting_surnameUser ");
            return "Введите фамилию:";
        } else if ("awaiting_surnameUser ".equals(currentState)) {
            String surname = update.getMessage().getText();
            surnameUser .put(userId, surname);
            userStatesForRegistration.remove(userId);
            userStatesForRegistration.put(userId, "awaiting_schoolClassUser ");
            return "Введите класс:";
        } else if ("awaiting_schoolClassUser ".equals(currentState)) {
            String schoolClass = update.getMessage().getText();
            try {
                int classNumber = Integer.parseInt(schoolClass);
                if (classNumber <= 11 && classNumber >= 1) {
                    schoolClassUser.put(userId, schoolClass);
                    userStatesForRegistration.remove(userId);
                    userStatesForRegistration.put(userId, "awaiting_mailUser ");
                    return "Введите почту:";
                } else {
                    return MessageConstants.UN_SUCCESSFUL_CLASS;
                }
            } catch (NumberFormatException e) {
                return MessageConstants.UN_SUCCESSFUL_CLASS;
            }
        } else if ("awaiting_mailUser ".equals(currentState)) {
            String mail = update.getMessage().getText();
            if (emailSender.isValidEmail(mail)) {
                mailUser .put(userId, mail);
                userStatesForRegistration.remove(userId);
                usersData.insertData(userId, this, databaseConnection);
                return MessageConstants.SUCCESSFUL_REGISTRATION_COMMAND_RESPONSE;
            } else {
                mailUser .remove(userId, mail);
                return MessageConstants.NOT_CORRECT_MAIL_COMMAND_RESPONSE;
            }
        }
        return MessageConstants.REGISTRATION_COMMAND_RESPONSE;
    }
}