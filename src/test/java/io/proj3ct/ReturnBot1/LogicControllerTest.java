package io.proj3ct.ReturnBot1;

import io.proj3ct.ReturnBot1.Command.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тестовый класс для LogicController.
 * Этот класс содержит тесты для проверки корректной работы методов LogicController,
 * в частности для команд DefaultCommand и TestAbitCommand.
 */
public class LogicControllerTest {
    private LogicController logicController;
    private DefaultCommand defaultCommand;
    private TestAbitCommand testAbitCommand;
    /**
     * Инициализация объектов перед каждым тестом.
     * В данном методе создаются экземпляры команд DefaultCommand и TestAbitCommand,
     * а также инициализируется LogicController с этими командами.
     */
    @BeforeEach
    public void setUp() {
        defaultCommand = new DefaultCommand();
        testAbitCommand = new TestAbitCommand();
        logicController = new LogicController(testAbitCommand, defaultCommand);
    }
    /**
     * Тестирует выполнение команды DefaultCommand.
     * Этот тест проверяет, что при получении сообщения с командой "Abdad"
     * возвращает правильный ответ из  DefaultCommand, а именно MessageConstants.DEFAULT_RESPONSE.
     */
    @Test
    public void testDefaultCommand() {
        long userId = 123L;
        String messageText = "Abdad";
        boolean flagForKeyboard = false;
        // Вызываем метод
        List<String> response = logicController.handleMessage(userId, messageText, flagForKeyboard);

        // Проверяем, что метод возвращает правильный результат
        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(MessageConstants.DEFAULT_RESPONSE, response.get(0));
    }
    /**
     * Тестирует выполнение команды TestAbitCommand.
     * Этот тест проверяет, что при получении сообщения с командой "/testabit"
     */
    @Test
    public void testTestAbitCommand(){
        long userId = 123L;
        String messageText = "/testabit";
        boolean flagForKeyboard = false;

        List<String> response = logicController.handleMessage(userId, messageText, flagForKeyboard);

        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(MessageConstants.TEST_ABIT_COMMAND_RESPONSE, response.get(0));
        assertEquals(messageText, response.get(1));
    }

}