package io.proj3ct.ReturnBot1;

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;

/**
 * тесты класа LogicBrain
 */
class LogicBrainTest {


    @Test

    /**
     * Тест для случая, когда пользователь ввел /start
     */
    void testStartCommand() {
        LogicBrain logicBrain = new LogicBrain();
        String answer = logicBrain.slogic("/start");
        Assertions.assertEquals("Привет, этот бот может помочь тебе понять куда ты хочешь поступить," +
                " пожалуйста пользуйся кнопками. Если у тебя остались вопросы, можешь воспользоваться командой /question." +
                " Если хотите начать работу напишите /work", answer);
    }

    @Test
    /**
     * Тест для случая, когда пользователь ввел /help
     */
    void testHelpCommand() {
        LogicBrain logicBrain = new LogicBrain();
        String answer = logicBrain.slogic("/help");
        Assertions.assertEquals("Привет, этот бот может помочь тебе понять куда ты хочешь поступить," +
                " пожалуйста пользуйся кнопками. Если у тебя остались вопросы, можешь воспользоваться командой /question." +
                " Если хотите начать работу напишите /work", answer);
    }

    @Test
    /**
     * Тест для случая, нажатия кнопки ИЕНИМ
     */
    void testInst1Command() {
        LogicBrain logicBrain = new LogicBrain();
        String answer = logicBrain.slogic("ИЕНИМ");
        Assertions.assertEquals("Вот все факультеты которые есть в институте ИЕНИМ:", answer);
    }

    @Test
    /**
     * Тест для случая, нажатия кнопки РТФ
     */
    void testInst2Command() {
        LogicBrain logicBrain = new LogicBrain();
        String answer = logicBrain.slogic("РТФ");
        Assertions.assertEquals("Вот все факультеты которые есть в институте РТФ:", answer);
    }

    @Test
    /**
     * Тест для случая, нажатия кнопки ХТИ
     */
    void testInst3Command() {
        LogicBrain logicBrain = new LogicBrain();
        String answer = logicBrain.slogic("ХТИ");
        Assertions.assertEquals("Вот все факультеты которые есть в институте ХТИ:", answer);
    }

    @Test

    /**
     * Тест для случая, когда пользователь ввел некоректное сообщение
     */
    void testDefaultCommand() {
        LogicBrain logicBrain = new LogicBrain();
        String answer = logicBrain.slogic("sdgashgwrhg");
        Assertions.assertEquals("Привет, этот бот может помочь тебе понять куда ты хочешь поступить," +
                " пожалуйста пользуйся кнопками. Если у тебя остались вопросы, можешь воспользоваться командой /question." +
                " Если хотите начать работу напишите /work", answer);
    }
}


