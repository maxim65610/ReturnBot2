package io.proj3ct.ReturnBot1;

import io.proj3ct.ReturnBot1.baseClasses.EnvironmentService;
import io.proj3ct.ReturnBot1.baseClasses.MessageConstants;
import io.proj3ct.ReturnBot1.datebase.DatabaseConnection;
import io.proj3ct.ReturnBot1.keybords.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Тесты для класса LogicForEditDepartmentData. Проверяет логику работы с командой редактирования
 * данных о департаменте, включая обработку пароля.
 */
class LogicForEditDepartmentDataTest {

    /**
     * Экземпляр класса LogicForEditDepartmentData, который тестируется.
     */
    private LogicForEditDepartmentData logicForEditDepartmentData;

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
     * Мок для получения информации о департаментах.
     */
    @Mock
    private DepartmentsInfo mockDepartmentsInfo;

    /**
     * Мок для данных клавиатуры (возможно, для взаимодействия с пользователем).
     */
    @Mock
    private KeyboardsData mockKeyboardsData;

    /**
     * Мок для сервиса получения данных окружения, включая правильный пароль.
     */
    @Mock
    private EnvironmentService mockEnvironmentService;

    /**
     * Настройка тестового окружения и создание экземпляра LogicForEditDepartmentData.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        logicForEditDepartmentData = new LogicForEditDepartmentData(
                mockDatabaseConnection,
                mockDataForDepartment,
                mockDepartmentsInfo,
                mockKeyboardsData,
                mockEnvironmentService
        );
    }

    /**
     * Тестирует правильную работу с командой редактирования департамента при вводе правильного пароля.
     * Проверяет последовательность шагов: запрос пароля, отображение списка департаментов,
     * редактирование департамента и окончательное изменение данных.
     */
    @Test
    void testWorksWithEditDepartment_correctPassword() {
        Long chatId = 123L;
        String messageText = "/edit_department_data";

        // Проверка ответа на команду /edit_department_data
        String response = logicForEditDepartmentData.worksWithEditDepartment(messageText, chatId);
        assertEquals(MessageConstants.PASSWORD_COMMAND_RESPONSE, response);
        assertEquals("awaiting_password", logicForEditDepartmentData.getUserStatesForEditDepartment(chatId));

        // Мокаем список департаментов
        String[] mockDepartments = {
                "1 - Компьютерные Науки" + "\n" + "2 - Радиотехника" + "\n" + "3 - Прикладная информатика"
        };
        when(mockDepartmentsInfo.getAllNamesWithId()).thenReturn(mockDepartments);
        when(mockEnvironmentService.getPassword()).thenReturn("correctPassword");

        // Ввод правильного пароля
        response = logicForEditDepartmentData.worksWithEditDepartment("correctPassword", chatId);
        String expectedTextForMessage = MessageConstants.EDIT_DEPARTMENT_COMMAND_RESPONSE + "\n"
                + "1 - Компьютерные Науки" + "\n" + "2 - Радиотехника" + "\n" + "3 - Прикладная информатика" + "\n";
        assertEquals(expectedTextForMessage, response);
        assertEquals("awaiting_numberForEdit", logicForEditDepartmentData.getUserStatesForEditDepartment(chatId));

        // Ввод номера департамента для редактирования
        response = logicForEditDepartmentData.worksWithEditDepartment("4", chatId);
        expectedTextForMessage = MessageConstants.NEW_DEPARTMENT_COMMAND_RESPONSE;
        assertEquals(expectedTextForMessage, response);
        assertEquals("awaiting_newDepartment", logicForEditDepartmentData.getUserStatesForEditDepartment(chatId));

        // Ввод нового названия департамента
        response = logicForEditDepartmentData.worksWithEditDepartment("ИЕНИМ", chatId);
        expectedTextForMessage = MessageConstants.TEXT_FOR_DEPARTMENT_COMMAND_RESPONSE;
        assertEquals(expectedTextForMessage, response);
        assertEquals("awaiting_textForDepartment", logicForEditDepartmentData.getUserStatesForEditDepartment(chatId));

        // Ввод текста для департамента
        response = logicForEditDepartmentData.worksWithEditDepartment("Здесь круто", chatId);
        expectedTextForMessage = MessageConstants.CHANGE_DEPARTMENT_COMMAND_RESPONSE;
        assertEquals(expectedTextForMessage, response);
        assertEquals("0", logicForEditDepartmentData.getUserStatesForEditDepartment(chatId));
    }

    /**
     * Тестирует обработку команды редактирования департамента при вводе неправильного пароля.
     * Проверяет, что при неверном пароле возвращается сообщение об ошибке.
     */
    @Test
    void testWorksWithEditDepartment_unCorrectPassword() {
        Long chatId = 123L;
        String messageText = "/edit_department_data";

        // Проверка ответа на команду /edit_department_data
        logicForEditDepartmentData.worksWithEditDepartment(messageText, chatId);
        when(mockEnvironmentService.getPassword()).thenReturn("correctPassword");

        // Ввод неправильного пароля
        String response = logicForEditDepartmentData.worksWithEditDepartment("unCorrectPassword", chatId);
        assertEquals(MessageConstants.BAD_PASSWORD_COMMAND_RESPONSE, response);
        assertEquals("0", logicForEditDepartmentData.getUserStatesForEditDepartment(chatId));
    }
}
