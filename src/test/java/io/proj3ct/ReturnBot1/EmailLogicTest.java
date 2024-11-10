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
        emailLogic = new EmailLogic();
        usersDataMock = mock(UsersData.class);
        emailSenderMock = mock(EmailSender.class);
        databaseConnectionMock = mock(DatabaseConnection.class);
        emailLogic.setUsersData(usersDataMock);
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
     * Тест для проверки получения состояния пользователя, когда оно отсутствует.
     * Ожидается, что метод вернет "0", если состояния нет.
     */
    @Test
    public void testWorksWithMail_UserNotRegistered() {
        Long userId = 1L;
        String messageText = "/question";

        when(usersDataMock.checkUserIdExistsInRegistrationDataTable(userId, databaseConnectionMock))
                .thenReturn(false);
        String response = emailLogic.getWorksWithMail(messageText, userId, emailSenderMock, emailLogic, databaseConnectionMock);

        assertEquals("Эта функция недоступна, пока вы не зарегистрируетесь", response);
    }
    /**
     * Тест для проверки получения состояния пользователя, когда оно отсутствует.
     * Ожидается, что метод вернет "0", если состояния нет.
     */
    @Test
    public void testWorksWithMail_UserRegistered_AwaitingQuestion() {
        Long userId = 1L;
        String messageText = "/question";

        when(usersDataMock.checkUserIdExistsInRegistrationDataTable(userId, databaseConnectionMock))
                .thenReturn(true);
        emailLogic.getUserStatesForEmail(userId); // Это нужно для инициализации состояния
        emailLogic.getWorksWithMail(messageText, userId, emailSenderMock, emailLogic, databaseConnectionMock);

        String questionText = "Как зарегистрироваться?";
        String mailUser  = "test@example.com";

        when(usersDataMock.getUserMail(userId, databaseConnectionMock)).thenReturn(mailUser );
        when(emailSenderMock.getUsername()).thenReturn("testUser ");
        String response = emailLogic.getWorksWithMail(questionText, userId, emailSenderMock, emailLogic, databaseConnectionMock);
        verify(emailSenderMock).sendEmail(eq("testUser "), eq("Вопрос от абитуриента " + mailUser ), eq(questionText));
        assertEquals("Ваш вопрос отправлен", response);
    }
}
