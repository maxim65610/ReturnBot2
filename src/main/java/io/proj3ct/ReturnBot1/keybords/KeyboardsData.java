package io.proj3ct.ReturnBot1.keybords;

import io.proj3ct.ReturnBot1.datebase.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс для работы с данными для /work в базе данных.
 * Этот класс предоставляет методы для вставки, изменения, удаления и получения данных.
 */
public class KeyboardsData {

    private final Logger logger = Logger.getLogger(KeyboardsData.class.getName());

    /**
     * Вставляет новые данные в таблицу DepartsInfo.
     *
     * @param userId            идентификатор пользователя
     * @param databaseConnection объект подключения к базе данных
     * @param newId             новый уникальный идентификатор для записи
     */
    public void insertData(Long userId, DatabaseConnection databaseConnection, Long newId) {
        String dataRequest = "INSERT INTO DepartsInfo " +
                "(id_depart, info, name, inst) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = databaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(dataRequest)) {

            stmt.setLong(1, newId); // Устанавливаем новый ID
            stmt.setString(2, "1");
            stmt.setString(3,"1" );
            stmt.setString(4,"1" );
            stmt.executeUpdate();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка добавления данных: " + e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
    /**
     * Генерирует новый уникальный идентификатор для диспетча.
     *
     * @param databaseConnection объект подключения к базе данных
     * @return новый идентификатор, который равен максимальному ID в таблице + 1
     */
    public Long generateNewId(DatabaseConnection databaseConnection) {
        String maxIdQuery = "SELECT COALESCE(MAX(id), 0) AS maxId FROM DepartsInfo";
        try (Connection conn = databaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(maxIdQuery);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getLong("maxId") + 1; // Возвращаем maxId + 1
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка получения максимального ID: " + e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return 1L; // Если таблица пуста, возвращаем 1
    }
    /**
     * Обновляет данные в таблице DepartsInfo по указанному идентификатору пользователя.
     *
     * @param userId            идентификатор пользователя для обновления записи
     * @param databaseConnection объект подключения к базе данных
     */
    public void updateData(Long userId, DatabaseConnection databaseConnection) {
        String dataRequest = "UPDATE DepartsInfo SET info = ?, name = ?, inst = ? WHERE id_depart = ?";

        try (Connection conn = databaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(dataRequest)) {

            // Устанавливаем новые значения
            stmt.setString(1, "1"); // Установите новое значение для info
            stmt.setString(2, "1"); // Установите новое значение для name
            stmt.setString(3, "1"); // Установите новое значение для inst
            stmt.setLong(4, userId); // Устанавливаем userId как идентификатор для обновления

            // Выполняем обновление
            stmt.executeUpdate();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка обновления данных: " + e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
    /**
     * Удаляет данные из таблицы DepartsInfo по указанному идентификатору пользователя.
     *
     * @param userId            идентификатор пользователя для удаления записи
     * @param databaseConnection объект подключения к базе данных
     */
    public void deleteData(Long userId, DatabaseConnection databaseConnection) {
        String dataRequest = "DELETE FROM DepartsInfo WHERE id_depart = ?";

        try (Connection conn = databaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(dataRequest)) {

            // Устанавливаем userId как идентификатор для удаления
            stmt.setLong(1, userId);

            // Выполняем удаление
            stmt.executeUpdate();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка удаления данных: " + e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
