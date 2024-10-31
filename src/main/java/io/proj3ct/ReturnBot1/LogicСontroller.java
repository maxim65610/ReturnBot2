package io.proj3ct.ReturnBot1;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

/**
 * Контроллер логики обработки сообщений и взаимодействия с пользователями.
 * Обрабатывает различные типы входящих обновлений и управляет состояниями пользователей.
 */
public class LogicСontroller {
    private final LogicForTestABI logicForTestABI = new LogicForTestABI();
    private final TextForMessage textForMessage = new TextForMessage();
    private final DepartmentsInfo departmentsInfo = new DepartmentsInfo();
    private final EmailLogic emailLogic = new EmailLogic();
    private final String username = System.getenv("mail"); // Ваша почта
    private final String password = System.getenv("passwordForMail");
    private final EmailSender emailSender = new EmailSender(username, password);


    /**
     * Проверяет, что делать с переданными данными для клавиатуры.
     *
     * @param data Входные данные для обработки.
     * @return Обработанное сообщение или исходные данные.
     */
    private String checkWhatTodo(String data) {
        if (data.equals("ИЕНИМ") || data.equals("РТФ") || data.equals("ХТИ")) {
            return textForMessage.handleMessage(data);
        } else {
            return data;
        }
    }
    /**
     * Возвращает ответ бота на сообщение от пользователя для работы с /question..
     *
     * @param update Обновление, полученное от Telegram.
     * @param userId ID пользователя, отправившего сообщение.
     * @return Список строк, представляющих сообщения и опции для клавиатуры.
     */
    public List<String> getListStringWithTextToSendAndOptionForKeyboard(Update update, long userId){
        return messageHandlerForKeyboard(update, userId);
    }
    /**
     * Обрабатывает обновления и генерирует ответные сообщения.
     *
     * @param update Обновление, полученное от Telegram.
     * @param userId ID пользователя, отправившего сообщение.
     * @return Список строк, представляющих сообщения и опции для клавиатуры.
     */
    private List<String> messageHandlerForKeyboard(Update update, long userId) {
        List<String> listForWorkWithKeyboardAndMessage = new ArrayList<>();
        if ((update.hasCallbackQuery() && update.getCallbackQuery() != null) &&
                (!(logicForTestABI.getUserStatesForTest(update.getCallbackQuery().getFrom().getId())).equals("0"))) {
            String data = update.getCallbackQuery().getData();

            listForWorkWithKeyboardAndMessage =  logicForTestABI.getDataBd("", userId, data);
            if (logicForTestABI.getUserStatesForTest(userId).equals("awaiting_testABI_11")) {
                logicForTestABI.removeUserStatesForTest(userId);
            }
        }
        else if (update.hasCallbackQuery() && update.getCallbackQuery() != null) {
            String data = update.getCallbackQuery().getData();
            listForWorkWithKeyboardAndMessage.add(departmentsInfo.extract(data, checkWhatTodo(data)));
            listForWorkWithKeyboardAndMessage.add(data);
        }
        else if(update.hasMessage() && update.getMessage() != null) {
            String messageText = update.getMessage().getText();
            userId = update.getMessage().getChatId();
            if ("/question".equals(messageText) || (!(emailLogic.getUserStatesForEmail(userId).equals("0")))) {
                listForWorkWithKeyboardAndMessage.add(emailLogic.getReplyForWorkingWithMail
                        (update, messageText, userId, emailSender));
            }
            else if("/testAbit".equals(messageText)){
                logicForTestABI.getDataBd(messageText, userId, "100");
                listForWorkWithKeyboardAndMessage.add(textForMessage.handleMessage(messageText));
                listForWorkWithKeyboardAndMessage.add(messageText);
            }
            else if("/testres".equals(messageText)){
                listForWorkWithKeyboardAndMessage.add(logicForTestABI.getResult(update.getMessage().getChatId()));
            }
            else {
                listForWorkWithKeyboardAndMessage.add(textForMessage.handleMessage(messageText));
                listForWorkWithKeyboardAndMessage.add(messageText);
            }
        }
        return listForWorkWithKeyboardAndMessage;
    }
}
