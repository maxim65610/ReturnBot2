package io.proj3ct.ReturnBot1.keybords;

import io.proj3ct.ReturnBot1.baseClasses.MessageConstants;
import io.proj3ct.ReturnBot1.datebase.DatabaseConnection;

import java.util.HashMap;
import java.util.Map;
/**
 * Логика для создания новых факультетов.
 * Этот класс обрабатывает состояние пользователей и управляет процессом создания новых факультетов.
 */
public class LogicForNewDepartmentData {
    /** Объект для хранения данных о факультетах. */
    private final DataForDepartment dataForDepartment = new DataForDepartment();
    /** Map для хранения состояний пользователей*/
    private final Map<Long, String> userStatesForNewDepartment = new HashMap<>();
    /** Объект для работы с клавиатурами. */
    private final KeyboardsData keyboardsData = new KeyboardsData();
    /** Объект для подключения к базе данных. */
    private final DatabaseConnection databaseConnection = new DatabaseConnection();

    /**
     * Получает текущее состояние пользователя для создания нового факультета.
     *
     * @param chatID идентификатор чата пользователя
     * @return текущее состояние пользователя или "0", если состояние не найдено
     */
    public String getUserStatesForNewDepartment(Long chatID) {
        return userStatesForNewDepartment.getOrDefault(chatID, "0");
    }
    /**
     * Проверяет введённый пароль и обновляет состояние пользователя.
     *
     * @param messageText текст сообщения от пользователя
     * @param userId идентификатор пользователя
     * @return сообщение для пользователя о результате проверки пароля
     */
    private String checkValidPasswordInput(String messageText, Long userId) {
        if (messageText.equals(System.getenv("password"))) {
            userStatesForNewDepartment.put(userId, "awaiting_institute");
            return MessageConstants.CORRECT_PASSWORD_AND_INSTITUTE_COMMAND_RESPONSE;
        } else {
            userStatesForNewDepartment.remove(userId);
            return MessageConstants.BAD_PASSWORD_COMMAND_RESPONSE;
        }
    }
    /**
     * Обрабатывает команды пользователя для создания нового факультета.
     *
     * @param messageText текст сообщения от пользователя
     * @param userId идентификатор пользователя
     * @return ответ пользователю в зависимости от состояния и команды
     */
    public String worksWithNewDepartment(String messageText, Long userId) {
        String currentState = userStatesForNewDepartment.get(userId);
        if ("/new_department_data".equals(messageText)) {
            userStatesForNewDepartment.put(userId, "awaiting_Password");
            return MessageConstants.PASSWORD_COMMAND_RESPONSE;
        } else if ("awaiting_Password".equals(currentState)) {
            return checkValidPasswordInput(messageText, userId);
        } else if ("awaiting_institute".equals(currentState)) {
            dataForDepartment.setInstituteForNewDepartment(userId, messageText);
            userStatesForNewDepartment.put(userId, "awaiting_nameDepartment");
            return MessageConstants.NAME_DEPARTMENT_INSTITUTE_COMMAND_RESPONSE;
        }
        else if ("awaiting_nameDepartment".equals(currentState)) {
            dataForDepartment.setNameForNewDepartment(userId, messageText);
            userStatesForNewDepartment.put(userId, "awaiting_textForDepartment");
            return MessageConstants.TEXT_FOR_DEPARTMENT_COMMAND_RESPONSE;
        }
        else if("awaiting_textForDepartment".equals(currentState)){
            dataForDepartment.setTextForNewDepartment(userId, messageText);
            userStatesForNewDepartment.remove(userId);
            Long newID = keyboardsData.generateNewId(databaseConnection);
            keyboardsData.insertData(userId,databaseConnection,newID,dataForDepartment);
            return MessageConstants.SUCCESSFUL_ADD_DEPARTMENT_COMMAND_RESPONSE;
        }
        return MessageConstants.DEFAULT_RESPONSE;
    }
}
