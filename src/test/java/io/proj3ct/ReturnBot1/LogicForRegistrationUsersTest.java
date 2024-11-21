package io.proj3ct.ReturnBot1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Модульные тесты для класса LogicAndDataForRegistrationUsers.
 * Этот класс тестирует логику регистрации пользователей, включая обработку
 * авторизации, ввод личных данных и проверку электронной почты.
 */
public class LogicForRegistrationUsersTest {
    private LogicForRegistrationUsers logic;
    private UsersData usersData;
    private DatabaseConnection databaseConnection;
    private EmailSender emailSender;
    private DataUsersForRegistration dataUsersForRegistration;
    private Connection connection;
    private Statement statement; // Объект Statement для операций с базой данных

    /**
     * Настраивает тестовую среду перед каждым тестом.
     * Инициализирует моки и настраивает соединение с базой данных.
     */
    @BeforeEach
    public void setUp() throws SQLException {
        // Инициализация моков
        usersData = Mockito.mock(UsersData.class);
        databaseConnection = Mockito.mock(DatabaseConnection.class);
        dataUsersForRegistration = Mockito.mock(DataUsersForRegistration.class);
        emailSender = Mockito.mock(EmailSender.class);
        // Проверка на наличие нулевых моков
        if (usersData == null || databaseConnection == null || emailSender == null) {
            throw new IllegalStateException("usersData, databaseConnection или emailSender равны null");
        }

        // Настройка соединений и заявлений
        connection = Mockito.mock(Connection.class);
        statement = Mockito.mock(Statement.class);

        when(databaseConnection.connect()).thenReturn(connection);
        when(connection.createStatement()).thenReturn(statement);

        // Инициализация других объектов
        logic = new LogicForRegistrationUsers(usersData, databaseConnection, dataUsersForRegistration);
    }

    /**
     * Тестирует успешный процесс регистрации, когда предоставлен корректный адрес электронной почты.
     */
    @Test
    public void testWorksWithRegistration_EnterMail() {
        Long userId = 1L;
        logic.worksWithRegistration("/authorization", userId, emailSender, logic);
        logic.worksWithRegistration("John", userId, emailSender, logic);
        logic.worksWithRegistration("Doe", userId, emailSender, logic);
        logic.worksWithRegistration("10", userId, emailSender, logic);

        String validEmail = "test@example.com";
        Mockito.when(emailSender.isValidEmail(validEmail)).thenReturn(true);

        String response = logic.worksWithRegistration(validEmail, userId, emailSender, logic);
        assertEquals(MessageConstants.SUCCESSFUL_REGISTRATION_COMMAND_RESPONSE, response);
    }

    /**
     * Тестирует процесс регистрации, когда предоставлен некорректный адрес электронной почты.
     */
    @Test
    public void testWorksWithRegistration_InvalidMail() {
        Long userId = 1L;
        logic.worksWithRegistration("/authorization", userId, emailSender, logic);
        logic.worksWithRegistration("John", userId, emailSender, logic);
        logic.worksWithRegistration("Doe", userId, emailSender, logic);
        logic.worksWithRegistration("10", userId, emailSender, logic);

        String invalidEmail = "invalid-email";
        Mockito.when(emailSender.isValidEmail(invalidEmail)).thenReturn(false);

        String response = logic.worksWithRegistration(invalidEmail, userId, emailSender, logic);
        assertEquals(MessageConstants.NOT_CORRECT_MAIL_COMMAND_RESPONSE, response);
    }
    /**
     * Тестирует процесс ввода имени пользователя во время регистрации.
     */
    @Test
    public void testWorksWithRegistration_EnterName() {
        Long userId = 1L;
        logic.worksWithRegistration("/authorization", userId, emailSender, logic);

        String response = logic.worksWithRegistration("John", userId, emailSender, logic);
        assertEquals(MessageConstants.ENTER_SURNAME, response.trim()); // Обрезанный ответ

        assertEquals("awaiting_surnameUser", logic.getUserStatesForRegistration(userId).trim()); // Обрезанное ожидаемое значение
    }

    /**
     * Тестирует процесс ввода фамилии пользователя во время регистрации.
     */
    @Test
    public void testWorksWithRegistration_EnterSurname() {
        Long userId = 1L;
        logic.worksWithRegistration("/authorization", userId, emailSender, logic);
        logic.worksWithRegistration("John", userId, emailSender, logic);

        String response = logic.worksWithRegistration("Doe", userId, emailSender, logic);
        assertEquals(MessageConstants.ENTER_CLASS, response.trim()); // Обрезанный ответ

        assertEquals("awaiting_schoolClassUser", logic.getUserStatesForRegistration(userId).trim()); // Обрезанное ожидаемое значение
    }

    /**
     * Тестирует процесс ввода класса пользователя во время регистрации.
     */
    @Test
    public void testWorksWithRegistration_EnterClass() {
        Long userId = 1L;
        logic.worksWithRegistration("/authorization", userId, emailSender, logic);
        logic.worksWithRegistration("John", userId, emailSender, logic);
        logic.worksWithRegistration("Doe", userId, emailSender, logic);

        String response = logic.worksWithRegistration("10", userId, emailSender, logic);
        assertEquals(MessageConstants.ENTER_MAIL, response.trim()); // Обрезанный ответ

        assertEquals("awaiting_mailUser", logic.getUserStatesForRegistration(userId).trim()); // Обрезанное ожидаемое значение
    }
}
