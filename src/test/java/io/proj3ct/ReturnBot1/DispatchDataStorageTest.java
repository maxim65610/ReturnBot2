package io.proj3ct.ReturnBot1;

import io.proj3ct.ReturnBot1.dispatch.DispatchDataStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для класса DispatchDataStorage.
 */
public class DispatchDataStorageTest {

    private DispatchDataStorage dispatchDataStorage;
    /**
     * Подготовка к тестам: создаем новый экземпляр DispatchDataStorage.
     */
    @BeforeEach
    public void setUp() {
        dispatchDataStorage = new DispatchDataStorage();
    }
    /**
     * Проверяем, что текст диспетча устанавливается и возвращается корректно.
     */
    @Test
    public void testSetAndGetDispatchText() {
        Long chatID = 1L;
        String text = "Текст диспетча";

        dispatchDataStorage.setDispatchText(chatID, text);
        String actualText = dispatchDataStorage.getDispatchText(chatID);

        assertEquals(text, actualText, "Текст диспетча должен быть установлен и возвращен правильно");
    }
    /**
     * Проверяем, что время диспетча устанавливается и возвращается корректно.
     */
    @Test
    public void testSetAndGetDispatchTime() {
        Long chatID = 1L;
        String time = "12:00";

        dispatchDataStorage.setDispatchTime(chatID, time);
        String actualTime = dispatchDataStorage.getDispatchTime(chatID);

        assertEquals(time, actualTime, "Время диспетча должно быть установлено и возвращено правильно");
    }
    /**
     * Проверяем, что категория диспетча устанавливается и возвращается корректно.
     */
    @Test
    public void testSetAndGetDispatchCategory() {
        Long chatID = 1L;
        String category = "Категория 1";

        dispatchDataStorage.setDispatchCategory(chatID, category);
        String actualCategory = dispatchDataStorage.getDispatchCategory(chatID);

        assertEquals(category, actualCategory, "Категория диспетча должна быть установлена и возвращена правильно");
    }
    /**
     * Проверяем, что отдел диспетча устанавливается и возвращается корректно.
     */
    @Test
    public void testSetAndGetDispatchDepartment() {
        Long chatID = 1L;
        String department = "Отдел 1";

        dispatchDataStorage.setDispatchDepartment(chatID, department);
        String actualDepartment = dispatchDataStorage.getDispatchDepartment(chatID);

        assertEquals(department, actualDepartment, "Отдел диспетча должен быть установлен и возвращен правильно");
    }
    /**
     * Проверяем, что возвращается null для несуществующего идентификатора чата.
     */
    @Test
    public void testGetDispatchText_NonExistentChatID() {
        Long chatID = 999L;
        assertNull(dispatchDataStorage.getDispatchText(chatID), "Должно вернуть null для несуществующего идентификатора чата");
    }
    /**
     * Проверяем, что возвращается null для несуществующего идентификатора чата.
     */
    @Test
    public void testGetDispatchTime_NonExistentChatID() {
        Long chatID = 999L;
        assertNull(dispatchDataStorage.getDispatchTime(chatID), "Должно вернуть null для несуществующего идентификатора чата");
    }
    /**
     * Проверяем, что возвращается null для несуществующего идентификатора чата.
     */
    @Test
    public void testGetDispatchCategory_NonExistentChatID() {
        Long chatID = 999L;
        assertNull(dispatchDataStorage.getDispatchCategory(chatID), "Должно вернуть null для несуществующего идентификатора чата");
    }
    /**
     * Проверяем, что возвращается null для несуществующего идентификатора чата.
     */
    @Test
    public void testGetDispatchDepartment_NonExistentChatID() {
        Long chatID = 999L;
        assertNull(dispatchDataStorage.getDispatchDepartment(chatID), "Должно вернуть null для несуществующего идентификатора чата");
    }
}