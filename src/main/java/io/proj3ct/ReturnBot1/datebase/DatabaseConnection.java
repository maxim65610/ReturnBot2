package io.proj3ct.ReturnBot1.datebase;

import io.proj3ct.ReturnBot1.baseClasses.EnvironmentService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Класс для подключения к базе данных
 */
public class DatabaseConnection {
    private final String dbUrl;
    private final String dbUser;
    private final String dbPassword;

    /**
     * Конструктор для базы данных
     * dbUrl URL базы данных
     * dbUser имя пользователя базы данных
     * dbPassword пароль пользователя базы данных
     */
    public DatabaseConnection() {
        EnvironmentService environmentService = new EnvironmentService();
        dbUrl = environmentService.getBdUrl();
        dbUser = environmentService.getBdUser();
        dbPassword = environmentService.getBdPassword();
    }
    /**
     * Метод для установления соединения с базой данных
     * @return объект Connection
     */
    public Connection connect() {
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

}
