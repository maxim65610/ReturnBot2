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

/**
 * Класс для тестирования логики работы класса LogicBrain.
 * Содержит тесты для различных команд и методов обработки ввода пользователя.
 */
class MessageLogicTest {

    private MessageLogic messageLogic;
    private Map<Long, String> userStates;
    private Map<Long, String> userMails;
    @BeforeEach
    void setUp() {
        // Создаем экземпляр LogicBrain
        messageLogic = new MessageLogic();
        userStates = new HashMap<>();
        userMails = new HashMap<>();
    }

    @Test
    /**
     * Тест для команд start,help,work,test и проверяет их ответы.
     */
    void testCommands() {
        String answer1 = messageLogic.slogic("/start");
        String answer2 = messageLogic.slogic("/help");
        String answer3 = messageLogic.slogic("/work");
        String answer4 = messageLogic.slogic("/test");

        assertEquals("Привет, этот бот может помочь тебе понять куда ты хочешь поступить," +
                " пожалуйста пользуйся кнопками. Если у тебя остались вопросы, можешь воспользоваться " +
                "командой /question. Если хотите начать работу напишите /work. Также у тебя есть возможность" +
                " пройти тест на то, какое направление вам больше подходит, просто напишите /test", answer1);
        assertEquals("Привет, этот бот может помочь тебе понять куда ты хочешь поступить," +
                " пожалуйста пользуйся кнопками. Если у тебя остались вопросы, можешь воспользоваться " +
                "командой /question. Если хотите начать работу напишите /work. Также у тебя есть возможность" +
                " пройти тест на то, какое направление вам больше подходит, просто напишите /test", answer2);
        assertEquals("Вот все институты у которых ты можешь посмотреть факультеты:", answer3);
        assertEquals("Вы начали проходить тестирование по выбору факультета," +
                " выберите один предмет из этих трех:", answer4);

    }


    /**
     * Тест для случаев ИЕНИМ, РТФ, ХТИ.
     */

