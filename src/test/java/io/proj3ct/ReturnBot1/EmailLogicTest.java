package io.proj3ct.ReturnBot1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


/**
 * Класс тестов для проверки логики обработки электронной почты в классе EmailLogic.
 */
class EmailLogicTest {

    private EmailLogic emailLogic;
    private EmailSender emailSender;
    private TextForMessage textForMessage;
    private Update update;

    /**
     * Конструктор дял LogicControllerTest.
     */
    private void EmailLogicTest(){
        emailLogic = new EmailLogic();
        emailSender = Mockito.mock(EmailSender.class);
        textForMessage = Mockito.mock(TextForMessage.class);
        update = Mockito.mock(Update.class);
        MockitoAnnotations.openMocks(this);
    }
    /**
     * Создание объекта для тестов EmailLogicTest.
     */
    @BeforeEach
    void setUp() {
        EmailLogicTest();
    }
    /**
     * Тест для проверки получения состояния пользователя, когда оно отсутствует.
     */
    @Test
    void testGetUserStatesForEmail_NoStates() {
        Long userId = 123L;
        String state = emailLogic.getUserStatesForEmail(userId);
        assertEquals("0", state, "Должно возвращаться '0', если состояния нет.");
    }
    /**
     * Тест для проверки обработки команды /question.
     */
    @Test
    void testWorksWithMail_QuestionCommand() {
        Long userId = 123L;
        Update mockUpdate = mock(Update.class);
        Message mockMessage = mock(Message.class);
        when(mockUpdate.getMessage()).thenReturn(mockMessage);
        when(mockMessage.getText()).thenReturn("test@gmail.com");

        emailLogic.getReplyForWorkingWithMail(mockUpdate, "/question", userId, emailSender);
        assertEquals("awaiting_email", emailLogic.getUserStatesForEmail(userId),
                "Состояние должно быть 'awaiting_email'.");
    }
    /**
     * Тест для проверки корректного ввода электронной почты.
     */
    @Test
    void testWorksWithMail_CorrectEmail() {
        Long userId = 123L;
        Update mockUpdate = mock(Update.class);
        Message mockMessage = mock(Message.class);
        when(mockUpdate.getMessage()).thenReturn(mockMessage);
        when(mockMessage.getText()).thenReturn("test@gmail.com");
        emailLogic.getReplyForWorkingWithMail(mockUpdate, "/question", userId, emailSender);

        String validEmail = "test@gmail.com";
        when(mockUpdate.getMessage().getText()).thenReturn(validEmail);
        when(emailSender.isValidEmail(validEmail)).thenReturn(true);
        when(textForMessage.handleMessage("correctMail")).thenReturn("Электронная почта корректная.");

        String response = emailLogic.getReplyForWorkingWithMail(mockUpdate, validEmail, userId, emailSender);
        assertEquals("Почта указана корректно, напишите ваш вопрос", response);
        assertEquals("awaiting_question", emailLogic.getUserStatesForEmail(userId),
                "Состояние должно быть 'awaiting_question'.");
    }

}
