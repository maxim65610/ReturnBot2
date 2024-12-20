package io.proj3ct.ReturnBot1.datebase;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс для создания таблиц в базе данных.
 */
public class DatebaseTables {
    /**
     * Объект для подключения к базе данных.
     */
    private final Logger logger = Logger.getLogger(DatebaseTables.class.getName());
    private DatabaseConnection databaseConnection;

    /**
     * Конструктор класса, который инициализирует объект подключения к базе данных.
     *
     * @param databaseConnection Объект для подключения к базе данных.
     */
    public DatebaseTables(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    /**
     * Метод для создания таблицы AnswersData в базе данных.
     * Таблица предназначена для хранения данных о вопросах, ответах и связанной информации.
     * Если таблица уже существует, она не будет создана повторно.
     */
    public void createAnswersDataTableQuery() {
        String createAnswersDataTableQuery = """
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

        try (Connection conn = databaseConnection.connect();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(createAnswersDataTableQuery);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка создания таблицы: " + e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Метод для создания таблицы DepartsInfo в базе данных.
     * Таблица предназначена для хранения информации о департаментах.
     * Если таблица уже существует, она не будет создана повторно.
     */
    public void createDepartsInfoTableQuery() {
        String createDepartsInfoTableQuery = """
        CREATE TABLE IF NOT EXISTS DepartsInfo (
        id_depart text PRIMARY KEY, 
        info text NOT NULL  
        );""";

        try (Connection conn = databaseConnection.connect();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(createDepartsInfoTableQuery);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка создания таблицы: " + e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Метод для создания таблицы RegistrationDataTable в базе данных.
     * Таблица предназначена для хранения данных о регистрации пользователей.
     * Если таблица уже существует, она не будет создана повторно.
     */
    public void createRegistrationDataTable() {
        String registrationDataTable = """
        CREATE TABLE IF NOT EXISTS RegistrationDataTable (
        id_chat text PRIMARY KEY, 
        name text NOT NULL,
        surname text NOT NULL, 
        school_class text NOT NULL,  
        mail text NOT NULL
        );""";

        try (Connection conn = databaseConnection.connect();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(registrationDataTable);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка создания таблицы: " + e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
