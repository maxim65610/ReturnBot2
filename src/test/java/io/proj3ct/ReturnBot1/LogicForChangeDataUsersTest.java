package io.proj3ct.ReturnBot1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import io.proj3ct.ReturnBot1.baseClasses.TextForMessage;
import io.proj3ct.ReturnBot1.datebase.DatabaseConnection;
import io.proj3ct.ReturnBot1.mail.EmailSender;
import io.proj3ct.ReturnBot1.registration.LogicForChangeDataUsers;
import io.proj3ct.ReturnBot1.registration.UsersData;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Тестовый класс для проверки логики изменения данных пользователей в классе
 * LogicForChangeDataUsers.
 */
public class LogicForChangeDataUsersTest {

    private LogicForChangeDataUsers logicForChangeDataUsers;

    @Mock
    private UsersData usersData;

    @Mock
    private DatabaseConnection databaseConnection;

    @Mock
    private TextForMessage textForMessage;

    @Mock
    private EmailSender emailSender;

    private Long userId = 1L;
    /**
     * Настройка тестового окружения перед каждым тестом.
     * Инициализирует моки и устанавливает зависимости для тестируемого класса.
     */
    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        logicForChangeDataUsers = new LogicForChangeDataUsers();

        // Установка зависимостей через сеттеры
        logicForChangeDataUsers.setUsersData(usersData);
        logicForChangeDataUsers.setDatabaseConnection(databaseConnection);
        logicForChangeDataUsers.setTextForMessage(textForMessage);
    }
    /**
     * Тестирует процесс изменения имени пользователя.
     */
    @Test
    public void testWorksWithChangeDataName() {
        // Начало процесса изменения данных
        when(textForMessage.setTheText("userDataChange")).thenReturn("Выберите, что хотите изменить: имя, фамилию, класс или почту.");

        String response = logicForChangeDataUsers.worksWithChangeData("/userDataChange", userId, emailSender);
        assertEquals("Выберите, что хотите изменить: имя, фамилию, класс или почту.", response);

        // Изменение имени
        when(textForMessage.setTheText("name")).thenReturn("Введите новое имя:");
        response = logicForChangeDataUsers.worksWithChangeData("/userDataChangeName", userId, emailSender);
        assertEquals("Введите новое имя:", response);

        // Ввод нового имени
        when(textForMessage.setTheText("successful_name")).thenReturn("Имя успешно изменено!");
        response = logicForChangeDataUsers.worksWithChangeData("John", userId, emailSender);
        assertEquals("Имя успешно изменено!", response);
        verify(usersData).changeName(userId, databaseConnection, "John");
    }
    /**
     * Тестирует процесс изменения фамилии пользователя.
     */
    @Test
    public void testWorksWithChangeDataSurname() {
        // Начало процесса изменения данных
        when(textForMessage.setTheText("userDataChange")).thenReturn("Выберите, что хотите изменить: имя, фамилию, класс или почту.");

        String response = logicForChangeDataUsers.worksWithChangeData("/userDataChange", userId, emailSender);
        assertEquals("Выберите, что хотите изменить: имя, фамилию, класс или почту.", response);

        // Изменение фамилии
        when(textForMessage.setTheText("surname")).thenReturn("Введите новую фамилию:");
        response = logicForChangeDataUsers.worksWithChangeData("/userDataChangeSurname", userId, emailSender);
        assertEquals("Введите новую фамилию:", response);

        // Ввод новой фамилии
        when(textForMessage.setTheText("successful_surname")).thenReturn("Фамилия успешно изменена!");
        response = logicForChangeDataUsers.worksWithChangeData("Doe", userId, emailSender);
        assertEquals("Фамилия успешно изменена!", response);
        verify(usersData).changeSurname(userId, databaseConnection, "Doe");
    }
    /**
     * Тестирует процесс изменения класса пользователя.
     */
    @Test
    public void testWorksWithChangeDataClass() {
        // Начало процесса изменения данных
        when(textForMessage.setTheText("userDataChange")).thenReturn("Выберите, что хотите изменить: имя, фамилию, класс или почту.");

        String response = logicForChangeDataUsers.worksWithChangeData("/userDataChange", userId, emailSender);
        assertEquals("Выберите, что хотите изменить: имя, фамилию, класс или почту.", response);

        // Изменение класса
        when(textForMessage.setTheText("class")).thenReturn("Введите новый класс:");
        response = logicForChangeDataUsers.worksWithChangeData("/userDataChangeClass", userId, emailSender);
        assertEquals("Введите новый класс:", response);

        // Ввод нового класса
        when(textForMessage.setTheText("successful_class")).thenReturn("Класс успешно изменен!");
        response = logicForChangeDataUsers.worksWithChangeData("10", userId, emailSender);
        assertEquals("Класс успешно изменен!", response);
        verify(usersData).changeClass(userId, databaseConnection, "10");
    }
    /**
     * Тестирует процесс изменения почты пользователя.
     */
    @Test
    public void testWorksWithChangeDataMail() {
        // Начало процесса изменения данных
        when(textForMessage.setTheText("userDataChange")).thenReturn("Выберите, что хотите изменить: имя, фамилию, класс или почту.");

        String response = logicForChangeDataUsers.worksWithChangeData("/userDataChange", userId, emailSender);
        assertEquals("Выберите, что хотите изменить: имя, фамилию, класс или почту.", response);

        // Изменение почты
        when(textForMessage.setTheText("io/proj3ct/ReturnBot1/mail")).thenReturn("Введите новый адрес электронной почты:");
        response = logicForChangeDataUsers.worksWithChangeData("/userDataChangeMail", userId, emailSender);
        assertEquals("Введите новый адрес электронной почты:", response);

        // Ввод новой почты
        when(emailSender.isValidEmail("test@example.com")).thenReturn(true);
        when(textForMessage.setTheText("successful_mail")).thenReturn("Почта успешно изменена!");
        response = logicForChangeDataUsers.worksWithChangeData("test@example.com", userId, emailSender);
        assertEquals("Почта успешно изменена!", response);
        verify(usersData).changeMail(userId, databaseConnection, "test@example.com");
    }

}
