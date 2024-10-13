package io.proj3ct.ReturnBot1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Класс для подключения к базе данных, и
 * создания ее по необходимости
 */

public class DatabaseConnection {

    /**
     * Метод для установления соединения с базой данных
     * @param DB_URL URL базы данных
     * @param DB_USER имя пользователя базы данных
     * @param DB_PASSWORD пароль пользователя базы данных
     * @return объект Connection
     */
    public Connection connect(String DB_URL, String DB_USER, String DB_PASSWORD) {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Подключение к базе данных выполнено успешно!");
        } catch (ClassNotFoundException e) {
            System.out.println("Драйвер PostgreSQL не найден: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Ошибка подключения к базе данных: " + e.getMessage());
        }
        return connection;
    }

    /**
     * Метод для создания таблицы AnswersData в базе данных
     * @param DB_URL URL базы данных
     * @param DB_USER имя пользователя базы данных
     * @param DB_PASSWORD пароль пользователя базы данных
     */
    public String createAllTable(String DB_URL, String DB_USER, String DB_PASSWORD) {
        String sql1 = """
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
            Connection conn = connect(DB_URL, DB_USER, DB_PASSWORD);
            if (conn != null) {
                try (Statement stmt = conn.createStatement()) {
                    stmt.executeUpdate(sql1);
                    System.out.println("Таблица AnswersData успешно создана.");
                    return "Nice";
                }
            } else {
                System.out.println("Не удалось создать таблицу: соединение с базой данных не установлено.");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка создания таблицы: " + e.getMessage());
        }

        return "notNice";
    }

}
