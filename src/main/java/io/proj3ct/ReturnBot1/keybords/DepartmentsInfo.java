package io.proj3ct.ReturnBot1.keybords;

import io.proj3ct.ReturnBot1.datebase.DatabaseConnection;
import io.proj3ct.ReturnBot1.datebase.DatebaseTables;

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
     * Извлекает информацию о факультете по заданному идентификатору.
     *
     * <p>Этот метод выполняет запрос к базе данных для получения информации
     * о факультете из таблицы DepartsInfo, используя переданный идентификатор
     * факультета. В случае успешного извлечения данных возвращается строка
     * с информацией о факультете, если данные не найдены, возвращается null.</p>
     *
     * @param data Идентификатор факультета (например, строка с кодом факультета).
     * @return Строка с информацией о факультете или null, если факультет не найден.
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