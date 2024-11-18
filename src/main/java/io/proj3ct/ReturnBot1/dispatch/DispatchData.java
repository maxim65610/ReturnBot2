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
    /**

     Метод для получения всех данных о dispatch из таблицы DispatchDataTable.

     @param databaseConnection объект для подключения к базе данных.

     @return двумерный массив строк, где каждая строка содержит данные о dispatch.
     */
    public String[][] getAllDispatchData(DatabaseConnection databaseConnection) {
        // Изменяем запрос на получение всех данных
        String query = "SELECT * FROM DispatchDataTable";
        List<String[]> dispatchDataList = new ArrayList<>();

        try (Connection conn = databaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();

            // Обрабатываем результат запроса
            while (rs.next()) {
                String[] dispatchData = new String[5];
                dispatchData[0] = rs.getString("id");
                dispatchData[1] = rs.getString("text");
                dispatchData[2] = rs.getString("time");
                dispatchData[3] = rs.getString("category");
                dispatchData[4] = rs.getString("department");

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
    /**
     * Метод для получения идентификаторов пользователей и статуса dispatch из таблицы регистрации.
     *
     * @param databaseConnection объект для подключения к базе данных.
     * @return двумерный массив строк, где каждая строка содержит идентификатор пользователя и статус dispatch.
     */
    public String[][] getUserIdAndDispatchOnOrOff(DatabaseConnection databaseConnection) {
        // Изменяем запрос на получение всех данных
        String query = "SELECT * FROM RegistrationDataTable";
        List<String[]> dispatchDataList = new ArrayList<>();

        try (Connection conn = databaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            // Обрабатываем результат запроса
            while (rs.next()) {
                String[] dispatchData = new String[2];
                dispatchData[0] = rs.getString("id_chat");
                dispatchData[1] = rs.getString("dispatch");
                dispatchDataList.add(dispatchData);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка получения данных: " + e.getMessage());
        }
        // Преобразуем список в двумерный массив
        String[][] dispatchDataArray = new String[dispatchDataList.size()][2];
        for (int i = 0; i < dispatchDataList.size(); i++) {
            dispatchDataArray[i] = dispatchDataList.get(i);
        }
        return dispatchDataArray;
    }
    /**
     * Получает год окончания школы для пользователя из таблицы регистрации.
     * Этот метод выполняет запрос к базе данных и возвращает год окончания школы,
     * если пользователь найден в таблице регистрации.
     *
     * @param userId идентификатор пользователя, чьи данные необходимо получить
     * @param databaseConnection объект подключения к базе данных
     * @return год окончания школы для пользователя, или строку "Вы не прошли регистрацию",
     *         если пользователь не найден или произошла ошибка при запросе данных
     */
    public String getUserYearEndSchool(Long userId,DatabaseConnection databaseConnection ) {

        String takeData = "SELECT * FROM RegistrationDataTable WHERE id_chat = ?";

        try (Connection conn = databaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(takeData)) {
            stmt.setString(1, userId.toString());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("year_end_school");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка получения данных: " + e.getMessage());
        }
        return "Вы не прошли регистрацию";
    }
    /**
     * Получает результат теста (или факультет), связанный с пользователем из таблицы регистрации.
     * Этот метод выполняет запрос к базе данных и возвращает название факультета,
     * если пользователь найден в таблице регистрации.
     *
     * @param userId идентификатор пользователя, чьи данные необходимо получить
     * @param databaseConnection объект подключения к базе данных
     * @return результат теста (факультет) для пользователя, или строку "Вы не прошли регистрацию",
     *         если пользователь не найден или произошла ошибка при запросе данных
     */
    public String getUserResultTest(Long userId,DatabaseConnection databaseConnection ) {
        String takeData = "SELECT * FROM RegistrationDataTable WHERE id_chat = ?";

        try (Connection conn = databaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(takeData)) {
            stmt.setString(1, userId.toString());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("department");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка получения данных: " + e.getMessage());
        }
        return "Вы не прошли регистрацию";
    }

}
