package io.proj3ct.ReturnBot1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


class TextForMessageTest {

    private TextForMessage textForMessage;

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
        String answerCommandDefault = textForMessage.handleMessage("/start");
        String answerCommandWork = textForMessage.handleMessage("/work");
        String answerCommandTestAbit = textForMessage.handleMessage("/testAbit");
        String answerCommandQuestion = textForMessage.handleMessage("/question");
        String answerCommandAuthorization = textForMessage.handleMessage("/authorization");
        String answerCommandUserDataDell = textForMessage.handleMessage("/userDataDell");
        String answerCommandUserDataChange = textForMessage.handleMessage("/userDataChange");

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
        String ExpectedMessageQuestion = "Пожалуйста, отправьте свой вопрос:";
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
        assertEquals(ExpectedMessageQuestion, answerCommandQuestion);
        assertEquals(ExpectedMessageAuthorization, answerCommandAuthorization);
        assertEquals(ExpectedMessageDataDell, answerCommandUserDataDell);
        assertEquals(ExpectedMessageDataChange, answerCommandUserDataChange);

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
        String answerCommandInstIENIM = textForMessage.handleMessage("ИЕНИМ");
        String answerCommandInstRTF = textForMessage.handleMessage("РТФ");
        String answerCommandInstHTI = textForMessage.handleMessage("ХТИ");
        String answerCommandCorrectMail = textForMessage.handleMessage("correctMail");
        String answerCommandUnCorrectMail = textForMessage.handleMessage("notСorrectMail");
        String answerCommandQuestionHasBeenSend = textForMessage.handleMessage("questionHasBeenSend");
        String answerCommandUserPassedTest = textForMessage.handleMessage("userPassedTest");
        String answerCommandResultTestABI = textForMessage.handleMessage("resultTestABI");
        String answerCommandSuccessfulReg = textForMessage.handleMessage("successfulReg");
        String answerCommandEnterName = textForMessage.handleMessage("name");
        String answerCommandEnterSurname = textForMessage.handleMessage("surname");
        String answerCommandEnterMail = textForMessage.handleMessage("mail");
        String answerCommandEnterClass = textForMessage.handleMessage("class");
        String answerCommandSuccessfulName = textForMessage.handleMessage("successful_name");
        String answerCommandSuccessfulSurname = textForMessage.handleMessage("successful_surname");
        String answerCommandSuccessfulMail = textForMessage.handleMessage("successful_mail");
        String answerCommandSuccessfulClass = textForMessage.handleMessage("successful_class");


        String ExpectedMessageInstIENIM = "Вот все факультеты, которые есть в институте ИЕНИМ:";
        String ExpectedMessageInstRTF = "Вот все факультеты, которые есть в институте РТФ:";
        String ExpectedMessageInstHTI = "Вот все факультеты, которые есть в институте ХТИ:";
        String ExpectedMessageCorrectMail = "Почта указана корректно, напишите ваш вопрос";
        String ExpectedMessageUnCorrectMail = "Адрес электронной почты был указан неправильно " +
                "отправьте его ещё раз";
        String ExpectedMessageQuestionHasBeenSend = "Ваш вопрос отправлен";
        String ExpectedMessageUserPassedTest = "Поздравляю, вы прошли тест." +
                " Чтобы узнать результат напишите /testres";
        String ExpectedMessageResultTestABI = "Вам больше всего подходит факультет: ";
        String ExpectedMessageSuccessfulReg ="Авторизация окончена успешно.\n" +
                "Если хотите проверить данные воспользуйтесь /userInfo\n" +
                "Если хотите удалить данные воспользуйтесь /userDataDell\n" +
                "Если хотите поменять данные воспользуйтесь /userDataChange";
        String ExpectedMessageEnterName = "Введите имя:";
        String ExpectedMessageEnterSurname = "Введите фамилию:";
        String ExpectedMessageEnterMail = "Введите почту:";
        String ExpectedMessageEnterClass = "Введите класс:";
        String ExpectedMessageSuccessfulName = "Имя успешно изменено";
        String ExpectedMessageSuccessfulSurname = "Фамилия успешно изменена";
        String ExpectedMessageSuccessfulMail = "Почта успешно изменена";
        String ExpectedMessageSuccessfulClass = "Класс успешно изменен";

        assertEquals(ExpectedMessageInstIENIM, answerCommandInstIENIM);
        assertEquals(ExpectedMessageInstRTF, answerCommandInstRTF);
        assertEquals(ExpectedMessageInstHTI, answerCommandInstHTI);
        assertEquals(ExpectedMessageCorrectMail, answerCommandCorrectMail);
        assertEquals(ExpectedMessageUnCorrectMail, answerCommandUnCorrectMail);
        assertEquals(ExpectedMessageQuestionHasBeenSend, answerCommandQuestionHasBeenSend);
        assertEquals(ExpectedMessageUserPassedTest, answerCommandUserPassedTest);
        assertEquals(ExpectedMessageResultTestABI, answerCommandResultTestABI);
        assertEquals(ExpectedMessageSuccessfulReg, answerCommandSuccessfulReg);
        assertEquals(ExpectedMessageEnterName, answerCommandEnterName);
        assertEquals(ExpectedMessageEnterSurname, answerCommandEnterSurname);
        assertEquals(ExpectedMessageEnterMail, answerCommandEnterMail);
        assertEquals(ExpectedMessageEnterClass, answerCommandEnterClass);
        assertEquals(ExpectedMessageSuccessfulName, answerCommandSuccessfulName);
        assertEquals(ExpectedMessageSuccessfulSurname, answerCommandSuccessfulSurname);
        assertEquals(ExpectedMessageSuccessfulMail, answerCommandSuccessfulMail);
        assertEquals(ExpectedMessageSuccessfulClass, answerCommandSuccessfulClass);
    }

}



