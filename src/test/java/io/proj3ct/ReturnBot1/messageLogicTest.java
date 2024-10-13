package io.proj3ct.ReturnBot1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Класс для тестирования логики работы класса LogicBrain.
 * Содержит тесты для различных команд и методов обработки ввода пользователя.
 */
class messageLogicTest {

    private messageLogic messageLogic;

    @BeforeEach
    void setUp() {
        // Создаем экземпляр LogicBrain
        messageLogic = new messageLogic();
    }

    @Test
    /**
     * Тест для случая, когда пользователь ввел команду /start.
     * Проверяет, что ответ соответствует ожидаемому сообщению.
     */
    void testStartCommand() {
        String answer = messageLogic.slogic("/start");
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
        String answer = messageLogic.slogic("/help");
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
        String answer = messageLogic.slogic("/work");
        assertEquals("Вот все институты у которых ты можешь посмотреть факультеты:", answer);
    }

    @Test
    /**
     * Тест для случая, когда пользователь нажал кнопку ИЕНИМ.
     * Проверяет, что ответ соответствует ожидаемому сообщению.
     */
    void testInst1Command() {
        String answer = messageLogic.slogic("ИЕНИМ");
        assertEquals("Вот все факультеты которые есть в институте ИЕНИМ:", answer);
    }

    @Test
    /**
     * Тест для случая, когда пользователь нажал кнопку РТФ.
     * Проверяет, что ответ соответствует ожидаемому сообщению.
     */
    void testInst2Command() {
        String answer = messageLogic.slogic("РТФ");
        assertEquals("Вот все факультеты которые есть в институте РТФ:", answer);
    }

    @Test
    /**
     * Тест для случая, когда пользователь нажал кнопку ХТИ.
     * Проверяет, что ответ соответствует ожидаемому сообщению.
     */
    void testInst3Command() {
        String answer = messageLogic.slogic("ХТИ");
        assertEquals("Вот все факультеты которые есть в институте ХТИ:", answer);
    }

    @Test
    /**
     * Тест для случая, когда пользователь ввел некорректное сообщение.
     * Проверяет, что ответ соответствует ожидаемому сообщению.
     */
    void testDefaultCommand() {
        String answer = messageLogic.slogic("sdgashgwrhg");
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
}



