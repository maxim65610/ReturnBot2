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
     * @param databaseConnection объект подключения к базе данных
     * @param newId             новый уникальный идентификатор для записи
     */
    public void insertData(Long userID,DatabaseConnection databaseConnection, Long newId,
                           DataForDepartment dataForDepartment) {
        String dataRequest = "INSERT INTO DepartsInfo " +
                "(id_depart, info, name, inst) VALUES (?, ?, ?, ?)";
        try (Connection conn = databaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(dataRequest)) {
            stmt.setLong(1, newId); // Устанавливаем новый ID
            stmt.setString(2,dataForDepartment.getTextForNewDepartment(userID) );
            stmt.setString(3,dataForDepartment.getNameForNewDepartment(userID) );
            stmt.setString(4,dataForDepartment.getInstituteForNewDepartment(userID) );
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
        String allIdsQuery = "SELECT id_depart FROM DepartsInfo";
        Long maxId = 1L;
        try (Connection conn = databaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(allIdsQuery);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String idDepartStr = rs.getString("id_depart");
                try {
                    Long idDepart = Long.parseLong(idDepartStr);
                    if (idDepart > maxId) {
                        maxId = idDepart;
                    }
                } catch (NumberFormatException e) {
                    logger.log(Level.WARNING, "Невозможно преобразовать id_depart в число: " + idDepartStr, e);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка получения всех ID: " + e.getMessage(), e);
            throw new RuntimeException(e);
        }

        return maxId+1L;
    }
    /**
     * Обновляет данные в таблице DepartsInfo по указанному идентификатору пользователя.
     *
     * @param userId            идентификатор пользователя для обновления записи
     * @param databaseConnection объект подключения к базе данных
     */
    public void updateData(Long userId, DatabaseConnection databaseConnection, DataForDepartment dataForDepartment) {
        String dataRequest = "UPDATE DepartsInfo SET info = ?, name = ? WHERE id_depart = ?";
        try (Connection conn = databaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(dataRequest)) {
            // Устанавливаем новые значения
             // Устанавливаем новый ID
            stmt.setString(1,dataForDepartment.getTextForEditDepartment(userId));
            stmt.setString(2,dataForDepartment.getNameForEditDepartment(userId));
            stmt.setString(3, dataForDepartment.getNumberForEditDepartment(userId));

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
    public void deleteData(Long userId, DatabaseConnection databaseConnection,DataForDepartment dataForDepartment) {
        String dataRequest = "DELETE FROM DepartsInfo WHERE id_depart = ?";

        try (Connection conn = databaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(dataRequest)) {

            // Устанавливаем userId как идентификатор для удаления
            stmt.setString(1, dataForDepartment.getNumberForDeleteDepartment(userId));

            // Выполняем удаление
            stmt.executeUpdate();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка удаления данных: " + e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
