package io.proj3ct.ReturnBot1;

import io.proj3ct.ReturnBot1.keybords.DataForDepartment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для класса DataForDepartment.
 */
public class DataForDepartmentTest {
    private DataForDepartment dataForDepartment;

    /**
     * Подготовка к тестам: создаем новый экземпляр DataForDepartment.
     */
    @BeforeEach
    public void setUp() {
        dataForDepartment = new DataForDepartment();
    }
    /**
     * Проверяем, что номер факультета устанавливается правильно.
     */
    @Test
    public void testSetNumberForEditDepartment() {
        Long userId = 1L;
        String number = "123";

        dataForDepartment.setNumberForEditDepartment(userId, number);
        String actualNumber = dataForDepartment.getNumberForEditDepartment(userId);

        assertEquals(number, actualNumber, "Номер факультета должен быть установлен правильно");
    }
    /**
     * Проверяем, что название факультета устанавливается правильно.
     */
    @Test
    public void testSetNameForEditDepartment() {
        Long userId = 1L;
        String name = "Название факультета";

        dataForDepartment.setNameForEditDepartment(userId, name);
        String actualName = dataForDepartment.getNameForEditDepartment(userId);

        assertEquals(name, actualName, "Название факультета должно быть установлено правильно");
    }
    /**
     * Проверяем, что текст факультета устанавливается правильно.
     */
    @Test
    public void testSetTextForEditDepartment() {
        Long userId = 1L;
        String text = "Текст факультета";

        dataForDepartment.setTextForEditDepartment(userId, text);
        String actualText = dataForDepartment.getTextForEditDepartment(userId);

        assertEquals(text, actualText, "Текст факультета должен быть установлен правильно");
    }
    /**
     * Проверяем, что институт для нового факультета устанавливается правильно.
     */
    @Test
    public void testSetInstituteForNewDepartment() {
        Long userId = 1L;
        String institute = "Название института";

        dataForDepartment.setInstituteForNewDepartment(userId, institute);
        String actualInstitute = dataForDepartment.getInstituteForNewDepartment(userId);

        assertEquals(institute, actualInstitute, "Институт для нового факультета должен быть установлен правильно");
    }
    /**
     * Проверяем, что название нового факультета устанавливается правильно.
     */
    @Test
    public void testSetNameForNewDepartment() {
        Long userId = 1L;
        String name = "Новое название факультета";

        dataForDepartment.setNameForNewDepartment(userId, name);
        String actualName = dataForDepartment.getNameForNewDepartment(userId);

        assertEquals(name, actualName, "Название нового факультета должно быть установлено правильно");
    }
    /**
     * Проверяем, что текст нового факультета устанавливается правильно.
     */
    @Test
    public void testSetTextForNewDepartment() {
        Long userId = 1L;
        String text = "Текст нового факультета";

        dataForDepartment.setTextForNewDepartment(userId, text);
        String actualText = dataForDepartment.getTextForNewDepartment(userId);

        assertEquals(text, actualText, "Текст нового факультета должен быть установлен правильно");
    }
    /**
     * Проверяем, что номер для удаления факультета устанавливается правильно.
     */
    @Test
    public void testSetNumberForDeleteDepartment() {
        Long userId = 1L;
        String number = "456";

        dataForDepartment.setNumberForDeleteDepartment(userId, number);
        String actualNumber = dataForDepartment.getNumberForDeleteDepartment(userId);

        assertEquals(number, actualNumber, "Номер для удаления факультета должен быть установлен правильно");
    }
    /**
     * Проверяем, что возвращается null для несуществующего идентификатора пользователя.
     */
    @Test
    public void testGetNumberForEditDepartmentNonExistentUserId() {
        Long userId = 999L;
        assertNull(dataForDepartment.getNumberForEditDepartment(userId), "Должно вернуть null для несуществующего идентификатора пользователя");
    }
    /**
     * Проверяем, что возвращается null для несуществующего идентификатора пользователя.
     */
    @Test
    public void testGetNameForNewDepartmentNonExistentUserId() {
        Long userId = 999L;
        assertNull(dataForDepartment.getNameForNewDepartment(userId), "Должно вернуть null для несуществующего идентификатора пользователя");
    }
}