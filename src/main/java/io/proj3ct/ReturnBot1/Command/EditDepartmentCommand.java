package io.proj3ct.ReturnBot1.Command;

import io.proj3ct.ReturnBot1.keybords.LogicForEditDepartmentData;

import java.util.ArrayList;
import java.util.List;

public class EditDepartmentCommand implements Command{
    private final LogicForEditDepartmentData logicForEditDepartmentData = new LogicForEditDepartmentData();
    public List<String> execute(long userId, String messageText, boolean flagForKeyboard) {
        List<String> response = new ArrayList<>();
        if ("/edit_department_data".equals(messageText) ||
                (!logicForEditDepartmentData.getUserStatesForEditDepartment(userId).equals("0"))) {
            response.add(logicForEditDepartmentData.worksWithEditDepartment(messageText, userId));
        }
        return response;
    }
}

