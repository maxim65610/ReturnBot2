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
    private final String dbUrl;
    private final String dbUser;
    private final String dbPassword;
    /**
     * Конструктор для базы данных
     * @ DB_URL URL базы данных
     * @ DB_USER имя пользователя базы данных
     * @ DB_PASSWORD пароль пользователя базы данных
     */
    public DatabaseConnection() {
        dbUrl = System.getenv("bdUrl");
        dbUser = System.getenv("bdUser");
        dbPassword = System.getenv("bdPassword");
    }
    /**
     * Метод для установления соединения с базой данных
     * @ DB_URL URL базы данных
     * @ DB_USER имя пользователя базы данных
     * @ DB_PASSWORD пароль пользователя базы данных
     * @return объект Connection
     */
    Connection connect() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (ClassNotFoundException e) {
            System.out.println("Драйвер PostgreSQL не найден: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Ошибка подключения к базе данных: " + e.getMessage());
        }
        return connection;
    }
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
            Connection conn = connect();
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
     * Метод для создания таблицы AnswersData в базе данных
     */
    public void createDepartsInfoTableQuery() {
        String createDepartsInfoTableQuery  = """
        CREATE TABLE IF NOT EXISTS DepartsInfo (
        id_depart text PRIMARY KEY, 
        info text NOT NULL  
        );""";

        try {
            Connection conn = connect();
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
