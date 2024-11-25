package io.proj3ct.ReturnBot1;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import io.proj3ct.ReturnBot1.baseClasses.EnvironmentService;
import io.proj3ct.ReturnBot1.baseClasses.MessageConstants;
import io.proj3ct.ReturnBot1.datebase.DatabaseConnection;
import io.proj3ct.ReturnBot1.keybords.DataForDepartment;
import io.proj3ct.ReturnBot1.keybords.DepartmentsInfo;
import io.proj3ct.ReturnBot1.keybords.KeyboardsData;
import io.proj3ct.ReturnBot1.keybords.LogicForDeleteDepartment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
/**
 * Тесты для класса LogicForDeleteDepartment. Проверяет логику работы с командой удаления
 * данных о департаменте, включая проверку пароля.
 */
class LogicForDeleteDepartmentTest {
    /**Экземпляр класса LogicForDeleteDepartment, который тестируется.*/
    private LogicForDeleteDepartment logicForDeleteDepartment;

    /**
     * Мок для подключения к базе данных.
     */
    @Mock
    private DatabaseConnection mockDatabaseConnection;

    /**
     * Мок для данных департамента.
     */
    @Mock
    private DataForDepartment mockDataForDepartment;

    /**
     * Мок для информации о департаментах.
     */
    @Mock
    private DepartmentsInfo mockDepartmentsInfo;

    /**
     * Мок для данных клавиатуры.
     */
    @Mock
    private KeyboardsData mockKeyboardsData;

    /**
     * Мок для сервиса получения данных окружения (например, пароля).
     */
    @Mock
    private EnvironmentService mockEnvironmentService;

    /**
     * Настройка тестового окружения и создание экземпляра LogicForDeleteDepartment.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        logicForDeleteDepartment = new LogicForDeleteDepartment(
                mockDatabaseConnection,
                mockDataForDepartment,
                mockDepartmentsInfo,
                mockKeyboardsData,
                mockEnvironmentService
        );
    }
    /**
     * Тестирует правильную работу с командой удаления департамента при вводе правильного пароля.
     */
    @Test
    void testWorksWithDeleteDepartment_correctPassword(){
        Long chatId = 123L;
        String messageText = "/delete_department_data";
        String response = logicForDeleteDepartment.worksWithDeleteDepartment(messageText, chatId);
        assertEquals(MessageConstants.PASSWORD_COMMAND_RESPONSE, response);
        assertEquals("awaiting_password", logicForDeleteDepartment.getUserStatesForDeleteDepartment(chatId));

        String[] mockDepartments = {
                "1 - Компьютерные Науки" + "\n" + "2 - Радиотехника" + "\n" + "3 - Прикладная информатика"
        };
        when(mockDepartmentsInfo.getAllNamesWithId()).thenReturn(mockDepartments);
        when(mockEnvironmentService.getPassword()).thenReturn("correctPassword");
        response = logicForDeleteDepartment.worksWithDeleteDepartment("correctPassword", chatId);
        String expectedTextForMessage = MessageConstants.CORRECT_PASSWORD_AND_INSTITUTE_DELETE_COMMAND_RESPONSE + "\n"
                + "1 - Компьютерные Науки" + "\n" + "2 - Радиотехника" + "\n" + "3 - Прикладная информатика" + "\n";
        assertEquals(expectedTextForMessage, response);
        assertEquals(logicForDeleteDepartment.getUserStatesForDeleteDepartment(chatId),
                "awaiting_numberForDelete");

        String numberForDeleteDepartment = "4";
        response = logicForDeleteDepartment.worksWithDeleteDepartment(numberForDeleteDepartment, chatId);
        expectedTextForMessage = MessageConstants.SUCCESSFUL_DELETE_DEPARTMENT_COMMAND_RESPONSE;
        assertEquals(expectedTextForMessage, response);
        assertEquals("0", logicForDeleteDepartment.getUserStatesForDeleteDepartment(chatId));
    }
    /**
     * Тестирует обработку команды удаления департамента при вводе неправильного пароля.
     */
    @Test
    void testWorksWithDeleteDepartment_unCorrectPassword(){
        Long chatId = 123L;
        String messageText = "/delete_department_data";
        logicForDeleteDepartment.worksWithDeleteDepartment(messageText, chatId);
        when(mockEnvironmentService.getPassword()).thenReturn("correctPassword");
        String response = logicForDeleteDepartment.worksWithDeleteDepartment("unCorrectPassword", chatId);
        assertEquals(MessageConstants.BAD_PASSWORD_COMMAND_RESPONSE, response);
        assertEquals("0", logicForDeleteDepartment.getUserStatesForDeleteDepartment(chatId));
    }
}
