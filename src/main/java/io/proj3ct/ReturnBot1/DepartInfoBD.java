package io.proj3ct.ReturnBot1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Класс для контроля над выводимой с клавиатур информации
 */

public class DepartInfoBD {
    /**
     * Класс, обрабатывает хеши и по хешам возвращает текст, который должен отправляться по нажатии на кнопку
     */
    DatabaseConnection databaseConnection = new DatabaseConnection();

    public String takeInfo(String data,String textToSend) {

        String sql = "SELECT * FROM DepartsInfo WHERE id_depart = ?";

        try (Connection conn = DriverManager.getConnection(databaseConnection.getDB_URL(),
                databaseConnection.getDB_USER(), databaseConnection.getDB_PASSWORD());

             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1,data);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString("info");

            }

        } catch (SQLException e) {
            System.out.println("Ошибка извлечения данных: " + e.getMessage());
        }
        return textToSend;
    }
}
