package io.proj3ct.ReturnBot1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Тестирование класса LogicController.
 * Этот класс содержит тесты для проверки функциональности методов LogicController.
 * Мы используем Mockito для замоканивания зависимости DepartmentsInfo,
 * чтобы изолировать логику тестируемого класса от реального взаимодействия с базой данных.
 */
class LogicControllerTest {
    private LogicController logicController;
    private DepartmentsInfo mockDepartmentsInfo;

    /**
     * Инициализация перед каждым тестом.
     * В этом методе мы создаем замоканный объект DepartmentsInfo и инициализируем экземпляр LogicController.
     */
    @BeforeEach
    void setUp() {
        mockDepartmentsInfo = mock(DepartmentsInfo.class);

        // Используем конструктор для инициализации logicController
        logicController = new LogicController(mockDepartmentsInfo);
    }

    /**
     * Тестирование метода handleMessage в случае, если факультет найден.
     * В этом тесте проверяется, что метод handleMessage правильно обрабатывает входные данные,
     * когда метод DepartmentsInfo.extract(String) возвращает информацию о факультете.
     */
    @Test
    void testHandleMessage_withDepartmentFound() {
        String input = "ИЕНИМ";
        String expectedResponse = "Информация о факультете ИЕНИМ";

        when(mockDepartmentsInfo.extract(input)).thenReturn(expectedResponse);

        List<String> result = logicController.handleMessage(123L, input, true);

        assertEquals(2, result.size());
        assertEquals(expectedResponse, result.get(0));
        assertEquals(input, result.get(1));
    }

    /**
     * Тестирование метода handleMessage в случае, если факультет не найден.
     * В этом тесте проверяется, что метод handleMessage корректно работает, когда метод
     * DepartmentsInfo.extract(String) возвращает null, что означает отсутствие информации о факультете.
     */
    @Test
    void testHandleMessage_withDepartmentNotFound() {
        String input = "Неверный факультет";
        when(mockDepartmentsInfo.extract(input)).thenReturn(null);

        List<String> result = logicController.handleMessage(123L, input, true);

        assertEquals(2, result.size());
        assertEquals("Неверный факультет", result.get(0));
        assertEquals(input, result.get(1));
    }

    /**
     * Тестирование метода handleMessage для обработки обычных сообщений.
     * В этом тесте проверяется, что метод handleMessage корректно обрабатывает сообщения
     * без участия системы факультетов, когда флаг для клавиатуры установлен в false.
     */
    @Test
    public void testHandleMessage_DefaultCase() {
        // Проверяем, что обработка обычного сообщения работает
        List<String> result = logicController.handleMessage(123L, "Any message", false);

        // Проверяем, что обычное сообщение было возвращено
        assertNotNull(result);
        assertTrue(result.contains("Any message"));
    }
}
