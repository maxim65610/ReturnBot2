package io.proj3ct.ReturnBot1;

import io.proj3ct.ReturnBot1.baseClasses.MessageConstants;
import io.proj3ct.ReturnBot1.keybords.DepartmentsInfo;

import java.util.HashMap;
import java.util.Map;

public class LogicForDeleteDepartment {
    private final DataForDepartment dataForDepartment = new DataForDepartment();
    private final Map<Long, String> userStatesForDeleteDepartment = new HashMap<>();
    private final DepartmentsInfo departmentsInfo = new DepartmentsInfo();
    public String getUserStatesForDeleteDepartment(Long chatID) {
        return userStatesForDeleteDepartment.getOrDefault(chatID, "0");
    }
    private String checkValidPasswordInput(String messageText, Long userId) {
        if (messageText.equals(System.getenv("password"))) {
            userStatesForDeleteDepartment.put(userId, "awaiting_numberForDelete");
            return getAllDepartmentsFromBd();
        } else {
            userStatesForDeleteDepartment.remove(userId);
            return MessageConstants.BAD_PASSWORD_COMMAND_RESPONSE;
        }
    }
    private String getAllDepartmentsFromBd(){
        String messageTextForUser = "";
        String[] allNamesDepartmentsWithId = departmentsInfo.getAllNamesWithId();
        messageTextForUser += MessageConstants.CORRECT_PASSWORD_AND_INSTITUTE_DELETE_COMMAND_RESPONSE + "\n";
        for(String namesDepartmentsWithId : allNamesDepartmentsWithId){
            messageTextForUser += namesDepartmentsWithId + "\n";
        }
        return messageTextForUser;
    }
    public String worksWithDeleteDepartment(String messageText, Long userId) {
        String currentState = userStatesForDeleteDepartment.get(userId);
        if ("/delete_department_data".equals(messageText)) {
            userStatesForDeleteDepartment.put(userId, "awaiting_password");
            return MessageConstants.PASSWORD_COMMAND_RESPONSE;
        } else if ("awaiting_password".equals(currentState)) {
            return checkValidPasswordInput(messageText, userId);
        }
        else if("awaiting_numberForDelete".equals(currentState)){
            dataForDepartment.setNumberForDeleteDepartment(userId, messageText);
            userStatesForDeleteDepartment.remove(userId);
            System.out.println(dataForDepartment.getNumberForDeleteDepartment(userId));
            return MessageConstants.SUCCESSFUL_DELETE_DEPARTMENT_COMMAND_RESPONSE;
        }
        return MessageConstants.DEFAULT_RESPONSE;
    }
}
