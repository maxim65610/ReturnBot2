package io.proj3ct.ReturnBot1;
import io.proj3ct.ReturnBot1.Command.*;
import io.proj3ct.ReturnBot1.baseClasses.MessageConstants;
import io.proj3ct.ReturnBot1.keybords.LogicForDeleteDepartment;
import io.proj3ct.ReturnBot1.keybords.LogicForEditDepartmentData;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/**
 * Тесты для классов реализующих интерфейс Command
 */
class CommandTests {
    /**
     * Тестирует выполнение команды DefaultCommand.
     * Проверяет, что команда возвращает стандартный ответ и исходное сообщение.
     */
    @Test
    void testDefaultCommand() {
        // Arrange
        Command command = new DefaultCommand();
        long userId = 123L;
        String messageText = "Test message";
        boolean flagForKeyboard = false;

        // Act
        List<String> result = command.execute(userId, messageText, flagForKeyboard);

        // Assert
        assertEquals(2, result.size());
        assertEquals(MessageConstants.DEFAULT_RESPONSE, result.get(0)); // Предположим, что это значение из MessageConstants.DEFAULT_RESPONSE
        assertEquals("Test message", result.get(1));
    }

    /**
     * Тестирует выполнение команды DeleteDepartmentCommand.
     * Проверяет, что команда удаляет данные департамента и возвращает соответствующий ответ.
     */
    @Test
    void testDeleteDepartmentCommand() {
        // Arrange
        LogicForDeleteDepartment logicForDeleteDepartment = mock(LogicForDeleteDepartment.class);
        DeleteDepartmentCommand command = new DeleteDepartmentCommand();

        long userId = 123L;
        String messageText = "/deleteDepartmentData";
        boolean flagForKeyboard = false;

        // Настройка мока
        when(logicForDeleteDepartment.getUserStatesForDeleteDepartment(userId)).thenReturn("0");
        when(logicForDeleteDepartment.worksWithDeleteDepartment(messageText, userId))
                .thenReturn("Department deleted successfully");

        // Act
        List<String> result = command.execute(userId, messageText, flagForKeyboard);

        // Assert
        assertEquals(1, result.size());
        assertEquals(MessageConstants.PASSWORD_COMMAND_RESPONSE, result.get(0));
    }

    /**
     * Тестирует выполнение команды EditDepartmentCommand.
     * Проверяет, что команда редактирует данные департамента и возвращает соответствующий ответ.
     */
    @Test
    void testEditDepartmentCommand() {
        // Arrange
        LogicForEditDepartmentData logicForEditDepartmentData = mock(LogicForEditDepartmentData.class);
        EditDepartmentCommand command = new EditDepartmentCommand();

        long userId = 123L;
        String messageText = "/editDepartmentData";
        boolean flagForKeyboard = false;

        // Настройка мока
        when(logicForEditDepartmentData.getUserStatesForEditDepartment(userId)).thenReturn("0");
        when(logicForEditDepartmentData.worksWithEditDepartment(messageText, userId))
                .thenReturn("Department edited successfully");

        // Act
        List<String> result = command.execute(userId, messageText, flagForKeyboard);

        // Assert
        assertEquals(1, result.size());
        assertEquals(MessageConstants.PASSWORD_COMMAND_RESPONSE, result.get(0));
    }
}

