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

        String answerCommandTestAbit = textForMessage.setTheText("/testAbit");
        String answerCommandQuestion = textForMessage.setTheText("/question");

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
        String answerCommandCorrectMail = textForMessage.setTheText("correctMail");
        String answerCommandUnCorrectMail = textForMessage.setTheText("notСorrectMail");
        String answerCommandQuestionHasBeenSend = textForMessage.setTheText("questionHasBeenSend");
        String answerCommandUserPassedTest = textForMessage.setTheText("userPassedTest");
        String answerCommandResultTestABI = textForMessage.setTheText("resultTestABI");
        String answerCommandSuccessfulReg = textForMessage.setTheText("successfulReg");
        String answerCommandEnterName = textForMessage.setTheText("name");
        String answerCommandEnterSurname = textForMessage.setTheText("surname");
        String answerCommandEnterMail = textForMessage.setTheText("io/proj3ct/ReturnBot1/mail");
        String answerCommandEnterClass = textForMessage.setTheText("class");
        String answerCommandSuccessfulName = textForMessage.setTheText("successful_name");
        String answerCommandSuccessfulSurname = textForMessage.setTheText("successful_surname");
        String answerCommandSuccessfulMail = textForMessage.setTheText("successful_mail");
        String answerCommandSuccessfulClass = textForMessage.setTheText("successful_class");


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



