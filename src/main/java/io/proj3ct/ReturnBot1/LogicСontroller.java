package io.proj3ct.ReturnBot1;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * обрабатывает сообщения и контролирует логику других классов, связанных с логикой
 */
public class LogicСontroller {
    private LogicForTestABI logicForTestABI = new LogicForTestABI();
    private TextForCommonMessage textForCommonMessage = new TextForCommonMessage();
    private DepartmentsInfo DepartmentsInfo = new DepartmentsInfo();
    private EmailLogic emailLogic = new EmailLogic();
    private Map<Long, List<String>> mapForWorkWithKeyboardAndMessage = new ConcurrentHashMap<>();
    private String checkWhatTodo(String data) {
        if (data.equals("ИЕНИМ") || data.equals("РТФ") || data.equals("ХТИ")) {
            return textForCommonMessage.handleMessage(data);
        } else {
            return data;
        }
    }
    public void messageHandlerForKeyboard(Update update, EmailSender emailSender) {

        List<String> listForWorkWithKeyboardAndMessage = new ArrayList<>();
        long userId = 0;
        if ((update.hasCallbackQuery() && update.getCallbackQuery() != null) &&
                (!(logicForTestABI.getUserStatesForTest(update.getCallbackQuery().getFrom().getId())).equals("0"))) {
            userId= update.getCallbackQuery().getFrom().getId();
            String data = update.getCallbackQuery().getData();

            listForWorkWithKeyboardAndMessage =  logicForTestABI.worksWithTestAPI("", userId, data);
            if (logicForTestABI.getUserStatesForTest(userId).equals("awaiting_testABI_11")) {
                logicForTestABI.removeUserStatesForTest(userId);
            }
        }
        else if (update.hasCallbackQuery() && update.getCallbackQuery() != null) {
            userId = update.getCallbackQuery().getFrom().getId();
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
        if(mapForWorkWithKeyboardAndMessage.isEmpty()){
            mapForWorkWithKeyboardAndMessage.put(userId, listForWorkWithKeyboardAndMessage);
        }
        else{
            mapForWorkWithKeyboardAndMessage.remove(userId);
            mapForWorkWithKeyboardAndMessage.put(userId, listForWorkWithKeyboardAndMessage);
        }
    }
    public List<String> getListForWorkWithKeyboardAndMessage(Long userId){
        return mapForWorkWithKeyboardAndMessage.get(userId);
    }
}
