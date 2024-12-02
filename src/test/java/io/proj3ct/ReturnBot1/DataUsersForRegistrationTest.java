package io.proj3ct.ReturnBot1;

import io.proj3ct.ReturnBot1.registration.DataUsersForRegistration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тестовый класс для проверки функциональности класса DataUsersForRegistration.
 */
public class DataUsersForRegistrationTest {
    private DataUsersForRegistration dataUsers;

    /**
     * Метод, который выполняется перед каждым тестом.
     * Здесь мы инициализируем экземпляр DataUsersForRegistration.
     */
    @BeforeEach
    public void setUp() {
        dataUsers = new DataUsersForRegistration();
    }
    /**
     * Тест, который проверяет добавление и получение имени пользователя.
     */
    @Test
    public void testSetAndGetNameUser () {
        Long userId = 1L;
        String expectedName = "John";

        dataUsers.setNameUser (userId, expectedName);
        String actualName = dataUsers.getNameUser (userId);

        assertEquals(expectedName, actualName, "Имя пользователя должно совпадать с ожидаемым.");
    }
    /**
     * Тест, который проверяет добавление и получение фамилии пользователя.
     */
    @Test
    public void testSetAndGetSurnameUser () {
        Long userId = 1L;
        String expectedSurname = "Doe";

        dataUsers.setSurnameUser (userId, expectedSurname);
        String actualSurname = dataUsers.getSurnameUser (userId);

        assertEquals(expectedSurname, actualSurname, "Фамилия пользователя должна совпадать с ожидаемой.");
    }
    /**
     * Тест, который проверяет добавление и получение класса пользователя.
     */
    @Test
    public void testSetAndGetSchoolClassUser () {
        Long userId = 1L;
        String expectedClass = "10A";

        dataUsers.setSchoolClassUser (userId, expectedClass);
        String actualClass = dataUsers.getSchoolClassUser (userId);

        assertEquals(expectedClass, actualClass, "Класс пользователя должен совпадать с ожидаемым.");
    }
    /**
     * Тест, который проверяет добавление и получение адреса электронной почты пользователя.
     */
    @Test
    public void testSetAndGetMailUser () {
        Long userId = 1L;
        String expectedEmail = "john.doe@example.com";

        dataUsers.setMailUser (userId, expectedEmail);
        String actualEmail = dataUsers.getMailUser (userId);

        assertEquals(expectedEmail, actualEmail, "Адрес электронной почты пользователя должен совпадать с ожидаемым.");
    }
    /**
     * Тест, который проверяет поведение при запросе данных несуществующего пользователя.
     */
    @Test
    public void testGetNonExistentUser () {
        Long nonExistentUserId = 999L;

        assertNull(dataUsers.getNameUser (nonExistentUserId), "Имя несуществующего пользователя должно быть null.");
        assertNull(dataUsers.getSurnameUser (nonExistentUserId), "Фамилия несуществующего пользователя должна быть null.");
        assertNull(dataUsers.getSchoolClassUser (nonExistentUserId), "Класс несуществующего пользователя должен быть null.");
        assertNull(dataUsers.getMailUser (nonExistentUserId), "Адрес электронной почты несуществующего пользователя должен быть null.");
    }
}