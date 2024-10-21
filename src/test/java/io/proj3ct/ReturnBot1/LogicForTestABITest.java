package io.proj3ct.ReturnBot1;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

/**
 * Тесты для класса {@link LogicForTestABI}.
 * Этот класс содержит модульные тесты для проверки
 * функциональности логики обработки тестов ABI.
 */
class LogicForTestABITest {

    @InjectMocks
    private LogicForTestABI logic;

    @Mock
    private RetrieveData mockRetrieveData;

    /**
     * Инициализация перед каждым тестом.
     * Настраивает моки и инъекции зависимостей.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Тестирует метод {@link LogicForTestABI#getUserStatesForTest(Long)}.
     * Проверяет, что метод возвращает "0", когда состояния пользователей пусты.
     */
    @Test
    void testGetUserStatesForTest_Empty() {
        String result = logic.getUserStatesForTest(1L);
        assertEquals("0", result, "Expected state for empty user states should be '0'");
    }

    /**
     * Тестирует метод {@link LogicForTestABI#getUserStatesForTest(Long)}.
     * Проверяет, что метод возвращает ожидаемое состояние, когда оно установлено.
     */
    @Test
    void testGetUserStatesForTest_WithState() {
        logic.worksWithTestAPI("/testAbit", 1L, "100");
        String result = logic.getUserStatesForTest(1L);
        assertEquals("awaiting_testABI_1", result, "Expected state should match the one set");
    }

    /**
     * Тестирует метод {@link LogicForTestABI#removeUserStatesForTest(Long)}.
     * Проверяет, что состояние пользователя удаляется правильно.
     */
    @Test
    void testRemoveUserStatesForTest() {
        logic.worksWithTestAPI("/testAbit", 1L, "100");
        logic.removeUserStatesForTest(1L);
        assertEquals("0", logic.getUserStatesForTest(1L), "Expected state to be removed");
    }

    /**
     * Тестирует метод {@link LogicForTestABI#worksWithTestAPI(String, Long, String)}.
     * Проверяет, что метод возвращает корректные данные для ожидаемого состояния теста.
     */
    @Test
    void testWorksWithTestAPI() {
        when(mockRetrieveData.getDataById(101, "question")).thenReturn("Sample Question");
        when(mockRetrieveData.getDataById(101, "answer1")).thenReturn("Answer 1");
        when(mockRetrieveData.getDataById(101, "answer2")).thenReturn("Answer 2");
        when(mockRetrieveData.getDataById(101, "answer3")).thenReturn("Answer 3");
        when(mockRetrieveData.getDataById(101, "cash1")).thenReturn("Cash 1");
        when(mockRetrieveData.getDataById(101, "cash2")).thenReturn("Cash 2");

        logic.worksWithTestAPI("/testAbit", 1L, "100");

        List<String> dataBD = logic.worksWithTestAPI("awaiting_testABI_1", 1L, "100");
        assertNotNull(dataBD, "Data should not be null");
        assertEquals(6, dataBD.size(), "Expected size should be 6");
        assertEquals("Sample Question", dataBD.get(0));
        assertEquals("Answer 1", dataBD.get(1));
        assertEquals("Answer 2", dataBD.get(2));
        assertEquals("Answer 3", dataBD.get(3));
        assertEquals("Cash 1", dataBD.get(4));
        assertEquals("Cash 2", dataBD.get(5));
    }

    /**
     * Тестирует метод {@link LogicForTestABI#getResult(long)}.
     * Проверяет, что метод возвращает правильный факультет в зависимости от выбора пользователя.
     */
    @Test
    void testGettingResult() {
        Long userID = 1L;
        List<String> choices = Arrays.asList("1000", "1001", "1000", "1000", "1001", "1000", "1000", "1001", "1000"); // Добавим "1000" несколько раз для теста

        // Инициализация теста
        logic.worksWithTestAPI("/testAbit", userID, "100");

        // Симуляция выбора пользователя
        for (String choice : choices) {
            logic.worksWithTestAPI("", userID, choice);
        }

        // Имитация возврата данных о факультете
        when(mockRetrieveData.getDataById(1000, "cash3")).thenReturn("Faculty A");
        when(mockRetrieveData.getDataById(1001, "cash3")).thenReturn("Faculty B");

        // Завершение теста
        logic.worksWithTestAPI("", userID, "-"); // Завершаем тест для получения результата

        // Проверяем результат через getResult
        String result = logic.getResult(userID);

        // Ожидаем, что наиболее частый выбор будет "Faculty A"
        assertEquals("Faculty A", result, "Expected result should match 'Faculty A'");
    }
}









