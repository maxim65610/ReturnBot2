package io.proj3ct.ReturnBot1;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Класс с тестами для проверки работы создания таблицы и подключения к таблице.
 */
class DatabaseConnectionTest {

    /**
     *Тест для проверки подключения к таблице.
     */
    @Test
    void testConnect() {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.connect("jdbc:postgresql://localhost:5432/tgBot", "postgres", "root");
        assertNotNull(connection);
    }

    /**
     * Тест для проверки работы создания таблицы.
     */
    @Test
    void testCreateAllTable() {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String res = databaseConnection.createAllTable("jdbc:postgresql://localhost:5432/tgBot", "postgres", "root");
        assertEquals("Nice",res);
        // Add assertions to check if table is created successfully
    }
}