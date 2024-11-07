package io.proj3ct.ReturnBot1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Выводит информацию о факультетах урфу.
 * Извлекает информацию из базы данных по заданному идентификатору.
 * Нужен для того, чтобы из бд вытащить информацию про нужный факультет.
 */
public class DepartmentsInfo {


    private final DatabaseConnection databaseConnection = new DatabaseConnection();
    private final DatebaseTables datebaseTables = new DatebaseTables(databaseConnection);

    /**
     * Выводит информацию о факультете урфу
     */
    public String extract(String data) {
        datebaseTables.createDepartsInfoTableQuery();
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
        return null;
    }
}