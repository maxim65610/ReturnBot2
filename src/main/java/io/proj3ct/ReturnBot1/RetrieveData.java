package io.proj3ct.ReturnBot1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * Класс для извлечения данных из базы данных.
 */
public class RetrieveData {

    private DatabaseConnection databaseConnection = new DatabaseConnection();
    /**
     * Метод для извлечения строки данных из таблицы AnswersData по id_question
     * @param id идентификатор строки
     * @param data текст необходимый для работы с бд
     */
    public String getDataById(int id, String data) {
        databaseConnection.createAnswersDataTableQuery();

        String selectAnswersFromDataTable = "SELECT * FROM AnswersData WHERE id_question = ?";

        try (Connection conn = databaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(selectAnswersFromDataTable)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
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
                return "Данные не найдены для id_question: " + id;
            }
        } catch (SQLException e) {
            System.out.println("Ошибка извлечения данных: " + e.getMessage());
            return "Ошибка извлечения данных: " + e.getMessage();

        }
        return "";
    }
}
