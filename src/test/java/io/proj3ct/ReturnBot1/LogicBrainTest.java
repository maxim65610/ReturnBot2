package io.proj3ct.ReturnBot1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LogicBrainTest {
    @Test
    @DisplayName("/echo command")
    void testEchoCommand(){
        LogicBrain logicBrain = new LogicBrain();
        String answer = logicBrain.slogic("Hello");
        Assertions.assertEquals("Вы ввели: " + "Hello", answer);
    }

    @Test
    @DisplayName("/help command")
    void testHelpCommand() {
        LogicBrain logicBrain = new LogicBrain();
        String answer = logicBrain.slogic("/help");
        Assertions.assertEquals("Привет, я могу повторять за тобой.", answer);
    }

    @Test
    @DisplayName("/start command")
    void testStartCommand() {
        LogicBrain logicBrain = new LogicBrain();
        String answer = logicBrain.slogic("/start");
        Assertions.assertEquals("Привет, я могу повторять за тобой.", answer);
    }

}