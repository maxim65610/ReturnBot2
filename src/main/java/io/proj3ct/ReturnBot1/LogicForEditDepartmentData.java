package io.proj3ct.ReturnBot1;

import io.proj3ct.ReturnBot1.baseClasses.MessageConstants;
import io.proj3ct.ReturnBot1.datebase.DatabaseConnection;
import io.proj3ct.ReturnBot1.datebase.DatebaseTables;
import io.proj3ct.ReturnBot1.keybords.DepartmentsInfo;
import io.proj3ct.ReturnBot1.mail.EmailSender;
import io.proj3ct.ReturnBot1.registration.DataUsersForRegistration;
import io.proj3ct.ReturnBot1.registration.LogicForRegistrationUsers;
import io.proj3ct.ReturnBot1.registration.UsersData;

import java.util.HashMap;
import java.util.Map;

public class LogicForEditDepartmentData {
    // Хранит состояния пользователей для смены департамента
    private final DataForDepartment dataForDepartment = new DataForDepartment();
    private final Map<Long, String> userStatesForEditDepartment = new HashMap<>();
    private final DepartmentsInfo departmentsInfo = new DepartmentsInfo();
    public String getUserStatesForEditDepartment(Long chatID) {
        return userStatesForEditDepartment.getOrDefault(chatID, "0");
    }
    private String checkValidPasswordInput(String messageText, Long userId) {
        if (messageText.equals(System.getenv("password"))) {
            userStatesForEditDepartment.put(userId, "awaiting_numberForEdit");
            return getAllDepartmentsFromBd();
        } else {
            userStatesForEditDepartment.remove(userId);
            return MessageConstants.BAD_PASSWORD_COMMAND_RESPONSE;
        }
    }
    private String getAllDepartmentsFromBd(){
        String messageTextForUser = "";
        String[] allNamesDepartmentsWithId = departmentsInfo.getAllNamesWithId();
        messageTextForUser += MessageConstants.EDIT_DEPARTMENT_COMMAND_RESPONSE + "\n";
        for(String namesDepartmentsWithId : allNamesDepartmentsWithId){
            messageTextForUser += namesDepartmentsWithId + "\n";
        }
        return messageTextForUser;
    }
    public String worksWithEditDepartment(String messageText, Long userId) {
        String currentState = userStatesForEditDepartment.get(userId);
        if ("/edit_department_data".equals(messageText)) {
            userStatesForEditDepartment.put(userId, "awaiting_Password");
            return MessageConstants.PASSWORD_COMMAND_RESPONSE;
        } else if ("awaiting_Password".equals(currentState)) {
            return checkValidPasswordInput(messageText, userId);
        } else if ("awaiting_numberForEdit".equals(currentState)) {
            dataForDepartment.setNumberForEditDepartment(userId, messageText);
            userStatesForEditDepartment.put(userId, "awaiting_newDepartment");
            return MessageConstants.NEW_DEPARTMENT_COMMAND_RESPONSE;
        }
        else if ("awaiting_newDepartment".equals(currentState)) {
            dataForDepartment.setNameForEditDepartment(userId, messageText);
            userStatesForEditDepartment.put(userId, "awaiting_textForDepartment");
            return MessageConstants.TEXT_FOR_DEPARTMENT_COMMAND_RESPONSE;
        }
        else if("awaiting_textForDepartment".equals(currentState)){
            dataForDepartment.setTextForEditDepartment(userId, messageText);
            userStatesForEditDepartment.remove(userId);
            return MessageConstants.CHANGE_DEPARTMENT_COMMAND_RESPONSE;
        }
        return MessageConstants.DEFAULT_RESPONSE;
    }
}
