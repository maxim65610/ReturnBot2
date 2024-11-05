package io.proj3ct.ReturnBot1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


class CommonMessageLogicTest {

    private CommonMessageLogic commonMessageLogic;
    private Map<Long, String> userStates;
    private Map<Long, String> userMails;
    @BeforeEach
    void setUp() {
        // Создаем экземпляр LogicBrain
        commonMessageLogic = new CommonMessageLogic();
        userStates = new HashMap<>();
        userMails = new HashMap<>();
    }

    @Test
    /**
     * Тест для команд start,help,work,test и проверяет их ответы.
     */
    public void testCommands() {
        String answerCommandDefault = commonMessageLogic.handleMessage("/start");
        String answerCommandWork = commonMessageLogic.handleMessage("/work");
        String answerCommandTestAbit = commonMessageLogic.handleMessage("/testAbit");
        String answerCommandAuthorization = commonMessageLogic.handleMessage("/authorization");
        String answerCommandUserDataDell = commonMessageLogic.handleMessage("/userDataDell");
        String answerCommandUserDataChange = commonMessageLogic.handleMessage("/userDataChange");

        String ExpectedMessageDefault = "Привет, этот бот может помочь тебе понять куда ты хочешь поступить," +
                " пожалуйста пользуйся кнопками.\n" +
                "Если у тебя остались вопросы, можешь воспользоваться командой /question.\n" +
                "Если хотите начать работу, напишите /work.\n" +
                "Также у тебя есть возможность пройти тест на то, какое направление вам больше подходит," +
                " просто напиши /testAbit.\n" +
                "Если вы хотите получить больший функционал бота воспользуйтесь /authorization";
        String ExpectedMessageWork = "Вот все институты, у которых ты можешь посмотреть факультеты:";
        String ExpectedMessageTestAbit = "Вы начали проходить тестирование по выбору факультета, " +
                "выберите один предмет из этих трех:";
        String ExpectedMessageAuthorization = "Здравствуйте, начинаем.\n" +
                "Всего будет 4 пункта, которые вы должны указать: имя, фамилию, класс, почту. \n" +
                "Введите имя:\n";
        String ExpectedMessageDataDell = "Ваши данные успешно удалены";
        String ExpectedMessageDataChange = "Выберите данные, которые хотите поменять:\n" +
                "/userDataChangeName - поменять имя\n" +
                "/userDataChangeSurname - поменять фамилию\n" +
                "/userDataChangeClass - поменять класс\n" +
                "/userDataChangeMail - поменять почту";

        assertEquals(ExpectedMessageDefault, answerCommandDefault);
        assertEquals(ExpectedMessageWork, answerCommandWork);
        assertEquals(ExpectedMessageTestAbit, answerCommandTestAbit);
        assertEquals(ExpectedMessageAuthorization, answerCommandAuthorization);
        assertEquals(ExpectedMessageDataDell, answerCommandUserDataDell);
        assertEquals(ExpectedMessageDataChange, answerCommandUserDataChange);

    }


    /**
     * Тест для случаев ИЕНИМ, РТФ, ХТИ.
     */

    @Test
    public void testDepartCommands(){
        String answerCommandInstIENIM = commonMessageLogic.handleMessage("ИЕНИМ");
        String answerCommandInstRTF = commonMessageLogic.handleMessage("РТФ");
        String answerCommandInstHTI = commonMessageLogic.handleMessage("ХТИ");

        String ExpectedMessageInstIENIM = "Вот все факультеты, которые есть в институте ИЕНИМ:";
        String ExpectedMessageInstRTF = "Вот все факультеты, которые есть в институте РТФ:";
        String ExpectedMessageInstHTI = "Вот все факультеты, которые есть в институте ХТИ:";

        assertEquals(ExpectedMessageInstIENIM, answerCommandInstIENIM);
        assertEquals(ExpectedMessageInstRTF, answerCommandInstRTF);
        assertEquals(ExpectedMessageInstHTI, answerCommandInstHTI);
    }

}



