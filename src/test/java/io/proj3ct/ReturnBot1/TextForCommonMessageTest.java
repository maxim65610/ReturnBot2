package io.proj3ct.ReturnBot1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


class TextForCommonMessageTest {

    private TextForCommonMessage textForCommonMessage;
    private Map<Long, String> userStates;
    private Map<Long, String> userMails;
    @BeforeEach
    void setUp() {
        // Создаем экземпляр LogicBrain
        textForCommonMessage = new TextForCommonMessage();
        userStates = new HashMap<>();
        userMails = new HashMap<>();
    }

    @Test
    /**
     * Тест для команд start,help,work,test и проверяет их ответы.
     */
    public void testCommands() {
        String answerCommandDefault = textForCommonMessage.handleMessage("/start");
        String answerCommandWork = textForCommonMessage.handleMessage("/work");
        String answerCommandTestAbit = textForCommonMessage.handleMessage("/testAbit");

        String ExpectedMessageDefault = "Привет, этот бот может помочь тебе понять куда ты хочешь поступить, " +
                "пожалуйста пользуйся кнопками. Если у тебя остались вопросы, можешь воспользоваться " +
                "командой /question. Если хотите начать работу, напишите /work. Также у тебя есть " +
                "возможность пройти тест на то, какое направление вам больше подходит, просто напиши /testAbit";
        String ExpectedMessageWork = "Вот все институты, у которых ты можешь посмотреть факультеты:";
        String ExpectedMessageTestAbit = "Вы начали проходить тестирование по выбору факультета, " +
                "выберите один предмет из этих трех:";

        assertEquals(ExpectedMessageDefault, answerCommandDefault);
        assertEquals(ExpectedMessageWork, answerCommandWork);
        assertEquals(ExpectedMessageTestAbit, answerCommandTestAbit);


    }


    /**
     * Тест для случаев ИЕНИМ, РТФ, ХТИ.
     */

    @Test
    public void testDepartCommands(){
        String answerCommandInstIENIM = textForCommonMessage.handleMessage("ИЕНИМ");
        String answerCommandInstRTF = textForCommonMessage.handleMessage("РТФ");
        String answerCommandInstHTI = textForCommonMessage.handleMessage("ХТИ");

        String ExpectedMessageInstIENIM = "Вот все факультеты, которые есть в институте ИЕНИМ:";
        String ExpectedMessageInstRTF = "Вот все факультеты, которые есть в институте РТФ:";
        String ExpectedMessageInstHTI = "Вот все факультеты, которые есть в институте ХТИ:";

        assertEquals(ExpectedMessageInstIENIM, answerCommandInstIENIM);
        assertEquals(ExpectedMessageInstRTF, answerCommandInstRTF);
        assertEquals(ExpectedMessageInstHTI, answerCommandInstHTI);
    }

}



