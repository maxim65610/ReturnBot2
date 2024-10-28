package io.proj3ct.ReturnBot1;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class LogicAndDataForRegistrationUsers {
    private Map<Long, String> nameUser = new HashMap<>();
    private Map<Long, String> surnameUser= new HashMap<>();
    private Map<Long, String> schoolClassUser = new HashMap<>();
    private Map<Long, String> mailUser = new HashMap<>();
    private Map<Long, String> userStatesForRegistration = new HashMap<>();
    private UsersData usersData = new UsersData();
    private DatabaseConnection databaseConnection = new DatabaseConnection();

    public DatabaseConnection getDatabaseConnection(){
        return databaseConnection;
    }
    public String getNameUser(Long chatID) {
        return nameUser.get(chatID);
    }

    public String getSurnameUser(Long chatID) {
        return surnameUser.get(chatID);
    }

    public String getSchoolClassUser(Long chatID) {
        return schoolClassUser.get(chatID);
    }

    public String getMailUser(Long chatID) {
        return mailUser.get(chatID);
    }

    public String getUserStateForRegistration(Long chatID) {
        return userStatesForRegistration.get(chatID);
    }

    public String getuserStatesForRegistration(Long chatID){
        if (userStatesForRegistration.isEmpty()) {
            return ("0");
        }
        else {
            return (userStatesForRegistration.get(chatID));
        }
    }
    private String registrationCommandReceived(){
        return CommonMessageConstants.REGISTRATION_COMMAND_RESPONSE;
    }

    public String worksWithRegistration(Update update, String messageText, Long userId,EmailSender emailSender
    ,LogicAndDataForRegistrationUsers logicAndDataForRegistrationUsers){


        String currentState = userStatesForRegistration.get(userId);
        if ("/authorization".equals(messageText)) {
            databaseConnection.createRegistrationDataTable();

            if(usersData.checkUserIdExistsInRegistrationDataTable(userId
                    ,logicAndDataForRegistrationUsers.getDatabaseConnection())){
                return "Вы уже зарегистрированы!!!\nЕсли хотите проверить данные воспользуйтесь /userInfo\n" +
                        "Если хотите удалить данные воспользуйтесь /userDataDell\n";
            }
            userStatesForRegistration.put(userId, "awaiting_nameUser");
        }
        else if("awaiting_nameUser".equals(currentState)) {
            String name = update.getMessage().getText();
            nameUser.put(userId, name);
            userStatesForRegistration.remove(userId);
            userStatesForRegistration.put(userId, "awaiting_surnameUser");
            return "Введите фамилию:";
        }
        else if("awaiting_surnameUser".equals(currentState)){
            String surname = update.getMessage().getText();
            surnameUser.put(userId, surname);
            userStatesForRegistration.remove(userId);
            userStatesForRegistration.put(userId, "awaiting_schoolClassUser");
            return "Введите класс:";
        }
        else if("awaiting_schoolClassUser".equals(currentState)){
            String schoolClass = update.getMessage().getText();
            try {
                int classNumber = Integer.parseInt(schoolClass);
                if(classNumber<=11 && classNumber>=1) {
                    schoolClassUser.put(userId, schoolClass);
                    userStatesForRegistration.remove(userId);
                    userStatesForRegistration.put(userId, "awaiting_mailUser");
                    return "Введите почту:";
                }
                else{
                    return "Вы ввели некорректный класс, ведите класс заново";
                }
            } catch (NumberFormatException e) {
                return "Вы ввели некорректный класс, ведите класс заново";
            }
        }
        else if("awaiting_mailUser".equals(currentState)){
            String mail = update.getMessage().getText();
            if(emailSender.isValidEmail(mail)){
                mailUser.put(userId, mail);
                userStatesForRegistration.remove(userId);
                usersData.insertData(userId,logicAndDataForRegistrationUsers,databaseConnection);
                return "Авторизация окончена успешно.\nЕсли хотите проверить данные воспользуйтесь /userInfo" +
                        "\nЕсли хотите удалить данные воспользуйтесь /userDataDell";

            } else {
                mailUser.remove(userId, mail);
                return "Адрес электронной почты был указан неправильно отправьте его ещё раз";
            }
        }

        return registrationCommandReceived();
    }
}
