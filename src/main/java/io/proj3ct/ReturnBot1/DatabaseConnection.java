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
    private String DB_URL;
    private String DB_USER;
    private String DB_PASSWORD;
    /**
     * Конструктор для базы данных
     * @ DB_URL URL базы данных
     * @ DB_USER имя пользователя базы данных
     * @ DB_PASSWORD пароль пользователя базы данных
     */
    public DatabaseConnection() {
        DB_URL = System.getenv("bdUrl");
        DB_USER = System.getenv("bdUser");
        DB_PASSWORD = System.getenv("bdPassword");
    }
    /**
     * Возвращает @param DB_URL
     */
    public String getDB_URL() {
        return DB_URL;
    }
    /**
     * Возвращает @param DB_USER
     */
    public String getDB_USER() {
        return DB_USER;
    }
    /**
     * Возвращает @param DB_PASSWORD
     */
    public  String getDB_PASSWORD() {
        return DB_PASSWORD;
    }
    /**
     * Метод для установления соединения с базой данных
     * @ DB_URL URL базы данных
     * @ DB_USER имя пользователя базы данных
     * @ DB_PASSWORD пароль пользователя базы данных
     * @return объект Connection
     */
    private Connection connect() {
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
     */
    public void createAllTable() {
        String AnswersDataTable = """
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

        String DepartsInfoTable = """
        CREATE TABLE IF NOT EXISTS DepartsInfo (
        id_depart text PRIMARY KEY, 
        info text NOT NULL  
        );""";

        try {
            Connection conn = connect();
            if (conn != null ) {
                try (Statement stmt = conn.createStatement()) {
                    stmt.executeUpdate(AnswersDataTable);
                    stmt.executeUpdate(DepartsInfoTable);

                }
            } else {
                System.out.println("Не удалось создать таблицу: соединение с базой данных не установлено.");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка создания таблицы: " + e.getMessage());
        }

    }
}
