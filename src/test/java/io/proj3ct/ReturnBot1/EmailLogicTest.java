package io.proj3ct.ReturnBot1;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Тестовый класс для проверки функциональности {@link EmailLogic}.
 * Этот класс содержит модульные тесты, которые проверяют различные аспекты
 * обработки электронных писем и управления состояниями пользователей.
 */
public class EmailLogicTest {

    /**
     * Экземпляр {@link EmailLogic}, который будет тестироваться.
     */
    @InjectMocks
    private EmailLogic emailLogic;

    /**
     * Мок-объект {@link EmailSender}, используемый для проверки отправки электронных писем.
     */
    @Mock
    private EmailSender mockEmailSender;

    /**
     * Метод, который выполняется перед каждым тестом.
     * Он инициализирует моки и инъекции.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Тест для проверки состояния пользователя, когда список состояний пуст.
     * Ожидается, что метод вернет "0".
     */
    @Test
    void testGetUserStatesForEmail_Empty() {
        String result = emailLogic.getUserStatesForEmail(1L);
        assertEquals("0", result, "Expected state for empty user states should be '0'");
    }

    /**
     * Тест для проверки состояния пользователя, когда состояние установлено.
     * Ожидается, что метод вернет "awaiting_email".
     */
    @Test
    void testGetUserStatesForEmail_WithState() {
        emailLogic.worksWithMail(null, "/question", 1L, mockEmailSender);
        String result = emailLogic.getUserStatesForEmail(1L);
        assertEquals("awaiting_email", result, "Expected state should match the one set");
    }

    /**
     * Тест для проверки обработки валидного адреса электронной почты.
     * Ожидается, что метод вернет сообщение о корректности почты.
     */
    @Test
    void testWorksWithMail_ValidEmail() {
        Update mockUpdate = mock(Update.class);
        Message mockMessage = mock(Message.class);

        // Настройка мока для получения текста сообщения
        when(mockUpdate.getMessage()).thenReturn(mockMessage);
        when(mockMessage.getText()).thenReturn("test@gmail.com");

        // Симуляция отправки команды "/question"
        emailLogic.worksWithMail(mockUpdate, "/question", 1L, mockEmailSender);

        // Имитация метода проверки валидности электронной почты
        when(mockEmailSender.isValidEmail("test@gmail.com")).thenReturn(true);

        // Вызов метода с валидным адресом электронной почты
        String response = emailLogic.worksWithMail(mockUpdate, "test@gmail.com", 1L, mockEmailSender);

        // Проверка ожидаемого ответа
        assertEquals("Почта указана корректно, напишите ваш вопрос", response);
    }

    /**
     * Тест для проверки обработки невалидного адреса электронной почты.
     * Ожидается, что метод вернет сообщение об ошибке.
     */
    @Test
    void testWorksWithMail_InvalidEmail() {
        Update mockUpdate = mock(Update.class);
        Message mockMessage = mock(Message.class);
        when(mockUpdate.getMessage()).thenReturn(mockMessage);
        when(mockMessage.getText()).thenReturn("invalid-email");

        emailLogic.worksWithMail(mockUpdate, "/question", 1L, mockEmailSender);

        when(mockEmailSender.isValidEmail("invalid-email")).thenReturn(false);
        String response = emailLogic.worksWithMail(mockUpdate, "invalid-email", 1L, mockEmailSender);

        assertEquals("Адрес электронной почты был указан неправильно отправьте его ещё раз", response);
    }
}

