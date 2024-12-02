package io.proj3ct.ReturnBot1;

import io.proj3ct.ReturnBot1.datebase.DatabaseConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.DriverManager;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;
/**
 * Тестовый класс для проверки функциональности класса DatabaseConnection.
 */
public class DatabaseConnectionTest {

    private DatabaseConnection databaseConnection;
    /**
     * Настраивает тестовую среду перед каждым тестом.
     * Создает экземпляр DatabaseConnection с заданными параметрами подключения.
     */
    @BeforeEach
    public void setUp() {
        // Передаем параметры в конструктор
        String dbUrl = "jdbc:postgresql://localhost:5432/testdb";
        String dbUser  = "testuser";
        String dbPassword = "testpassword";
        databaseConnection = new DatabaseConnection(dbUrl, dbUser , dbPassword);
    }
    /**
     * Тестирует метод подключения к базе данных.
     * Проверяет, что соединение не является null и что метод DriverManager.getConnection
     * был вызван с правильными параметрами.
     *
     * @throws Exception если происходит ошибка при подключении к базе данных
     */
    @Test
    public void testConnect() throws Exception {
        // Мокаем Connection и статические методы DriverManager
        Connection mockConnection = Mockito.mock(Connection.class);

        try (MockedStatic<DriverManager> mockedDriverManager = mockStatic(DriverManager.class)) {
            // Настройка поведения мока
            mockedDriverManager.when(() -> DriverManager.getConnection(anyString(), anyString(), anyString()))
                    .thenReturn(mockConnection);

            // Проверяем соединение
            Connection connection = databaseConnection.connect();
            assertNotNull(connection, "Connection should not be null");

            // Проверяем, что соединение было установлено
            mockedDriverManager.verify(() -> DriverManager.getConnection("jdbc:postgresql://localhost:5432/testdb"
                    , "testuser", "testpassword"));
        }
    }
}