    @Test
    public void testDepartCommands(){
        String answer1 = messageLogic.slogic("ИЕНИМ");
        String answer2 = messageLogic.slogic("РТФ");
        String answer3 = messageLogic.slogic("ХТИ");

        assertEquals("Вот все факультеты которые есть в институте ИЕНИМ:", answer1);

        assertEquals("Вот все факультеты которые есть в институте РТФ:", answer2);

        assertEquals("Вот все факультеты которые есть в институте ХТИ:", answer3);
    }
    @Test
    /**
     * Тест для случая, когда пользователь ввел корректный адрес электронной почты.
     * Проверяет, что ответ соответствует ожидаемому сообщению о корректности почты.
     */
    public void testHandleEmailInput_ValidEmail() {
        EmailSender emailSender = new EmailSender("user@example.com", "password123");
        messageLogic.setEmailSender(emailSender);
        String email = "test@example.com";
        String expectedResponse = "Почта указана корректно, напишите ваш вопрос";

        String actualResponse = messageLogic.handleEmailInput(email);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    /**
     * Тест для случая, когда пользователь ввел некорректный адрес электронной почты.
     * Проверяет, что ответ соответствует ожидаемому сообщению об ошибке.
     */
    public void testHandleEmailInput_InvalidEmail() {
        EmailSender emailSender = new EmailSender("user@example.com", "password123");
        messageLogic.setEmailSender(emailSender);
        String email = "invalid-email";
        String expectedResponse = "Адрес электронной почты был указан неправильно отправьте его ещё раз";

        String actualResponse = messageLogic.handleEmailInput(email);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    /**
     * Тест для проверки отправки электронной почты.
     * Проверяет, что метод sendEmail вызывается с правильными параметрами.
     */
    void testSendMail() {
        EmailSender mockEmailSender = Mockito.mock(EmailSender.class);
        messageLogic.setEmailSender(mockEmailSender);
        String mailMessage = "test@example.com";
        String question = "Как подать заявку?";

        // Выполняем метод sendMail
        messageLogic.sendMail(mailMessage, question);

        // Проверяем, что метод sendEmail был вызван с правильными параметрами
        verify(mockEmailSender).sendEmail(eq(mockEmailSender.getUsername()), eq("Вопрос от абитуриента " + mailMessage), eq(question));
    }

    /**
     * Проверяет начальное состояние работы пользователя с функцией отправки сообщения на почту через /question
     */
    @Test
    void testWorksWithMail_QuestionCommand() {
        Update update = Mockito.mock(Update.class);
        Message message = Mockito.mock(Message.class);
        Mockito.when(update.getMessage()).thenReturn(message);
        Mockito.when(message.getText()).thenReturn("/question");
        Long userId = 1L;

        String result = messageLogic.worksWithMail(update, "/question", userId, null, userStates, userMails);

        assertEquals("awaiting_email", userStates.get(userId));
        assertEquals("Пожалуйста, отправьте свою почту", result);
    }

    /**
     * Проверяет состояние awaiting_email с корректной почтой
     */


    @Test
    void testWorksWithMail_AwaitingEmailValidInput() {
        Update update = Mockito.mock(Update.class);
        Message message = Mockito.mock(Message.class);
        Mockito.when(update.getMessage()).thenReturn(message);
        Mockito.when(message.getText()).thenReturn("valid@example.com");
        Long userId = 1L;
        userStates.put(userId, "awaiting_email");

        // Мокируем EmailSender
        EmailSender mockEmailSender = Mockito.mock(EmailSender.class);
        Mockito.when(mockEmailSender.isValidEmail("valid@example.com")).thenReturn(true);
        messageLogic.setEmailSender(mockEmailSender);

        String result = messageLogic.worksWithMail(update, "valid@example.com", userId, "awaiting_email", userStates, userMails);

        assertEquals("awaiting_question", userStates.get(userId));
        assertEquals("valid@example.com", userMails.get(userId));
        assertEquals("Почта указана корректно, напишите ваш вопрос", result);
    }



    /**
     * Проверяет состояние awaiting_email с некорректной почтой
     */


    @Test
    void testWorksWithMail_AwaitingEmailInvalidInput() {
        Update update = Mockito.mock(Update.class);
        Message message = Mockito.mock(Message.class);
        Mockito.when(update.getMessage()).thenReturn(message);
        Mockito.when(message.getText()).thenReturn("invalid-email");
        Long userId = 1L;
        userStates.put(userId, "awaiting_email");

        // Мокируем EmailSender
        EmailSender mockEmailSender = Mockito.mock(EmailSender.class);
        Mockito.when(mockEmailSender.isValidEmail("invalid-email")).thenReturn(false);
        messageLogic.setEmailSender(mockEmailSender);

        String result = messageLogic.worksWithMail(update, "invalid-email", userId, "awaiting_email", userStates, userMails);

        assertEquals(null, userMails.get(userId)); // Почта должна быть удалена
        assertEquals("awaiting_email", userStates.get(userId)); // Состояние не должно измениться
        assertEquals("Адрес электронной почты был указан неправильно отправьте его ещё раз", result);
    }

    /**
     * Проверяет состояние awaiting_question и то, что метод отправки почты был вызван
     */
    @Test
    void testWorksWithMail_AwaitingQuestion() {
        Update update = Mockito.mock(Update.class);
        Message message = Mockito.mock(Message.class);
        Mockito.when(update.getMessage()).thenReturn(message);
        Mockito.when(message.getText()).thenReturn("Это мой вопрос");
        Long userId = 1L;
        userStates.put(userId, "awaiting_question");
        userMails.put(userId, "valid@example.com");

        // Мокируем EmailSender
        EmailSender mockEmailSender = Mockito.mock(EmailSender.class);
        messageLogic.setEmailSender(mockEmailSender);

        String result = messageLogic.worksWithMail(update, "Это мой вопрос", userId, "awaiting_question", userStates, userMails);

        assertEquals(null, userStates.get(userId)); // Состояние должно быть удалено
        assertEquals(null, userMails.get(userId)); // Почта должна быть удалена
        assertEquals("Ваш вопрос отправлен", result);

        // Проверяем, что метод отправки почты был вызван
        verify(mockEmailSender).sendEmail(eq(mockEmailSender.getUsername()), eq("Вопрос от абитуриента valid@example.com"), eq("Это мой вопрос"));
    }
}



