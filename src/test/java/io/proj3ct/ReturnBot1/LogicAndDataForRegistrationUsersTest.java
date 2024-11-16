package io.proj3ct.ReturnBot1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import io.proj3ct.ReturnBot1.baseClasses.TextForMessage;
import io.proj3ct.ReturnBot1.datebase.DatabaseConnection;
import io.proj3ct.ReturnBot1.mail.EmailSender;
import io.proj3ct.ReturnBot1.registration.LogicAndDataForRegistrationUsers;
import io.proj3ct.ReturnBot1.registration.UsersData;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;

/**
 * Тестовый класс для проверки логики регистрации пользователей в классе
 * LogicAndDataForRegistrationUsers.
 */
public class LogicAndDataForRegistrationUsersTest {

    private LogicAndDataForRegistrationUsers logicAndDataForRegistrationUsers;

    @Mock
    private UsersData usersData;

    @Mock
    private DatabaseConnection databaseConnection;

    @Mock
    private EmailSender emailSender;

    @Mock
    private TextForMessage textForMessage;

    @Mock
    private Update update;

    @Mock
    private Message message;
    /**
     * Настройка тестового окружения перед каждым тестом.
     * Инициализирует моки и устанавливает зависимости для тестируемого класса.
     */
    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        logicAndDataForRegistrationUsers = new LogicAndDataForRegistrationUsers();

        // Установка зависимостей через сеттеры
        logicAndDataForRegistrationUsers.setUsersData(usersData);
        logicAndDataForRegistrationUsers.setDatabaseConnection(databaseConnection);
        logicAndDataForRegistrationUsers.setTextForMessage(textForMessage);

        when(update.getMessage()).thenReturn(message);

    }
    /**
     * Тестирует процесс регистрации пользователя.
     * Проверяет, что на каждом этапе регистрации возвращаются ожидаемые сообщения,
     * и что данные пользователя сохраняются корректно.
     */
    @Test
    public void testWorksWithRegistration() {
        Long userId = 1L;

        when(message.getText()).thenReturn("/authorization");
        when(usersData.checkUserIdExistsInRegistrationDataTable(userId, databaseConnection)).thenReturn(false);
        when(textForMessage.setTheText("authorization")).thenReturn("Введите имя:");

        String response = logicAndDataForRegistrationUsers.worksWithRegistration("/authorization", userId, emailSender, logicAndDataForRegistrationUsers);
        assertEquals("Введите имя:", response);

        logicAndDataForRegistrationUsers.setUserStateForRegistration(userId, "awaiting_nameUser ");
        when(message.getText()).thenReturn("John");
        when(textForMessage.setTheText("name")).thenReturn("Введите фамилию:");

        response = logicAndDataForRegistrationUsers.worksWithRegistration("John", userId, emailSender, logicAndDataForRegistrationUsers);
        assertEquals("Введите фамилию:", response);
        assertEquals("John", logicAndDataForRegistrationUsers.getNameUser (userId));

        logicAndDataForRegistrationUsers.setUserStateForRegistration(userId, "awaiting_surnameUser ");
        when(message.getText()).thenReturn("Doe");
        when(textForMessage.setTheText("class")).thenReturn("Введите класс:");

        response = logicAndDataForRegistrationUsers.worksWithRegistration("Doe", userId, emailSender, logicAndDataForRegistrationUsers);
        assertEquals("Введите класс:", response);
        assertEquals("Doe", logicAndDataForRegistrationUsers.getSurnameUser (userId));

        logicAndDataForRegistrationUsers.setUserStateForRegistration(userId, "awaiting_schoolClassUser ");
        when(message.getText()).thenReturn("10");
        when(textForMessage.setTheText("io/proj3ct/ReturnBot1/mail")).thenReturn("Введите почту:");

        response = logicAndDataForRegistrationUsers.worksWithRegistration("10", userId, emailSender, logicAndDataForRegistrationUsers);
        assertEquals("Введите почту:", response);
        assertEquals("10", logicAndDataForRegistrationUsers.getSchoolClassUser (userId));

        logicAndDataForRegistrationUsers.setUserStateForRegistration(userId, "awaiting_mailUser ");
        when(message.getText()).thenReturn("test@example.com");
        when(emailSender.isValidEmail("test@example.com")).thenReturn(true);
        when(textForMessage.setTheText("successfulReg")).thenReturn("Регистрация успешна!");

        response = logicAndDataForRegistrationUsers.worksWithRegistration("test@example.com", userId, emailSender, logicAndDataForRegistrationUsers);
        assertEquals("Регистрация успешна!", response);
    }
}
