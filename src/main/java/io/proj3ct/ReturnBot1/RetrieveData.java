package io.proj3ct.ReturnBot1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RetrieveData {

    /**
     * Метод для извлечения строки данных из таблицы AnswersData по id_question
     * @param DB_URL URL базы данных
     * @param DB_USER имя пользователя базы данных
     * @param DB_PASSWORD пароль пользователя базы данных
     * @param id идентификатор строки
     */
    public void getDataById(String DB_URL, String DB_USER, String DB_PASSWORD, int id) {
        String sql = "SELECT * FROM AnswersData WHERE id_question = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int id_question = rs.getInt("id_question");
                String question = rs.getString("question");
                String answer1 = rs.getString("answer1");
                String answer2 = rs.getString("answer2");
                String answer3 = rs.getString("answer3");
                String cash1 = rs.getString("cash1");
                String cash2 = rs.getString("cash2");
                String cash3 = rs.getString("cash3");

                System.out.println("id_question: " + id_question);
                System.out.println("question: " + question);
                System.out.println("answer1: " + answer1);
                System.out.println("answer2: " + answer2);
                System.out.println("answer3: " + answer3);
                System.out.println("cash1: " + cash1);
                System.out.println("cash2: " + cash2);
                System.out.println("cash3: " + cash3);
            } else {
                System.out.println("Данные не найдены для id_question: " + id);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка извлечения данных: " + e.getMessage());
        }
    }
}
