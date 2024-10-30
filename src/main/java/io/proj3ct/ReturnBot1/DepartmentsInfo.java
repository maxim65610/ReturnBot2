package io.proj3ct.ReturnBot1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Класс для вывода информации о факультетах урфу
 */
public class DepartmentsInfo {

    private DatabaseConnection databaseConnection = new DatabaseConnection();
    /**
     * Извлекает информацию из базы данных по заданному идентификатору.
     * @param data идентификатор для поиска информации.
     * @param textToSend текст, возвращаемый при отсутствии информации.
     * Нужен для того, чтобы из бд вытащить информацию про нужный факультет.
     */
    public String extract(String data, String textToSend) {
        databaseConnection.createDepartsInfoTableQuery();
        String extractInfoFromDepartsInfo = "SELECT * FROM DepartsInfo WHERE id_depart = ?";

        try (Connection conn = databaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(extractInfoFromDepartsInfo)) {
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