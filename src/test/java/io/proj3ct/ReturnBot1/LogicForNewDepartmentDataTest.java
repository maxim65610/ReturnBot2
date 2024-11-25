package io.proj3ct.ReturnBot1;

import io.proj3ct.ReturnBot1.baseClasses.EnvironmentService;
import io.proj3ct.ReturnBot1.baseClasses.MessageConstants;
import io.proj3ct.ReturnBot1.datebase.DatabaseConnection;
import io.proj3ct.ReturnBot1.keybords.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * Тесты для класса LogicForNewDepartmentData. Проверяет логику работы с командой создания
 * нового департамента, включая обработку пароля.
 */
class LogicForNewDepartmentDataTest {

    /**
     * Экземпляр класса LogicForNewDepartmentData, который тестируется.
     */
    private LogicForNewDepartmentData logicForNewDepartmentData;

    /**
     * Мок для подключения к базе данных.
     */
    @Mock
    private DatabaseConnection mockDatabaseConnection;

    /**
     * Мок для данных о департаменте.
     */
    @Mock
    private DataForDepartment mockDataForDepartment;

    /**
     * Мок для данных клавиатуры.
     */
    @Mock
    private KeyboardsData mockKeyboardsData;

    /**
     * Мок для сервиса получения данных окружения, включая правильный пароль.
     */
    @Mock
    private EnvironmentService mockEnvironmentService;

    /**
     * Настройка тестового окружения и создание экземпляра LogicForNewDepartmentData.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        logicForNewDepartmentData = new LogicForNewDepartmentData(
                mockDatabaseConnection,
                mockDataForDepartment,
                mockKeyboardsData,
                mockEnvironmentService
        );
    }

    /**
     * Тестирует правильную работу с командой создания нового департамента при вводе правильного пароля.
     */
    @Test
    void worksWithNewDepartmentTest_correctPassword() {
        Long chatId = 123L;
        String messageText = "/new_department_data";

        // Проверка ответа на команду /new_department_data
        String response = logicForNewDepartmentData.worksWithNewDepartment(messageText, chatId);
        assertEquals(MessageConstants.PASSWORD_COMMAND_RESPONSE, response);
        assertEquals("awaiting_password", logicForNewDepartmentData.getUserStatesForNewDepartment(chatId));

        // Мокаем правильный пароль
        when(mockEnvironmentService.getPassword()).thenReturn("correctPassword");
        response = logicForNewDepartmentData.worksWithNewDepartment("correctPassword", chatId);
        assertEquals(MessageConstants.CORRECT_PASSWORD_AND_INSTITUTE_COMMAND_RESPONSE, response);
        assertEquals("awaiting_institute", logicForNewDepartmentData.getUserStatesForNewDepartment(chatId));

        // Ввод имени института
        response = logicForNewDepartmentData.worksWithNewDepartment("ИЕНИМ", chatId);
        assertEquals(MessageConstants.NAME_DEPARTMENT_INSTITUTE_COMMAND_RESPONSE, response);
        assertEquals("awaiting_nameDepartment", logicForNewDepartmentData.getUserStatesForNewDepartment(chatId));

        // Ввод названия департамента
        response = logicForNewDepartmentData.worksWithNewDepartment("Физика", chatId);
        assertEquals(MessageConstants.TEXT_FOR_DEPARTMENT_COMMAND_RESPONSE, response);
        assertEquals("awaiting_textForDepartment", logicForNewDepartmentData.getUserStatesForNewDepartment(chatId));

        // Ввод текста для департамента
        response = logicForNewDepartmentData.worksWithNewDepartment("Тут круто", chatId);
        assertEquals(MessageConstants.SUCCESSFUL_ADD_DEPARTMENT_COMMAND_RESPONSE, response);
        assertEquals("0", logicForNewDepartmentData.getUserStatesForNewDepartment(chatId));
    }
    /**
     * Тестирует обработку команды создания нового департамента при вводе неправильного пароля.
     * Проверяет, что при неверном пароле выводится сообщение об ошибке.
     */
    @Test
    void worksWithNewDepartmentTest_unCorrectPassword() {
        Long chatId = 123L;
        String messageText = "/new_department_data";

        // Проверка ответа на команду /new_department_data
        logicForNewDepartmentData.worksWithNewDepartment(messageText, chatId);
        when(mockEnvironmentService.getPassword()).thenReturn("correctPassword");

        // Ввод неправильного пароля
        String response = logicForNewDepartmentData.worksWithNewDepartment("unCorrectPassword", chatId);
        assertEquals(MessageConstants.BAD_PASSWORD_COMMAND_RESPONSE, response);
        assertEquals("0", logicForNewDepartmentData.getUserStatesForNewDepartment(chatId));
    }
}
