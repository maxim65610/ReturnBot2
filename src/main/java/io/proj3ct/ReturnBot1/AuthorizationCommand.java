package io.proj3ct.ReturnBot1;

import java.util.ArrayList;
import java.util.List;

public class AuthorizationCommand implements Command {
    private final LogicForRegistrationUsers logicForRegistrationUsers = new LogicForRegistrationUsers();
    private final EmailSender emailSender;
    public AuthorizationCommand(EmailSender emailSender) {
        this.emailSender = emailSender;
    }
    @Override
    public List<String> execute(long userId, String messageText) {
        List<String> response = new ArrayList<>();
        if ("/authorization".equals(messageText) || !logicForRegistrationUsers.getUserStatesForRegistration(userId).equals("0")) {
            response.add(logicForRegistrationUsers.worksWithRegistration(messageText, userId, emailSender, logicForRegistrationUsers));
        }
        return response;
    }
}
