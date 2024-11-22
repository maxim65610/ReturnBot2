package io.proj3ct.ReturnBot1.Command;

import io.proj3ct.ReturnBot1.EmailSender;
import io.proj3ct.ReturnBot1.LogicForRegistrationUsers;

import java.util.ArrayList;
import java.util.List;
/**
 * Команда для авторизации пользователя, обрабатывающая запросы на регистрацию.
 */
public class AuthorizationCommand implements Command {
    private final LogicForRegistrationUsers logicForRegistrationUsers = new LogicForRegistrationUsers();
    private final EmailSender emailSender;
    /**
     * Конструктор для инициализации команды с отправителем электронной почты.
     *
     * @param emailSender отправитель электронной почты для регистрации
     */
    public AuthorizationCommand(EmailSender emailSender) {
        this.emailSender = emailSender;
    }
    /**
     * Выполняет команду авторизации и регистрации пользователя.
     *
     * @param userId ID пользователя, отправившего сообщение
     * @param messageText текст сообщения
     * @param flagForKeyboard флаг для отображения клавиатуры
     * @return Список строк с результатами авторизации или регистрации
     */
    @Override
    public List<String> execute(long userId, String messageText, boolean flagForKeyboard) {
        List<String> response = new ArrayList<>();
        if ("/authorization".equals(messageText) || !logicForRegistrationUsers.
                getUserStatesForRegistration(userId).equals("0")) {
            response.add(logicForRegistrationUsers.worksWithRegistration(messageText,
                    userId, emailSender, logicForRegistrationUsers));
        }
        return response;
    }
}
