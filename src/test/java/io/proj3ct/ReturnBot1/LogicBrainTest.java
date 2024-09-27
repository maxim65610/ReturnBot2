package io.proj3ct.ReturnBot1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * тесты класа LogicBrain
 */
class LogicBrainTest {
    @Test
    @DisplayName("/echo command")
    /**
     * тест для случая, когда пользователь ввел текст(не /start и не /help)
     */
    void testEchoCommand(){
        LogicBrain logicBrain = new LogicBrain();
        String answer = logicBrain.slogic("Hello");
        Assertions.assertEquals("Вы ввели: " + "Hello", answer);
    }

    @Test
    @DisplayName("/help command")
    /**
     * тест для случая, когда пользователь ввел /help
     */
    void testHelpCommand() {
        LogicBrain logicBrain = new LogicBrain();
        String answer = logicBrain.slogic("/help");
        Assertions.assertEquals("Привет, я могу повторять за тобой.", answer);
    }

    @Test
    @DisplayName("/start command")
    /**
     * тест для случая, когда пользователь ввел /start
     */
    void testStartCommand() {
        LogicBrain logicBrain = new LogicBrain();
        String answer = logicBrain.slogic("/start");
        Assertions.assertEquals("Привет, я могу повторять за тобой.", answer);
    }

}