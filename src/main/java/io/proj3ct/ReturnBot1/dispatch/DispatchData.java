package io.proj3ct.ReturnBot1.dispatch;

import io.proj3ct.ReturnBot1.datebase.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    public void insertData(Long userId, LogicAndDataForDispatch logicAndDataForDispatch
            , DatabaseConnection databaseConnection, Long newId) {
        String dataRequest = "INSERT INTO DispatchDataTable (id, text, time, category, department) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = databaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(dataRequest)) {

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
    /**
     * Получает все данные диспетча по указанному идентификатору.
     *
     * @param userId              идентификатор пользователя, для которого запрашиваются данные
     * @param databaseConnection   объект подключения к базе данных
     * @return двумерный массив строк, содержащий данные диспетча
     */
    public String[][] getAllDispatchDataById(int userId, DatabaseConnection databaseConnection) {
        String query = "SELECT * FROM DispatchDataTable WHERE id = ?";
        List<String[]> dispatchDataList = new ArrayList<>();

        try (Connection conn = databaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String[] dispatchData = new String[5];
                dispatchData[0] = rs.getString("dispatch_text");
                dispatchData[1] = rs.getString("dispatch_time");
                dispatchData[2] = rs.getString("dispatch_category");
                dispatchData[3] = rs.getString("dispatch_department");
                dispatchData[4] = rs.getString("user_id");
                dispatchDataList.add(dispatchData);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка получения данных: " + e.getMessage());
        }

        // Преобразуем список в двумерный массив
        String[][] dispatchDataArray = new String[dispatchDataList.size()][5];
        for (int i = 0; i < dispatchDataList.size(); i++) {
            dispatchDataArray[i] = dispatchDataList.get(i);
        }

        return dispatchDataArray;
    }
}
