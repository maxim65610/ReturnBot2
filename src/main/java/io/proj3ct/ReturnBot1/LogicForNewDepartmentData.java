package io.proj3ct.ReturnBot1;

import io.proj3ct.ReturnBot1.baseClasses.MessageConstants;

import java.util.HashMap;
import java.util.Map;

public class LogicForNewDepartmentData {
    // Хранит состояния пользователей для смены департамента
    private final DataForDepartment dataForDepartment = new DataForDepartment();
    private final Map<Long, String> userStatesForNewDepartment = new HashMap<>();
    public String getUserStatesForNewDepartment(Long chatID) {
        return userStatesForNewDepartment.getOrDefault(chatID, "0");
    }
    private String checkValidPasswordInput(String messageText, Long userId) {
        if (messageText.equals(System.getenv("password"))) {
            userStatesForNewDepartment.put(userId, "awaiting_institute");
            return MessageConstants.CORRECT_PASSWORD_AND_INSTITUTE_COMMAND_RESPONSE;
        } else {
            userStatesForNewDepartment.remove(userId);
            return MessageConstants.BAD_PASSWORD_COMMAND_RESPONSE;
        }
    }
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
            return MessageConstants.SUCCESSFUL_ADD_DEPARTMENT_COMMAND_RESPONSE;
        }
        return MessageConstants.DEFAULT_RESPONSE;
    }
}
