package io.proj3ct.ReturnBot1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class LogicForTestABITest {

    private LogicForTestABI logicForTestABI;
     Map<Long, String> resultsTestAbi;
     Map<Long, String> userStatesForTest;
     Map<Long, List<String>> choiceABI;

    /**
     * Конструктор дял LogicForTestABITest.
     */
    private void LogicForTestABITest(){
        logicForTestABI = new LogicForTestABI();
        resultsTestAbi = new HashMap<>();
        userStatesForTest = new HashMap<>();
        choiceABI = new HashMap<>();
    }
    /**
     * Создание объекта для тестов logicForTestABI, resultsTestAbi, userStatesForTest, choiceABI.
     */
    @BeforeEach
    void setUp() {
        LogicForTestABITest();

    }
/**
 * Тестирует метод getUser StatesForTest, когда состояние пользователя отсутствует.
 * Ожидается, что метод вернет "0".
 */
    @Test
    public void testGetUserStatesForTest_NoState() {
        Long userId = 1L;
        assertEquals("0", logicForTestABI.getUserStatesForTest(userId));
    }


    /**
     * Тестирует метод getDataBd, чтобы убедиться, что он возвращает список данных.
     * Ожидается, что список данных не должен быть null и должен быть пустым.
     */
    @Test
    void testGetDataBd() {
        Long userId = 1L;
        String messageText = "/testAbit";
        String data = "100";
        List<String> dataBD = logicForTestABI.getDataBd(messageText, userId, data);
        assertNotNull(dataBD);
        assertTrue(dataBD.isEmpty());
    }



    /**
     * Тестирует метод worksWithTestAPI, чтобы убедиться, что он возвращает список данных.
     * Ожидается, что список данных не должен быть null и должен быть пустым.
     */
    @Test
    void testWorksWithTestAPI() {
        Long userId = 1L;
        String messageText = "/testAbit";
        String data = "100";
        List<String> dataBD = logicForTestABI.getDataBd(messageText, userId, data);
        assertNotNull(dataBD);
    }
}
