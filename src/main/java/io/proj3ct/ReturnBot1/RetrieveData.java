package io.proj3ct.ReturnBot1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * Класс для извлечения данных из базы данных.
 * Унаследован от класса DatabaseConnection.
 */
public class RetrieveData {

    /**
     * Метод для извлечения строки данных из таблицы AnswersData по id_question
     * @ DB_URL URL базы данных
     * @ DB_USER имя пользователя базы данных
     * @ DB_PASSWORD пароль пользователя базы данных
     * @param id идентификатор строки
     */

    DatabaseConnection databaseConnection = new DatabaseConnection();

    public String getDataById(int id, String data) {
        String sql = "SELECT * FROM AnswersData WHERE id_question = ?";


        try (Connection conn = DriverManager.getConnection(databaseConnection.getDB_URL(),
                databaseConnection.getDB_USER(), databaseConnection.getDB_PASSWORD());

             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                if(data.equals("id_question")){
                    String id_question = rs.getString(data);
                    return id_question;
                }
                if(data.equals("answer1")){
                    String answer1 = rs.getString(data);
                    return answer1;
                }
                if(data.equals("question")){
                    String question = rs.getString(data);
                    return question;
                }
                if(data.equals("answer2")){
                    String answer2 = rs.getString(data);
                    return answer2;
                }
                if(data.equals("answer3")){
                    String answer3 = rs.getString(data);
                    return answer3;
                }
                if(data.equals("cash1")){
                    String cash1 = rs.getString(data);
                    return cash1;
                }
                if(data.equals("cash2")){
                    String cash2 = rs.getString(data);
                    return cash2;
                }
                if(data.equals("cash3")){
                    String cash3 = rs.getString(data);
                    return cash3;
                }


            } else {
                System.out.println("Данные не найдены для id_question: " + id);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка извлечения данных: " + e.getMessage());
        }
        return sql;
    }
}
