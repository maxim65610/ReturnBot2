package io.proj3ct.ReturnBot1;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;


/**
 * Контроллер логики обработки сообщений и взаимодействия с пользователями.
 * Обрабатывает различные типы входящих обновлений и управляет состояниями пользователей.
 */
public class LogicСontroller {
    private LogicForTestABI logicForTestABI = new LogicForTestABI();
    private TextForCommonMessage textForCommonMessage = new TextForCommonMessage();
    private DepartmentsInfo DepartmentsInfo = new DepartmentsInfo();
    private EmailLogic emailLogic = new EmailLogic();
    /**
     * Проверяет, что делать с переданными данными для клавиатуры.
     *
     * @param data Входные данные для обработки.
     * @return Обработанное сообщение или исходные данные.
     */
    private String checkWhatTodo(String data) {
        if (data.equals("ИЕНИМ") || data.equals("РТФ") || data.equals("ХТИ")) {
            return textForCommonMessage.handleMessage(data);
        } else {
            return data;
        }
    }
    /**
     * Обрабатывает обновления и генерирует ответные сообщения.
     *
     * @param update Обновление, полученное от Telegram.
     * @param emailSender Объект для отправки электронных писем.
     * @param userId ID пользователя, отправившего сообщение.
     * @return Список строк, представляющих сообщения и опции для клавиатуры.
     */
    public List<String> messageHandlerForKeyboard(Update update, EmailSender emailSender, long userId) {
        List<String> listForWorkWithKeyboardAndMessage = new ArrayList<>();
        if ((update.hasCallbackQuery() && update.getCallbackQuery() != null) &&
                (!(logicForTestABI.getUserStatesForTest(update.getCallbackQuery().getFrom().getId())).equals("0"))) {
            String data = update.getCallbackQuery().getData();

            listForWorkWithKeyboardAndMessage =  logicForTestABI.worksWithTestAPI("", userId, data);
            if (logicForTestABI.getUserStatesForTest(userId).equals("awaiting_testABI_11")) {
                logicForTestABI.removeUserStatesForTest(userId);
            }
        }
        else if (update.hasCallbackQuery() && update.getCallbackQuery() != null) {
            String data = update.getCallbackQuery().getData();
            listForWorkWithKeyboardAndMessage.add(DepartmentsInfo.extract(checkWhatTodo(data), data));
            listForWorkWithKeyboardAndMessage.add(data);
        }
        else if(update.hasMessage() && update.getMessage() != null) {
            String messageText = update.getMessage().getText();
            userId = update.getMessage().getChatId();
            if ("/question".equals(messageText) || (!(emailLogic.getUserStatesForEmail(userId).equals("0")))) {
                listForWorkWithKeyboardAndMessage.add(emailLogic.worksWithMail(update, messageText, userId, emailSender));
            }
            else if("/testAbit".equals(messageText)){
                logicForTestABI.worksWithTestAPI(messageText, userId, "100");
                listForWorkWithKeyboardAndMessage.add(textForCommonMessage.handleMessage(messageText));
                listForWorkWithKeyboardAndMessage.add(messageText);
            }
            else if("/testres".equals(messageText)){
                listForWorkWithKeyboardAndMessage.add(logicForTestABI.getResult(update.getMessage().getChatId()));
            }
            else {
                listForWorkWithKeyboardAndMessage.add(textForCommonMessage.handleMessage(messageText));
                listForWorkWithKeyboardAndMessage.add(messageText);
            }
        }
        return listForWorkWithKeyboardAndMessage;
    }
}
