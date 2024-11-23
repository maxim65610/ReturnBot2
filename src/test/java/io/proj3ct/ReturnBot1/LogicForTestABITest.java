package io.proj3ct.ReturnBot1;


import io.proj3ct.ReturnBot1.departmentsAndTest.LogicForTestABI;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Содержит тесты, проверяющие корректность работы методов получения состояний пользователя и данных из базы.
 */
class LogicForTestABITest {

    private LogicForTestABI logicForTestABI;
     Map<Long, String> resultsTestAbi;
     Map<Long, String> userStatesForTest;
     Map<Long, List<String>> choiceABI;
    /**
     * Конструктор дял LogicForTestABITest.
     */
    private LogicForTestABITest(){
        logicForTestABI = new LogicForTestABI();
        resultsTestAbi = new HashMap<>();
        userStatesForTest = new HashMap<>();
        choiceABI = new HashMap<>();
    }
    /**
     * Тестирует метод getUser StatesForTest, когда состояние пользователя отсутствует.
     * Ожидается, что метод вернет "0".
     */
    @Test
    public void testGetUserStatesForTestNoState() {
        Long userId = 1L;
        assertEquals("0", logicForTestABI.getUserStatesForTest(userId));
    }
    /**
     * Проверяет, что метод возвращает "0", когда состояния пользователей пусты.
     */
    @Test
    void testGetUserStatesForTestEmpty() {
        Long userId = 1L;
        String result = logicForTestABI.getUserStatesForTest(userId);
        assertEquals("0", result, "Expected state for empty user states should be '0'");
    }
    /**
     * Тестирует метод getDataBd, чтобы убедиться, что он возвращает список данных.
     * Ожидается, что список данных не должен быть null и должен быть пустым.
     */
    @Test
    void testGetDataBd() {
        Long userId = 1L;
        String messageText = "/testabit";
        String data = "100";
        List<String> dataBD = logicForTestABI.worksWithTestABI(messageText, userId, data);
        assertNotNull(dataBD);
        assertTrue(dataBD.isEmpty());
    }
    /**
     * Тестирует метод worksWithTestAPI, чтобы убедиться, что он возвращает список данных.
     * Ожидается, что список данных не должен быть null.
     */
    @Test
    void testWorksWithTestAPI() {
        Long userId = 1L;
        String messageText = "/testabit";
        String data = "100";
        List<String> dataBD = logicForTestABI.worksWithTestABI(messageText, userId, data);
        assertNotNull(dataBD);
    }
}
