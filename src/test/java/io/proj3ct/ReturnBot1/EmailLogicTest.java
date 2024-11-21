package io.proj3ct.ReturnBot1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Тестовый класс для проверки логики отправки электронных писем в классе EmailLogic.
 */
public class EmailLogicTest {
    private EmailLogic emailLogic;
    private UsersData usersDataMock;
    private EmailSender emailSenderMock;
    private DatabaseConnection databaseConnectionMock;
    /**
     * Метод, выполняемый перед каждым тестом, для настройки моков и других зависимостей.
     */
    @Before
    public void setUp() {
        usersDataMock = mock(UsersData.class);
        emailSenderMock = mock(EmailSender.class);
        databaseConnectionMock = mock(DatabaseConnection.class);
        emailLogic = new EmailLogic(usersDataMock);
    }
    /**
     Тест для проверки получения состояния пользователя, когда оно отсутствует.
     */
    @Test
    public void testGetUserStatesForEmailNoStates() {
        Long userId = 123L;
        String state = emailLogic.getUserStatesForEmail(userId);
        assertEquals("0", state, "Должно возвращаться '0', если состояния нет.");
    }
    /**
     * Тест для проверки работы с функцией, когда пользователь не зарегистрирован.
     * Ожидается, что метод вернет сообщение об ошибке, если пользователь не зарегистрирован.
     */
    @Test
    public void testWorksWithMail_UserNotRegistered() {
        Long userId = 1L;
        String messageText = "/question";

        when(usersDataMock.checkUserIdExistsInRegistrationDataTable(userId, databaseConnectionMock))
                .thenReturn(false);
        String response = emailLogic.worksWithMail(messageText, userId, emailSenderMock, databaseConnectionMock);

        assertEquals("Эта функция недоступна, пока вы не зарегистрируетесь", response);
    }
    /**
     * Тест для проверки работы с функцией, когда пользователь зарегистрирован и ожидает вопрос.
     * Ожидается, что метод корректно обработает запрос и отправит письмо.
     */
    @Test
    public void testWorksWithMail_UserRegistered_AwaitingQuestion() {
        Long userId = 1L;
        String messageText = "/question";

        when(usersDataMock.checkUserIdExistsInRegistrationDataTable(userId, databaseConnectionMock))
                .thenReturn(true);
        emailLogic.getUserStatesForEmail(userId); // Это нужно для инициализации состояния
        emailLogic.worksWithMail(messageText, userId, emailSenderMock, databaseConnectionMock);

        String questionText = "Как зарегистрироваться?";
        String mailUser  = "test@example.com";

        when(usersDataMock.getUserMail(userId, databaseConnectionMock)).thenReturn(mailUser );
        when(emailSenderMock.getUsername()).thenReturn("testUser ");
        String response = emailLogic.worksWithMail(questionText, userId, emailSenderMock, databaseConnectionMock);
        verify(emailSenderMock).sendEmail(eq("testUser "), eq("Вопрос от абитуриента " + mailUser ), eq(questionText));
        assertEquals("Ваш вопрос отправлен", response);
    }
}
