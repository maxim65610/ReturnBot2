package io.proj3ct.ReturnBot1;

import java.util.ArrayList;
import java.util.List;

public class UserDataChangeCommand implements Command {
    private final LogicForChangeDataUsers logicForChangeDataUsers = new LogicForChangeDataUsers();
    private final EmailSender emailSender;

    public UserDataChangeCommand(EmailSender emailSender) {
        this.emailSender = emailSender;
    }
    @Override
    public List<String> execute(long userId, String messageText) {
        List<String> response = new ArrayList<>();
        if ("/userDataChange".equals(messageText) || !logicForChangeDataUsers.getUserStatesForChangeData(userId).equals("0")) {
            response.add(logicForChangeDataUsers.worksWithChangeData(messageText, userId, emailSender));
        }
        return response;
    }
}
