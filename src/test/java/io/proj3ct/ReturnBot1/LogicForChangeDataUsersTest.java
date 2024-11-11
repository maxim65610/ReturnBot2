package io.proj3ct.ReturnBot1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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
        when(textForMessage.handleMessage("userDataChange")).thenReturn("Выберите, что хотите изменить: имя, фамилию, класс или почту.");

        String response = logicForChangeDataUsers.getWorksWithChangeData("/userDataChange", userId, emailSender);
        assertEquals("Выберите, что хотите изменить: имя, фамилию, класс или почту.", response);

        // Изменение имени
        when(textForMessage.handleMessage("name")).thenReturn("Введите новое имя:");
        response = logicForChangeDataUsers.getWorksWithChangeData("/userDataChangeName", userId, emailSender);
        assertEquals("Введите новое имя:", response);

        // Ввод нового имени
        when(textForMessage.handleMessage("successful_name")).thenReturn("Имя успешно изменено!");
        response = logicForChangeDataUsers.getWorksWithChangeData("John", userId, emailSender);
        assertEquals("Имя успешно изменено!", response);
        verify(usersData).changeName(userId, databaseConnection, "John");
    }
    /**
     * Тестирует процесс изменения фамилии пользователя.
     */
    @Test
    public void testWorksWithChangeDataSurname() {
        // Начало процесса изменения данных
        when(textForMessage.handleMessage("userDataChange")).thenReturn("Выберите, что хотите изменить: имя, фамилию, класс или почту.");

        String response = logicForChangeDataUsers.getWorksWithChangeData("/userDataChange", userId, emailSender);
        assertEquals("Выберите, что хотите изменить: имя, фамилию, класс или почту.", response);

        // Изменение фамилии
        when(textForMessage.handleMessage("surname")).thenReturn("Введите новую фамилию:");
        response = logicForChangeDataUsers.getWorksWithChangeData("/userDataChangeSurname", userId, emailSender);
        assertEquals("Введите новую фамилию:", response);

        // Ввод новой фамилии
        when(textForMessage.handleMessage("successful_surname")).thenReturn("Фамилия успешно изменена!");
        response = logicForChangeDataUsers.getWorksWithChangeData("Doe", userId, emailSender);
        assertEquals("Фамилия успешно изменена!", response);
        verify(usersData).changeSurname(userId, databaseConnection, "Doe");
    }
    /**
     * Тестирует процесс изменения класса пользователя.
     */
    @Test
    public void testWorksWithChangeDataClass() {
        // Начало процесса изменения данных
        when(textForMessage.handleMessage("userDataChange")).thenReturn("Выберите, что хотите изменить: имя, фамилию, класс или почту.");

        String response = logicForChangeDataUsers.getWorksWithChangeData("/userDataChange", userId, emailSender);
        assertEquals("Выберите, что хотите изменить: имя, фамилию, класс или почту.", response);

        // Изменение класса
        when(textForMessage.handleMessage("class")).thenReturn("Введите новый класс:");
        response = logicForChangeDataUsers.getWorksWithChangeData("/userDataChangeClass", userId, emailSender);
        assertEquals("Введите новый класс:", response);

        // Ввод нового класса
        when(textForMessage.handleMessage("successful_class")).thenReturn("Класс успешно изменен!");
        response = logicForChangeDataUsers.getWorksWithChangeData("10", userId, emailSender);
        assertEquals("Класс успешно изменен!", response);
        verify(usersData).changeClass(userId, databaseConnection, "10");
    }
    /**
     * Тестирует процесс изменения почты пользователя.
     */
    @Test
    public void testWorksWithChangeDataMail() {
        // Начало процесса изменения данных
        when(textForMessage.handleMessage("userDataChange")).thenReturn("Выберите, что хотите изменить: имя, фамилию, класс или почту.");

        String response = logicForChangeDataUsers.getWorksWithChangeData("/userDataChange", userId, emailSender);
        assertEquals("Выберите, что хотите изменить: имя, фамилию, класс или почту.", response);

        // Изменение почты
        when(textForMessage.handleMessage("mail")).thenReturn("Введите новый адрес электронной почты:");
        response = logicForChangeDataUsers.getWorksWithChangeData("/userDataChangeMail", userId, emailSender);
        assertEquals("Введите новый адрес электронной почты:", response);

        // Ввод новой почты
        when(emailSender.isValidEmail("test@example.com")).thenReturn(true);
        when(textForMessage.handleMessage("successful_mail")).thenReturn("Почта успешно изменена!");
        response = logicForChangeDataUsers.getWorksWithChangeData("test@example.com", userId, emailSender);
        assertEquals("Почта успешно изменена!", response);
        verify(usersData).changeMail(userId, databaseConnection, "test@example.com");
    }

}
