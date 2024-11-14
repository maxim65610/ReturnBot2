package io.proj3ct.ReturnBot1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Класс для работы с данными диспетча в базе данных.
 * Предоставляет методы для вставки данных и генерации нового идентификатора.
 */
public class DispatchData {

    /**
     * Вставляет данные диспетча в таблицу DispatchDataTable.
     *
     * @param userId                  идентификатор пользователя, который создает диспетч
     * @param logicAndDataForDispatch объект логики и данных диспетча
     * @param databaseConnection      объект подключения к базе данных
     */
    public void insertData(Long userId, LogicAndDataForDispatch logicAndDataForDispatch, DatabaseConnection databaseConnection) {
        String dataRequest = "INSERT INTO DispatchDataTable (id, text, time, category, department) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = databaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(dataRequest)) {

            // Генерация нового ID
            Long newId = generateNewId(databaseConnection);
            stmt.setLong(1, newId); // Устанавливаем новый ID
            stmt.setString(2, logicAndDataForDispatch.getDispatchText(userId));
            stmt.setString(3, logicAndDataForDispatch.getDispatchTime(userId));
            stmt.setString(4, logicAndDataForDispatch.getDispatchCategory(userId));
            stmt.setString(5, logicAndDataForDispatch.getDispatchDepartment(userId));
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Ошибка вставки данных: " + e.getMessage());
        }
    }

    /**
     * Генерирует новый уникальный идентификатор для диспетча.
     *
     * @param databaseConnection объект подключения к базе данных
     * @return новый идентификатор, который равен максимальному ID в таблице + 1
     */
    public Long generateNewId(DatabaseConnection databaseConnection) {
        String maxIdQuery = "SELECT COALESCE(MAX(id), 0) AS maxId FROM DispatchDataTable";
        try (Connection conn = databaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(maxIdQuery);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getLong("maxId") + 1; // Возвращаем maxId + 1
            }
        } catch (SQLException e) {
            System.out.println("Ошибка получения максимального ID: " + e.getMessage());
        }
        return 1L; // Если таблица пуста, возвращаем 1
    }
}
