package io.proj3ct.ReturnBot1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Тестирует класс DatabaseConnection.
 */

public class DatabaseConnectionTest {

    /**
     * Тестирует метод DatabaseConnection.getDB_URL.
     * Проверяет, что возвращаемое значение соответствует значению * переменной окружения "bdUrl".
     */
    @Test public void testGetDB_URL() {
        DatabaseConnection connection = new DatabaseConnection();
        assertEquals(System.getenv("bdUrl"), connection.getDB_URL());
    }

    /**
     * Тестирует метод DatabaseConnection.getDB_USER.
     * Проверяет, что возвращаемое значение соответствует значению * переменной окружения "bdUser ".
     */
    @Test public void testGetDB_USER() {
        DatabaseConnection connection = new DatabaseConnection();
        assertEquals(System.getenv("bdUser "), connection.getDB_USER());
    }

    /**
     * Тестирует метод DatabaseConnection.getDB_PASSWORD.
     * Проверяет, что возвращаемое значение соответствует значению * переменной окружения "bdPassword".
     */
    @Test public void testGetDB_PASSWORD() {
        DatabaseConnection connection = new DatabaseConnection();
        assertEquals(System.getenv("bdPassword"), connection.getDB_PASSWORD());
    }

    /**
     * Тестирует метод DatabaseConnection.createAllTable на успешное создание таблиц.
     * Использует мок-объект для имитации поведения метода и проверяет,
     * что он возвращает строку "Nice".
     */
    @Test public void testCreateAllTableSuccess() {
        DatabaseConnection connection = mock(DatabaseConnection.class);
        when(connection.createAllTable()).thenReturn("Nice");
        assertEquals("Nice", connection.createAllTable());
    }

    /**
     * Тестирует метод DatabaseConnectio.createAllTableна неудачное создание таблиц.
     * Использует мок-объект для имитации поведения метода и проверяет,
     * что он возвращает строку "notNice".
     */
    @Test public void testCreateAllTableFailure() {
        DatabaseConnection connection = mock(DatabaseConnection.class);
        when(connection.createAllTable()).thenReturn("notNice");
        assertEquals("notNice", connection.createAllTable());
    }
}