package io.proj3ct.ReturnBot1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Модульные тесты для класса {@link TelegramBot}.
 * Этот класс использует Mockito для создания моков зависимостей и тестирования поведения бота в различных состояниях.
 */
class TelegramBotTest {

    private TelegramBot telegramBot;
    private messageLogic mockLogic;

    /**
     * Настраивает тестовую среду перед каждым методом тестирования.
     * Инициализирует мок экземпляра {@link messageLogic} и {@link TelegramBot} для тестирования.
     */
    @BeforeEach
    void setUp() {
        mockLogic = mock(messageLogic.class);
        telegramBot = new TelegramBot("testBot", "testToken", mockLogic);
    }

    /**
     * Тестирует поведение бота при ожидании email.
     * Проверяет, что состояние пользователя изменяется на "awaiting_email",
     * и что соответствующий метод вызывается у замоканного LogicBrain.
     */
    @Test
    void testWorksWithMail_AwaitingEmail() {
        long userId = 12345L;
        String messageText = "/question";
        String currentState = null; // Пользователь еще не в состоянии ожидания

        // Ввод данных
        telegramBot.worksWithMail(Mockito.mock(Update.class), messageText, userId, currentState);

        // Проверка, что состояние пользователя изменилось
        assertEquals("awaiting_email", telegramBot.userStates.get(userId));

        // Проверка, что сообщение было отправлено
        verify(mockLogic).slogic(messageText);
    }

    /**
     * Тестирует поведение бота при получении корректного email в состоянии ожидания email.
     * Проверяет, что email сохраняется, состояние пользователя меняется на "awaiting_question",
     * и правильное сообщение обрабатывается замоканным LogicBrain.
     */
    @Test
    void testWorksWithMail_AwaitingEmail_CorrectEmail() {
        long userId = 12345L;
        String messageText = "test@example.com";
        String currentState = "awaiting_email";

        // Создаем мок Update и Message
        Update mockUpdate = mock(Update.class);
        Message mockMessage = mock(Message.class);

        // Настраиваем поведение мока
        when(mockUpdate.getMessage()).thenReturn(mockMessage);
        when(mockMessage.getText()).thenReturn(messageText);

        // Настраиваем mockLogic для корректного ответа
        when(mockLogic.handleEmailInput(messageText)).thenReturn("Почта указана корректно, напишите ваш вопрос");

        // Ввод данных
        telegramBot.userStates.put(userId, currentState);
        telegramBot.worksWithMail(mockUpdate, messageText, userId, currentState);

        // Проверка, что email был сохранен и состояние пользователя изменилось
        assertEquals(messageText, telegramBot.userMails.get(userId));
        assertEquals("awaiting_question", telegramBot.userStates.get(userId));

        // Проверка, что сообщение было обработано
        verify(mockLogic).handleEmailInput(messageText);
    }

    /**
     * Тестирует поведение бота при получении вопроса в состоянии ожидания вопроса.
     * Проверяет, что email отправляется с правильным вопросом,
     * и что состояние пользователя и email очищаются.
     */
    @Test
    void testWorksWithMail_AwaitingQuestion() {
        long userId = 12345L;
        String messageText = "Какой адрес у ИЕНИМ?";
        String currentState = "awaiting_question";
        String email = "test@example.com";

        // Создаем мок Update и Message
        Update mockUpdate = mock(Update.class);
        Message mockMessage = mock(Message.class);

        // Настраиваем поведение мока
        when(mockUpdate.getMessage()).thenReturn(mockMessage);
        when(mockMessage.getText()).thenReturn(messageText);

        // Настраиваем состояние пользователя
        telegramBot.userStates.put(userId, currentState);
        telegramBot.userMails.put(userId, email);

        // Ввод данных
        telegramBot.worksWithMail(mockUpdate, messageText, userId, currentState);

        // Проверка, что email отправлен с правильным вопросом
        verify(mockLogic).sendMail(email, messageText);

        // Проверка, что состояние пользователя и email очищены
        assertNull(telegramBot.userStates.get(userId));
        assertNull(telegramBot.userMails.get(userId));
    }
}
