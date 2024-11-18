package io.proj3ct.ReturnBot1.datebase;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * Класс для создания таблиц
 */
public class DatebaseTables {
    /**
     * Объект для подключения к базе данных.
     */
    private DatabaseConnection databaseConnection;
    /**
     * Конструктор класса, который инициализирует объект подключения к базе данных.
     *
     * @param databaseConnection Объект для подключения к базе данных.
     */
    public DatebaseTables(DatabaseConnection databaseConnection){
        this.databaseConnection = databaseConnection;
    }
    /**
     * Метод для создания таблицы AnswersData в базе данных.
     * Таблица предназначена для хранения данных о вопросах, ответах и связанной информации.
     * Если таблица уже существует, она не будет создана повторно.
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
     * Метод для создания таблицы DepartsInfo в базе данных.
     * Таблица предназначена для хранения информации о департаментах.
     * Если таблица уже существует, она не будет создана повторно.
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
    /**
     * Метод для создания таблицы RegistrationDataTable в базе данных.
     * Таблица предназначена для хранения данных о регистрации пользователей.
     * Если таблица уже существует, она не будет создана повторно.
     */
    public void createRegistrationDataTable() {

        String registrationDataTableQuery = """
        CREATE TABLE IF NOT EXISTS RegistrationDataTable (
        id_chat text PRIMARY KEY, 
        name text NOT NULL,
        surname text NOT NULL, 
        school_сlass text NOT NULL,  
        mail text NOT NULL,
        dispatch text NOT NULL,
        year_end_school int NOT NULL,
        department text NOT NULL
        );""";
        try {
            Connection conn = databaseConnection.connect();
            if (conn != null ) {
                try (Statement stmt = conn.createStatement()) {
                    stmt.executeUpdate(registrationDataTableQuery);
                }
            } else {
                System.out.println("Не удалось создать таблицу: соединение с базой данных не установлено.");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка создания таблицы: " + e.getMessage());
        }

    }
    public void createDispatchDataTable() {
        String dispatchDataTableQuery = """
        CREATE TABLE IF NOT EXISTS DispatchDataTable (
        id int PRIMARY KEY, 
        text text NOT NULL,
        time text NOT NULL, 
        category text NOT NULL,  
        department text NOT NULL
        );""";

        try (Connection conn = databaseConnection.connect()) {
            if (conn != null) {
                try (Statement stmt = conn.createStatement()) {
                    stmt.executeUpdate(dispatchDataTableQuery);
                }
            } else {
                System.out.println("Не удалось создать таблицу: соединение с базой данных не установлено.");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка создания таблицы: " + e.getMessage());
            e.printStackTrace(); // Для получения более подробной информации о проблеме
        }
    }

}

