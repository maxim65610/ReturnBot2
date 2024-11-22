package io.proj3ct.ReturnBot1.Command;

import io.proj3ct.ReturnBot1.datebase.DatabaseConnection;
import io.proj3ct.ReturnBot1.mail.EmailLogic;
import io.proj3ct.ReturnBot1.mail.EmailSender;

import java.util.ArrayList;
import java.util.List;
/**
 * Команда для обработки запросов на отправку электронных писем.
 */
public class QuestionCommand implements Command {
    private final EmailLogic emailLogic = new EmailLogic();
    private final EmailSender emailSender;
    private final DatabaseConnection databaseConnection = new DatabaseConnection();
    /**
     * Конструктор для инициализации команды с отправителем электронной почты.
     *
     * @param emailSender отправитель электронной почты
     */
    public QuestionCommand(EmailSender emailSender){
        this.emailSender = emailSender;
    }
    /**
     * Выполняет команду по отправке электронного письма или обработке запроса.
     *
     * @param userId ID пользователя, отправившего сообщение
     * @param messageText текст сообщения
     * @param flagForKeyboard флаг для отображения клавиатуры
     * @return Список строк с результатом обработки запроса
     */
    @Override
    public List<String> execute(long userId, String messageText, boolean flagForKeyboard) {
        List<String> response = new ArrayList<>();
        if ("/question".equals(messageText) || !(emailLogic.getUserStatesForEmail(userId).equals("0"))) {
            response.add(emailLogic.worksWithMail(messageText, userId, emailSender, databaseConnection));
        }
        return response;
    }
}
