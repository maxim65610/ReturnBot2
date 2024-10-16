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
public class LogicBrainTest {

    private LogicBrain logicBrain;
    private Map<Long, String> userStates;
    private Map<Long, String> userMails;
    @BeforeEach
    void setUp() {
        // Создаем экземпляр LogicBrain
        logicBrain = new LogicBrain();
        userStates = new HashMap<>();
        userMails = new HashMap<>();
    }

    @Test
    /**
     * Тест для случая, когда пользователь ввел команду /start.
     * Проверяет, что ответ соответствует ожидаемому сообщению.
     */
    void testStartCommand() {
        String answer = logicBrain.slogic("/start");
        assertEquals("Привет, этот бот может помочь тебе понять куда ты хочешь поступить," +
                " пожалуйста пользуйся кнопками. Если у тебя остались вопросы, можешь воспользоваться командой /question." +
                " Если хотите начать работу напишите /work", answer);
    }

    @Test
    /**
     * Тест для случая, когда пользователь ввел команду /help.
     * Проверяет, что ответ соответствует ожидаемому сообщению.
     */
    void testHelpCommand() {
        String answer = logicBrain.slogic("/help");
        assertEquals("Привет, этот бот может помочь тебе понять куда ты хочешь поступить," +
                " пожалуйста пользуйся кнопками. Если у тебя остались вопросы, можешь воспользоваться командой /question." +
                " Если хотите начать работу напишите /work", answer);
    }

    /**
     * Тест для случая, когда пользователь ввел команду /work.
     * Проверяет, что ответ соответствует ожидаемому сообщению.
     */
    @Test
    void testWorkCommand() {
        String answer = logicBrain.slogic("/work");
        assertEquals("Вот все институты у которых ты можешь посмотреть факультеты:", answer);
    }

    @Test
    /**
     * Тест для случая, когда пользователь нажал кнопку ИЕНИМ.
     * Проверяет, что ответ соответствует ожидаемому сообщению.
     */
    void testInst1Command() {
        String answer = logicBrain.slogic("ИЕНИМ");
        assertEquals("Вот все факультеты которые есть в институте ИЕНИМ:", answer);
    }

    @Test
    /**
     * Тест для случая, когда пользователь нажал кнопку РТФ.
     * Проверяет, что ответ соответствует ожидаемому сообщению.
     */
    void testInst2Command() {
        String answer = logicBrain.slogic("РТФ");
        assertEquals("Вот все факультеты которые есть в институте РТФ:", answer);
    }

    @Test
    /**
     * Тест для случая, когда пользователь нажал кнопку ХТИ.
     * Проверяет, что ответ соответствует ожидаемому сообщению.
     */
    void testInst3Command() {
        String answer = logicBrain.slogic("ХТИ");
        assertEquals("Вот все факультеты которые есть в институте ХТИ:", answer);
    }

    @Test
    /**
     * Тест для случая, когда пользователь ввел некорректное сообщение.
     * Проверяет, что ответ соответствует ожидаемому сообщению.
     */
    void testDefaultCommand() {
        String answer = logicBrain.slogic("sdgashgwrhg");
        assertEquals("Привет, этот бот может помочь тебе понять куда ты хочешь поступить," +
                " пожалуйста пользуйся кнопками. Если у тебя остались вопросы, можешь воспользоваться командой /question." +
                " Если хотите начать работу напишите /work", answer);
    }
    @Test
    /**
     * Тест для случая, когда пользователь ввел корректный адрес электронной почты.
     * Проверяет, что ответ соответствует ожидаемому сообщению о корректности почты.
     */
    public void testHandleEmailInput_ValidEmail() {
        EmailSender emailSender = new EmailSender("user@example.com", "password123");
        logicBrain.setEmailSender(emailSender);
        String email = "test@example.com";
        String expectedResponse = "Почта указана корректно, напишите ваш вопрос";

        String actualResponse = logicBrain.handleEmailInput(email);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    /**
     * Тест для случая, когда пользователь ввел некорректный адрес электронной почты.
     * Проверяет, что ответ соответствует ожидаемому сообщению об ошибке.
     */
    public void testHandleEmailInput_InvalidEmail() {
        EmailSender emailSender = new EmailSender("user@example.com", "password123");
        logicBrain.setEmailSender(emailSender);
        String email = "invalid-email";
        String expectedResponse = "Адрес электронной почты был указан неправильно отправьте его ещё раз";

        String actualResponse = logicBrain.handleEmailInput(email);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    /**
     * Тест для проверки отправки электронной почты.
     * Проверяет, что метод sendEmail вызывается с правильными параметрами.
     */
    void testSendMail() {
        EmailSender mockEmailSender = Mockito.mock(EmailSender.class);
        logicBrain.setEmailSender(mockEmailSender);
        String mailMessage = "test@example.com";
        String question = "Как подать заявку?";

        // Выполняем метод sendMail
        logicBrain.sendMail(mailMessage, question);

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

        String result = logicBrain.worksWithMail(update, "/question", userId, null, userStates, userMails);

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
        logicBrain.setEmailSender(mockEmailSender);

        String result = logicBrain.worksWithMail(update, "valid@example.com", userId, "awaiting_email", userStates, userMails);

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
        logicBrain.setEmailSender(mockEmailSender);

        String result = logicBrain.worksWithMail(update, "invalid-email", userId, "awaiting_email", userStates, userMails);

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
        logicBrain.setEmailSender(mockEmailSender);

        String result = logicBrain.worksWithMail(update, "Это мой вопрос", userId, "awaiting_question", userStates, userMails);

        assertEquals(null, userStates.get(userId)); // Состояние должно быть удалено
        assertEquals(null, userMails.get(userId)); // Почта должна быть удалена
        assertEquals("Ваш вопрос отправлен", result);

        // Проверяем, что метод отправки почты был вызван
        verify(mockEmailSender).sendEmail(eq(mockEmailSender.getUsername()), eq("Вопрос от абитуриента valid@example.com"), eq("Это мой вопрос"));
    }
}




