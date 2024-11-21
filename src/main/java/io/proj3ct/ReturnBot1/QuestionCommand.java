package io.proj3ct.ReturnBot1;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionCommand implements Command{
    private final EmailLogic emailLogic = new EmailLogic();
    private final EmailSender emailSender;
    private final DatabaseConnection databaseConnection = new DatabaseConnection();
    public QuestionCommand(EmailSender emailSender){
        this.emailSender = emailSender;
    }
    @Override
    public List<String> execute(long userId, String messageText) {
        List<String> response = new ArrayList<>();
        if ("/question".equals(messageText) || !(emailLogic.getUserStatesForEmail(userId).equals("0"))) {
            response.add(emailLogic.worksWithMail(messageText, userId, emailSender, databaseConnection));
        }
        return response;
    }
}
