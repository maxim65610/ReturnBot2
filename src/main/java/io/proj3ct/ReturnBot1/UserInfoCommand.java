package io.proj3ct.ReturnBot1;

import java.util.ArrayList;
import java.util.List;

public class UserInfoCommand implements Command {
    private final UsersData usersData = new UsersData();
    private final DatabaseConnection databaseConnection = new DatabaseConnection();

    @Override
    public List<String> execute(long userId, String messageText) {
        List<String> response = new ArrayList<>();
        if ("/userInfo".equals(messageText)) {
            if (usersData.checkUserIdExistsInRegistrationDataTable(userId, databaseConnection)) {
                response.add(usersData.getRegistrationData(userId, databaseConnection));
            } else {
                response.add(MessageConstants.NO_REGISTRATION);
            }
        }
        return response;
    }
}
