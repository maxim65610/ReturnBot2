package io.proj3ct.ReturnBot1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Класс для вывода информации о факультетах урфу
 */
public class DepartInfoBD {
    private DatabaseConnection databaseConnection = new DatabaseConnection();


    /**
     * Извлекает информацию из базы данных по заданному идентификатору.
     * @param data Идентификатор для поиска информации.
     * @param textToSend Текст, возвращаемый при отсутствии информации.
     * Нужен для того, чтобы из бд вытащить информацию про нужный факультет.
     */
    public String takeInfo(String data,String textToSend) {
        databaseConnection.createDepartsInfoTableQuery();
        String selectDataFromDepartsInfo = "SELECT * FROM DepartsInfo WHERE id_depart = ?";

        try (Connection conn = databaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(selectDataFromDepartsInfo)) {
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
