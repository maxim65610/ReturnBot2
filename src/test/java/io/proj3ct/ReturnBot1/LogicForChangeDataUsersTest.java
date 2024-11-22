package io.proj3ct.ReturnBot1;

import io.proj3ct.ReturnBot1.baseClasses.MessageConstants;
import io.proj3ct.ReturnBot1.datebase.DatabaseConnection;
import io.proj3ct.ReturnBot1.mail.EmailSender;
import io.proj3ct.ReturnBot1.registration.LogicForChangeDataUsers;
import io.proj3ct.ReturnBot1.registration.UsersData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LogicForChangeDataUsersTest {
    private LogicForChangeDataUsers logic;
    private UsersData usersData;
    private DatabaseConnection databaseConnection;
    private EmailSender emailSender;

    @BeforeEach
    public void setUp() {
        // Инициализация зависимостей
        usersData = Mockito.mock(UsersData.class);
        databaseConnection = Mockito.mock(DatabaseConnection.class);
        emailSender = Mockito.mock(EmailSender.class);

        // Создание объекта LogicForChangeDataUsers с заглушками
        logic = new LogicForChangeDataUsers(new HashMap<>(), usersData, databaseConnection);
    }

    @Test
    public void testWorksWithChangeData_ChangeName() {
        Long userId = 1L;
        String messageText = "/user_data_change";
        logic.worksWithChangeData(messageText, userId, emailSender);

        messageText = "/user_data_change_name";
        String response = logic.worksWithChangeData(messageText, userId, emailSender);
        assertEquals(MessageConstants.ENTER_NAME, response);

        // Установка имени
        String newName = "John";
        Mockito.doNothing().when(usersData).changeName(userId, databaseConnection, newName);
        response = logic.worksWithChangeData(newName, userId, emailSender);
        assertEquals(MessageConstants.SUCCESSFUL_NAME, response);
    }

    @Test
    public void testWorksWithChangeData_ChangeSurname() {
        Long userId = 2L;
        String messageText = "/user_data_change";
        logic.worksWithChangeData(messageText, userId, emailSender);

        messageText = "/user_data_change_surname";
        String response = logic.worksWithChangeData(messageText, userId, emailSender);
        assertEquals(MessageConstants.ENTER_SURNAME, response);

        // Установка фамилии
        String newSurname = "Doe";
        Mockito.doNothing().when(usersData).changeSurname(userId, databaseConnection, newSurname);
        response = logic.worksWithChangeData(newSurname, userId, emailSender);
        assertEquals(MessageConstants.SUCCESSFUL_SURNAME, response);
    }

    @Test
    public void testWorksWithChangeData_ChangeClass() {
        Long userId = 3L;
        String messageText = "/user_data_change";
        logic.worksWithChangeData(messageText, userId, emailSender);

        messageText = "/user_data_change_class";
        String response = logic.worksWithChangeData(messageText, userId, emailSender);
        assertEquals(MessageConstants.ENTER_CLASS, response);

        // Установка класса
        String newClass = "10";
        Mockito.doNothing().when(usersData).changeClass(userId, databaseConnection, newClass);
        response = logic.worksWithChangeData(newClass, userId, emailSender);
        assertEquals(MessageConstants.SUCCESSFUL_CLASS, response);
    }

    @Test
    public void testWorksWithChangeData_ChangeMail() {
        Long userId = 4L;
        String messageText = "/user_data_change";
        logic.worksWithChangeData(messageText, userId, emailSender);

        messageText = "/user_data_change_mail";
        String response = logic.worksWithChangeData(messageText, userId, emailSender);
        assertEquals(MessageConstants.ENTER_MAIL, response);

        // Установка email
        String newEmail = "test@example.com";
        Mockito.when(emailSender.isValidEmail(newEmail)).thenReturn(true);
        Mockito.doNothing().when(usersData).changeMail(userId, databaseConnection, newEmail);
        response = logic.worksWithChangeData(newEmail, userId, emailSender);
        assertEquals(MessageConstants.SUCCESSFUL_MAIL, response);
    }
}
