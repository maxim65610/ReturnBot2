package io.proj3ct.ReturnBot1;

import java.util.ArrayList;
import java.util.List;

public class UserDataDeleteCommand implements Command {
    private final UsersData usersData= new UsersData();
    private final LogicForRegistrationUsers logicForRegistrationUsers = new LogicForRegistrationUsers();

    @Override
    public List<String> execute(long userId, String messageText) {
        List<String> response = new ArrayList<>();
        if ("/userDataDell".equals(messageText)) {
            usersData.deleteData(userId, logicForRegistrationUsers.getDatabaseConnection());
            response.add(MessageConstants.DATA_DELETED);
        }
        return response;
    }
}