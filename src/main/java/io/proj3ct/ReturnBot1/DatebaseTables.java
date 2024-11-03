package io.proj3ct.ReturnBot1;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * Класс для создания таблиц
 */
public class DatebaseTables {

    private DatabaseConnection databaseConnection;
    /**
     * Метод для создания таблицы AnswersData в базе данных
     */
    public void createAnswersDataTableQuery() {

        String createAnswersDataTableQuery  = """
        CREATE TABLE IF NOT EXISTS AnswersData (
        id_question int PRIMARY KEY, 
        question text NOT NULL,
        answer1 text NOT NULL,
        answer2 text NOT NULL,
        answer3 text NOT NULL, 
        cash1 text NOT NULL,
        cash2 text NOT NULL,
        cash3 text NOT NULL
        );""";

        try {
            Connection conn = databaseConnection.connect();
            if (conn != null ) {
                try (Statement stmt = conn.createStatement()) {
                    stmt.executeUpdate(createAnswersDataTableQuery);
                }
            } else {
                System.out.println("Не удалось создать таблицу: соединение с базой данных не установлено.");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка создания таблицы: " + e.getMessage());
        }
    }
    /**
     * Метод для создания таблицы DepartsInfoTable в базе данных
     */
    public void createDepartsInfoTableQuery() {

        String createDepartsInfoTableQuery  = """
        CREATE TABLE IF NOT EXISTS DepartsInfo (
        id_depart text PRIMARY KEY, 
        info text NOT NULL  
        );""";

        try {
            Connection conn = databaseConnection.connect();
            if (conn != null ) {
                try (Statement stmt = conn.createStatement()) {
                    stmt.executeUpdate(createDepartsInfoTableQuery);
                }
            } else {
                System.out.println("Не удалось создать таблицу: соединение с базой данных не установлено.");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка создания таблицы: " + e.getMessage());
        }
    }
}

