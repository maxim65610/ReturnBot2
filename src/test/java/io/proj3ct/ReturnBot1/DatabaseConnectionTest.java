package io.proj3ct.ReturnBot1;

import io.proj3ct.ReturnBot1.DatabaseConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import static org.mockito.Mockito.*;

/**
 * Класс для тестирования логики работы класса CommonMessageLogic.
 * Содержит тесты для различных команд и методов обработки ввода пользователя.
 */
class DatabaseConnectionTest {
    DatabaseConnection  databaseConnection = new DatabaseConnection();
    private String DB_URL;
    private String DB_USER;
    private String DB_PASSWORD;


    @BeforeEach
    void DatabaseConnection() {
        DB_URL = System.getenv("bdUrl");
        DB_USER = System.getenv("bdUser");
        DB_PASSWORD = System.getenv("bdPassword");

    }

    @Test
    void testConnect_Success() throws Exception {
        // Подменяем статический метод DriverManager.getConnection() с помощью Mockito
        Connection mockConnection = mock(Connection.class);
        mockStatic(DriverManager.class);
        when(DriverManager.getConnection(anyString(), anyString(), anyString())).thenReturn(mockConnection);

        // Вызываем метод connect()
        Connection connection = databaseConnection.connect();

        // Проверяем, что соединение не равно null assertNotNull(connection);
        // Проверяем, что DriverManager.getConnection был вызван verifyStatic(DriverManager.class);
        DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }


}