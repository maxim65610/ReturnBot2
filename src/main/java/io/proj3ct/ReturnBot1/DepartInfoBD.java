package io.proj3ct.ReturnBot1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Класс для вывода информации о факультетах урфу
 * @ DB_URL URL базы данных
 * @ DB_USER имя пользователя базы данных
 * @ DB_PASSWORD пароль пользователя базы данных
 */

public class DepartInfoBD {
    DatabaseConnection databaseConnection = new DatabaseConnection();
    String url = databaseConnection.getDB_URL();
    String user =databaseConnection.getDB_USER();
    String password = databaseConnection.getDB_PASSWORD();

    /**
     * Извлекает информацию из базы данных по заданному идентификатору.
     * @param data Идентификатор для поиска информации.
     * @param textToSend Текст, возвращаемый при отсутствии информации.
     */

    public String takeInfo(String data,String textToSend) {

        String sql = "SELECT * FROM DepartsInfo WHERE id_depart = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);

            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1,data);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("info");

            }


        } catch (SQLException e) {
            System.out.println("Ошибка извлечения данных: " + e.getMessage());
        }
        return textToSend;
    }
}
