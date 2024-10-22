package io.proj3ct.ReturnBot1;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Тестовый класс для проверки логики работы с тестами ABI в классе {@link LogicForTestABI}.
 * Использует Mockito для создания мока зависимостей и проверки поведения методов.
 */
public class LogicForTestABITest {

    /** Экземпляр класса, который тестируется. */
    private LogicForTestABI logicForTestABI;

    /** Мок объекта для извлечения данных. */
    private RetrieveData retrieveDataMock;
    private Map<Long, String> resultsTestAbi;


    /**
     * Настройка перед каждым тестом.
     * Инициализирует мок и экземпляр класса {@link LogicForTestABI}.
     */
    @BeforeEach
    public void setUp() {
        retrieveDataMock = Mockito.mock(RetrieveData.class);
        logicForTestABI = new LogicForTestABI();
        logicForTestABI.retrieveData = retrieveDataMock;  // Замена реального объекта на мок
    }
    /**
     * Тестирует начало теста ABI.
     * Проверяет, что при отправке команды "/test" состояние пользователя изменяется на "awaiting_testABI_1".
     */
    @Test
    public void testWorksWithTestAPI_StartTest() {
        Long userId = 1L;
        Map<Long, String> userStates = new HashMap<>();

        List<String> result = logicForTestABI.worksWithTestAPI("/test", userId, userStates, "-");

        assertEquals("awaiting_testABI_1", userStates.get(userId));
        assertTrue(result.isEmpty());
    }
    /**
     * Тестирует выбор ответа пользователем.
     * Проверяет, что после выбора ответа состояние пользователя изменяется на "awaiting_testABI_2",
     * и возвращается правильный вопрос.
     */
    @Test
    public void testWorksWithTestAPI_AnswerSelection() {
        Long userId = 1L;
        Map<Long, String> userStates = new HashMap<>();
        userStates.put(userId, "awaiting_testABI_1");

        // Мокаем данные для выбора
        when(retrieveDataMock.getDataById(101, "question")).thenReturn("Question 1");
        when(retrieveDataMock.getDataById(101, "answer1")).thenReturn("Answer 1");
        when(retrieveDataMock.getDataById(101, "answer2")).thenReturn("Answer 2");
        when(retrieveDataMock.getDataById(101, "answer3")).thenReturn("Answer 3");

        List<String> result = logicForTestABI.worksWithTestAPI("some message", userId, userStates, "100");

        assertEquals("awaiting_testABI_2", userStates.get(userId));
        assertEquals("Question 1", result.get(0));
    }
    /**
     * Тестирует получение результата на основе выборов пользователя.
     * Проверяет, что при наличии двух выборов "1000" результатом является "Faculty A".
     */
    @Test
    public void testGettingResult() {
        Long userId = 1L;
        Map<Long, List<String>> choiceABI = new HashMap<>();
        choiceABI.put(userId, Arrays.asList("1000", "1001", "1000")); // Два выбора 1000

        when(retrieveDataMock.getDataById(1000, "cash3")).thenReturn("Faculty A");
        when(retrieveDataMock.getDataById(1001, "cash3")).thenReturn("Faculty B");

        String result = logicForTestABI.gettingresult(userId, choiceABI);

        assertEquals("Faculty A", result);
    }
    /**
     * Тестирует получение результата факультета на основе пользовательского ID.
     * Проверяет, что возвращаемая строка содержит правильный факультет для данного пользователя.
     */
    @Test
    public void testGetResult() {
        Long userId = 1L;
        Map<Long, String> results = new HashMap<>();
        results.put(userId, "Faculty A");

        logicForTestABI.setResultsTestAbi(results);

        String result = logicForTestABI.getResult(userId);

        assertEquals("Вам больше всего подходит факультет: Faculty A", result);
    }
}

