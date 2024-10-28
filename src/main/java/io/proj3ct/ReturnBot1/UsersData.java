package io.proj3ct.ReturnBot1;

import java.sql.*;


public class UsersData {

    public String insertData(Long userId, LogicAndDataForRegistrationUsers logicAndDataForRegistrationUsers
            ,DatabaseConnection databaseConnection ) {

        String dataRequest = "INSERT INTO RegistrationDataTable " +
                "(id_chat, name, surname, school_сlass, mail) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(databaseConnection.getDB_URL(),
                databaseConnection.getDB_USER(), databaseConnection.getDB_PASSWORD());
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

    public boolean checkUserIdExistsInRegistrationDataTable(Long userId,DatabaseConnection databaseConnection ) {
        try {

// Подключение к базе данных
            Connection conn = DriverManager.getConnection(databaseConnection.getDB_URL(),
                    databaseConnection.getDB_USER(), databaseConnection.getDB_PASSWORD());
            String sql = "SELECT * FROM RegistrationDataTable WHERE id_chat = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
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

    public void deleteData(Long userId,DatabaseConnection databaseConnection ) {
        String dataRequest = "DELETE FROM RegistrationDataTable WHERE id_chat = ?";
        try (Connection conn = DriverManager.getConnection(databaseConnection.getDB_URL(),
                databaseConnection.getDB_USER(), databaseConnection.getDB_PASSWORD());
             PreparedStatement stmt = conn.prepareStatement(dataRequest)) {
            stmt.setString(1, userId.toString());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Ошибка удаления данных: " + e.getMessage());
        }
    }

    public String takeData(Long userId,DatabaseConnection databaseConnection ) {

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

}
