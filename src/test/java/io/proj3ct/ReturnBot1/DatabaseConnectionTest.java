package io.proj3ct.ReturnBot1;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DatabaseConnectionTest {

    @Test
    public void testGetDB_URL() {
        DatabaseConnection connection = new DatabaseConnection();
        assertEquals(System.getenv("bdUrl"), connection.getDB_URL());
    }

    @Test
    public void testGetDB_USER() {
        DatabaseConnection connection = new DatabaseConnection();
        assertEquals(System.getenv("bdUser"), connection.getDB_USER());
    }

    @Test
    public void testGetDB_PASSWORD() {
        DatabaseConnection connection = new DatabaseConnection();
        assertEquals(System.getenv("bdPassword"), connection.getDB_PASSWORD());
    }

    @Test
    public void testCreateAllTableSuccess() {
        DatabaseConnection connection = mock(DatabaseConnection.class);
        when(connection.createAllTable()).thenReturn("Nice");
        assertEquals("Nice", connection.createAllTable());
    }

    @Test
    public void testCreateAllTableFailure() {
        DatabaseConnection connection = mock(DatabaseConnection.class);
        when(connection.createAllTable()).thenReturn("notNice");
        assertEquals("notNice", connection.createAllTable());
    }
}