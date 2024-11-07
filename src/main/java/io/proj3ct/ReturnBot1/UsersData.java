package io.proj3ct.ReturnBot1;

import java.sql.*;

/**
 Класс для работы с данными пользователей в базе данных
 */
public class UsersData {
    /**
     Метод для вставки данных пользователя в таблицу регистрации
     @param userId идентификатор пользователя
     @param logicAndDataForRegistrationUsers объект, содержащий логику и данные для регистрации пользователя
     @param databaseConnection объект для подключения к базе данных
     */
    public String insertData(Long userId, LogicAndDataForRegistrationUsers logicAndDataForRegistrationUsers
            ,DatabaseConnection databaseConnection ) {

        String dataRequest = "INSERT INTO RegistrationDataTable " +
                "(id_chat, name, surname, school_сlass, mail) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = databaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(dataRequest)) {

            stmt.setString(1, userId.toString());
            stmt.setString(2, logicAndDataForRegistrationUsers.getNameUser(userId));
            stmt.setString(3, logicAndDataForRegistrationUsers.getSurnameUser(userId));
            stmt.setString(4, logicAndDataForRegistrationUsers.getSchoolClassUser(userId));
            stmt.setString(5, logicAndDataForRegistrationUsers.getMailUser(userId));
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Ошибка вставки данных: " + e.getMessage());
        }
        return "";
    }
    /**
     Метод для изменения поля имя пользователя в бд
     @param name новое имя пользователя
     @param userId идентификатор пользователя
     @param databaseConnection объект для подключения к базе данных
     */
    public void changeName(Long userId
            , DatabaseConnection databaseConnection, String name) {

        // SQL-запрос для обновления имени в таблице RegistrationDataTable по userId
        String dataRequest = "UPDATE RegistrationDataTable SET name = ? WHERE id_chat = ?";

        try (Connection conn = databaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(dataRequest)) {

            // Установка значений для параметров запроса
            stmt.setString(1, name); // Установка нового имени
            stmt.setString(2, userId.toString()); // Установка userId для фильтрации

            // Выполнение обновления
            int rowsAffected = stmt.executeUpdate();

            // Проверка, обновлено ли хоть одно значение
            if (rowsAffected > 0) {
            } else {
            }

        } catch (SQLException e) {
            // Обработка исключений
            System.out.println("Ошибка обновления данных: " + e.getMessage());
            e.getMessage();
        }
    }
    /**
     Метод для изменения поля фамилия пользователя в бд
     @param surname новая фамилия пользователя
     @param userId идентификатор пользователя
     @param databaseConnection объект для подключения к базе данных
     */
    public void changeSurname(Long userId
            , DatabaseConnection databaseConnection, String surname) {

        // SQL-запрос для обновления имени в таблице RegistrationDataTable по userId
        String dataRequest = "UPDATE RegistrationDataTable SET surname = ? WHERE id_chat = ?";

        try (Connection conn = databaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(dataRequest)) {

            // Установка значений для параметров запроса
            stmt.setString(1, surname); // Установка нового имени
            stmt.setString(2, userId.toString()); // Установка userId для фильтрации

            // Выполнение обновления
            int rowsAffected = stmt.executeUpdate();

            // Проверка, обновлено ли хоть одно значение
            if (rowsAffected > 0) {
            } else {
            }

        } catch (SQLException e) {
            // Обработка исключений
            System.out.println("Ошибка обновления данных: " + e.getMessage());
            e.getMessage();
        }
    }
    /**
     Метод для изменения поля школьного класса пользователя в бд
     @param schoolClass новое имя пользователя
     @param userId идентификатор пользователя
     @param databaseConnection объект для подключения к базе данных
     */
    public void changeClass(Long userId
            , DatabaseConnection databaseConnection, String schoolClass) {

        // SQL-запрос для обновления имени в таблице RegistrationDataTable по userId
        String dataRequest = "UPDATE RegistrationDataTable SET school_сlass = ? WHERE id_chat = ?";

        try (Connection conn = databaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(dataRequest)) {

            // Установка значений для параметров запроса
            stmt.setString(1, schoolClass); // Установка нового имени
            stmt.setString(2, userId.toString()); // Установка userId для фильтрации

            // Выполнение обновления
            int rowsAffected = stmt.executeUpdate();

            // Проверка, обновлено ли хоть одно значение
            if (rowsAffected > 0) {
            } else {
            }

        } catch (SQLException e) {
            // Обработка исключений
            System.out.println("Ошибка обновления данных: " + e.getMessage());
            e.getMessage();
        }
    }
    /**
     Метод для изменения поля почта пользователя в бд
     @param mail новое имя пользователя
     @param userId идентификатор пользователя
     @param databaseConnection объект для подключения к базе данных
     */
    public void changeMail(Long userId
            , DatabaseConnection databaseConnection, String mail) {

        // SQL-запрос для обновления имени в таблице RegistrationDataTable по userId
        String dataRequest = "UPDATE RegistrationDataTable SET mail = ? WHERE id_chat = ?";

        try (Connection conn = databaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(dataRequest)) {

            // Установка значений для параметров запроса
            stmt.setString(1, mail); // Установка нового имени
            stmt.setString(2, userId.toString()); // Установка userId для фильтрации

            // Выполнение обновления
            int rowsAffected = stmt.executeUpdate();

            // Проверка, обновлено ли хоть одно значение
            if (rowsAffected > 0) {
            } else {
            }

        } catch (SQLException e) {
            // Обработка исключений
            System.out.println("Ошибка обновления данных: " + e.getMessage());
            e.getMessage();
        }
    }
    /**
     Метод для проверки существования пользователя в таблице регистрации
     @param userId идентификатор пользователя
     @param databaseConnection объект для подключения к базе данных
     @return true, если пользователь существует, false в противном случае
     */
    public boolean checkUserIdExistsInRegistrationDataTable(Long userId,DatabaseConnection databaseConnection ) {

        String dataRequest = "SELECT * FROM RegistrationDataTable WHERE id_chat = ?";

          try (Connection conn = databaseConnection.connect();
                  PreparedStatement statement = conn.prepareStatement(dataRequest)) {

            statement.setString(1,userId.toString());

            // Выполнение запроса
            ResultSet resultSet = statement.executeQuery();

            // Проверка наличия данных
            if (resultSet.next()) {
                return true; // Данные найдены
            } else {
                return false; // Данные не найдены
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Обработка ошибки
        }
    }
    /**
     Метод для удаления данных пользователя из таблицы регистрации
     @param userId идентификатор пользователя
     @param databaseConnection объект для подключения к базе данных
     */
    public void deleteData(Long userId,DatabaseConnection databaseConnection ) {

        String dataRequest = "DELETE FROM RegistrationDataTable WHERE id_chat = ?";
        // Подключение к базе данных

        try (Connection conn = databaseConnection.connect();
             // Выполнение запроса
             PreparedStatement stmt = conn.prepareStatement(dataRequest)) {
             stmt.setString(1, userId.toString());
             stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Ошибка удаления данных: " + e.getMessage());
        }
    }
    /**
     Метод для получения данных пользователя из таблицы регистрации
     @param userId идентификатор пользователя
     @param databaseConnection объект для подключения к базе данных
     @return данные пользователя или сообщение об ошибке
     */
    public String takeData(Long userId,DatabaseConnection databaseConnection ) {

        String takeData = "SELECT * FROM RegistrationDataTable WHERE id_chat = ?";

        try (Connection conn = databaseConnection.connect();
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
    /**
     Метод для получения почты для EmailSender пользователя из таблицы регистрации
     @param userId идентификатор пользователя
     @param databaseConnection объект для подключения к базе данных
     @return данные пользователя или сообщение об ошибке
     */
    public String getUserMail(Long userId,DatabaseConnection databaseConnection ) {

        String takeData = "SELECT * FROM RegistrationDataTable WHERE id_chat = ?";

        try (Connection conn = databaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(takeData)) {
            stmt.setString(1, userId.toString());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("mail");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка получения данных: " + e.getMessage());
        }
        return "Вы не прошли регистрацию";
    }
}
