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


    public String getuserStatesForRegistration(Long chatID){
        if (userStatesForRegistration.isEmpty()) {
            return ("0");
        }
        else {
            return (userStatesForRegistration.get(chatID));
        }
    }

    public void insertData(Long userId) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String dataRequest = "INSERT INTO RegistrationDataTable (id_chat, name, surname, school_сlass, mail) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(databaseConnection.getDB_URL(),
                databaseConnection.getDB_USER(), databaseConnection.getDB_PASSWORD());
             PreparedStatement stmt = conn.prepareStatement(dataRequest)) {

            stmt.setString(1, userId.toString());
            stmt.setString(2, nameUser.get(userId));
            stmt.setString(3, surnameUser.get(userId));
            stmt.setString(4, schoolClassUser.get(userId));
            stmt.setString(5, mailUser.get(userId));
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Ошибка вставки данных: " + e.getMessage());
        }
    }

    public Boolean isExistData(Long userId) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String takeData = "SELECT * FROM RegistrationDataTable WHERE id_chat = ?";
        try (Connection conn = DriverManager.getConnection(databaseConnection.getDB_URL(),
                databaseConnection.getDB_USER(), databaseConnection.getDB_PASSWORD());
             PreparedStatement stmt = conn.prepareStatement(takeData)) {
            stmt.setString(1, userId.toString());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Ошибка получения данных: " + e.getMessage());
        }
        return false;
    }

    public String takeData(Long userId) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String takeData = "SELECT * FROM RegistrationDataTable WHERE id_chat = ?";
        try (Connection conn = DriverManager.getConnection(databaseConnection.getDB_URL(),
                databaseConnection.getDB_USER(), databaseConnection.getDB_PASSWORD());
             PreparedStatement stmt = conn.prepareStatement(takeData)) {
            stmt.setString(1, userId.toString());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String schoolClass = rs.getString("school_сlass");
                String mail = rs.getString("mail");
                return "Ваше имя: " + name +
                        "\nВаша фамилия: " + surname +
                        "\nВаш класс: " + schoolClass +
                        "\nВаша почта: " + mail;
            }
        } catch (SQLException e) {
            System.out.println("Ошибка получения данных: " + e.getMessage());
        }
        return "Вы не прошли регистрацию";
    }





    private String registrationCommandReceived(){
        return CommonMessageConstants.REGISTRATION_COMMAND_RESPONSE;
    }
    public String worksWithRegistration(Update update, String messageText, Long userId,EmailSender emailSender){
        String currentState = userStatesForRegistration.get(userId);
        if ("/authorization".equals(messageText)) {
            System.out.println(isExistData( userId));
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
                insertData(userId);
                return "Авторизация окончена успешно. Если хотите проверить данные воспользуйтесь /userInfo";

            } else {
                mailUser.remove(userId, mail);
                return "Адрес электронной почты был указан неправильно отправьте его ещё раз";
            }
        }

        return registrationCommandReceived();
    }
}
