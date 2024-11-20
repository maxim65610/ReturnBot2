package io.proj3ct.ReturnBot1;

import org.apache.commons.lang3.ObjectUtils;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс для работы с данными пользователей в базе данных.
 * Этот класс предоставляет методы для вставки, изменения, удаления и получения данных пользователей.
 */
public class UsersData {
    private final Logger logger = Logger.getLogger(UsersData.class.getName());

    /**
     * Метод для вставки данных пользователя в таблицу регистрации
     *
     * @param userId                           идентификатор пользователя
     * @param logicAndDataForRegistrationUsers объект, содержащий логику и данные для регистрации пользователя
     * @param databaseConnection               объект для подключения к базе данных
     */
    public void insertData(Long userId, LogicAndDataForRegistrationUsers logicAndDataForRegistrationUsers
            , DatabaseConnection databaseConnection ) {

        String dataRequest = "INSERT INTO RegistrationDataTable " +
                "(id_chat, name, surname, school_class, mail) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = databaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(dataRequest)) {

            stmt.setString(1, userId.toString());
            stmt.setString(2, logicAndDataForRegistrationUsers.getNameUser(userId));
            stmt.setString(3, logicAndDataForRegistrationUsers.getSurnameUser(userId));
            stmt.setString(4, logicAndDataForRegistrationUsers.getSchoolClassUser(userId));
            stmt.setString(5, logicAndDataForRegistrationUsers.getMailUser(userId));
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка создания таблицы: " + e.getMessage(), e);
            throw new RuntimeException(e);
        }
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
            stmt.executeUpdate();


        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка создания таблицы: " + e.getMessage(), e);
            throw new RuntimeException(e);
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
            stmt.executeUpdate();

        } catch (SQLException e) {
            // Обработка исключений
            logger.log(Level.SEVERE, "Ошибка создания таблицы: " + e.getMessage(), e);
            throw new RuntimeException(e);
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
        String dataRequest = "UPDATE RegistrationDataTable SET school_class = ? WHERE id_chat = ?";

        try (Connection conn = databaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(dataRequest)) {

            // Установка значений для параметров запроса
            stmt.setString(1, schoolClass); // Установка нового имени
            stmt.setString(2, userId.toString()); // Установка userId для фильтрации
            stmt.executeUpdate();

        } catch (SQLException e) {
            // Обработка исключений
            logger.log(Level.SEVERE, "Ошибка создания таблицы: " + e.getMessage(), e);
            throw new RuntimeException(e);
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
            stmt.executeUpdate();


        } catch (SQLException e) {
            // Обработка исключений
            logger.log(Level.SEVERE, "Ошибка создания таблицы: " + e.getMessage(), e);
            throw new RuntimeException(e);
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
              logger.log(Level.SEVERE, "Ошибка создания таблицы: " + e.getMessage(), e);
              throw new RuntimeException(e);
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
            logger.log(Level.SEVERE, "Ошибка создания таблицы: " + e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
    /**
     Метод для получения данных пользователя из таблицы регистрации
     @param userId идентификатор пользователя
     @param databaseConnection объект для подключения к базе данных
     @return данные пользователя или сообщение об ошибке
     */
    public String getRegistrationData(Long userId, DatabaseConnection databaseConnection ) {

        String takeData = "SELECT * FROM RegistrationDataTable WHERE id_chat = ?";
        try (Connection conn = databaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(takeData)) {
            stmt.setString(1, userId.toString());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String schoolClass = rs.getString("school_class");
                String mail = rs.getString("mail");
                return "Ваше имя: " + name +
                        "\nВаша фамилия: " + surname +
                        "\nВаш класс: " + schoolClass +
                        "\nВаша почта: " + mail;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка создания таблицы: " + e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return null;
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
            logger.log(Level.SEVERE, "Ошибка создания таблицы: " + e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return null;
    }
}
