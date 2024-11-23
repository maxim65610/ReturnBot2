package io.proj3ct.ReturnBot1.Command;

import io.proj3ct.ReturnBot1.LogicForNewDepartmentData;

import java.util.ArrayList;
import java.util.List;

public class NewDepartmentCommand implements Command{
    private final LogicForNewDepartmentData logicForNewDepartmentData= new LogicForNewDepartmentData();
    public List<String> execute(long userId, String messageText, boolean flagForKeyboard) {
        List<String> response = new ArrayList<>();
        if ("/new_department_data".equals(messageText) ||
                (!logicForNewDepartmentData.getUserStatesForNewDepartment(userId).equals("0"))) {
            response.add(logicForNewDepartmentData.worksWithNewDepartment(messageText, userId));
        }
        return response;
    }
}
