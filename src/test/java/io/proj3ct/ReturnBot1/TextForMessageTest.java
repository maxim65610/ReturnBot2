package io.proj3ct.ReturnBot1;

import io.proj3ct.ReturnBot1.baseClasses.TextForMessage;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Содержит тесты для проверки корректности ответов на команды и запросы,
 * которые обрабатываются методом {@link TextForMessage#setTheText(String)}.
 */
class TextForMessageTest {

    private final TextForMessage textForMessage;
    /**
     * Конструктор дял TextForMessageTest.
     */
    private TextForMessageTest(){
        textForMessage = new TextForMessage();
    }
    /**
     * Тест для команд /start, /work, /testAbit и /question.
     * Проверяет, что метод handleMessage возвращает ожидаемые ответы для каждой из указанных команд.
     */
    @Test
    public void testCommands() {
        String answerCommandDefault = textForMessage.setTheText("/start");
        String answerCommandWork = textForMessage.setTheText("/work");
        String answerCommandTestAbit = textForMessage.setTheText("/testabit");
        String answerCommandQuestion = textForMessage.setTheText("/question");

        String ExpectedMessageDefault = "Привет, этот бот может помочь тебе понять куда ты хочешь поступить," +
                " пожалуйста пользуйся кнопками.\n" +
                "Если у тебя остались вопросы, можешь воспользоваться командой /question.\n" +
                "Если хотите начать работу, напишите /work.\n" +
                "Также у тебя есть возможность пройти тест на то, какое направление вам больше подходит," +
                " просто напиши /testabit.\n" +
                "Если вы хотите получить больший функционал бота воспользуйтесь /authorization";
        String ExpectedMessageWork = "Вот все институты, у которых ты можешь посмотреть факультеты:";
        String ExpectedMessageTestAbit = "Вы начали проходить тестирование по выбору факультета, " +
                "выберите один предмет из этих трех:";
        String ExpectedMessageQuestion = "Пожалуйста, отправьте свой вопрос:";
        assertEquals(ExpectedMessageDefault, answerCommandDefault);
        assertEquals(ExpectedMessageWork, answerCommandWork);
        assertEquals(ExpectedMessageTestAbit, answerCommandTestAbit);
        assertEquals(ExpectedMessageQuestion, answerCommandQuestion);

    }
    /**
     Тестирует метод handleMessage, проверяя корректность ответов на различные команды.
     * Ожидается, что метод вернет правильные сообщения для следующих команд:
     * ИЕНИМ
     * РТФ
     * ХТИ
     * correctMail
     * notСorrectMail
     * questionHasBeenSend
     * userPassedTest
     * resultTestABI
     */
    @Test
    public void testDepartCommands(){
        String answerCommandInstIENIM = textForMessage.setTheText("ИЕНИМ");
        String answerCommandInstRTF = textForMessage.setTheText("РТФ");
        String answerCommandInstHTI = textForMessage.setTheText("ХТИ");

        String ExpectedMessageInstIENIM = "Вот все факультеты, которые есть в институте ИЕНИМ:";
        String ExpectedMessageInstRTF = "Вот все факультеты, которые есть в институте РТФ:";
        String ExpectedMessageInstHTI = "Вот все факультеты, которые есть в институте ХТИ:";

        assertEquals(ExpectedMessageInstIENIM, answerCommandInstIENIM);
        assertEquals(ExpectedMessageInstRTF, answerCommandInstRTF);
        assertEquals(ExpectedMessageInstHTI, answerCommandInstHTI);

    }

}



