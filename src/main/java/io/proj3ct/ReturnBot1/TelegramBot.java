package io.proj3ct.ReturnBot1;


import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс, представляющий Telegram-бота, который наследует функциональность
 * от TelegramLongPollingBot. Обрабатывает обновления от пользователей, управляет
 * состояниями пользователей и отправляет сообщения.
 */
public class TelegramBot extends TelegramLongPollingBot {
    private final String botName;
    private final String botToken;
    private final CommonMessageLogic botLogic;
    private final EmailSender emailSender;
    private final EmailLogic emailLogic;

    // Хранит состояния пользователей
    private Map<Long, String> userStates = new HashMap<>();

    // Хранит электронные адреса пользователей
    private  Map<Long, String> userMails = new HashMap<>();



    private LogicForTestABI logicForTestABI = new LogicForTestABI();
    /**
     * Конструктор класса TelegramBot.
     *
     * @param name  Имя бота.
     * @param token Токен бота.
     * @param logic Логика бота для обработки команд.
     */
    public TelegramBot(String name, String token, CommonMessageLogic logic, EmailSender emailSender, EmailLogic emailLogic) {
        botName = name;
        botToken = token;
        botLogic = logic;
        this.emailSender = emailSender;
        this.emailLogic = emailLogic;
    }


    /**
     * Проверяет, что делать в зависимости от введенных данных.
     *
     * @param data Данные, введенные пользователем.
     * @return Ответ на введенные данные.
     */
    public String checkWhatTodo(String data) {
        if (data.equals("ИЕНИМ") || data.equals("РТФ") || data.equals("ХТИ")) {
            return botLogic.handleMessage(data);
        } else {
            return data;
        }
    }

    /**
     * Обрабатывает обновления от Telegram.
     *
     * @param update Обновление, полученное от Telegram.
     */
    // TODO: Обработать случаи, когда update не содержит callbackQuery или message,
    // чтобы избежать ошибок if((update.hasCallbackQuery() && update.getCallbackQuery() != null)
    @Override
    public void onUpdateReceived(Update update) {
        if((update.hasCallbackQuery() && update.getCallbackQuery() != null) &&
                (!(logicForTestABI.getUserStatesForTest(update.getCallbackQuery().getFrom().getId())).equals("0"))){
            long chatID = update.getCallbackQuery().getFrom().getId();
            String data = update.getCallbackQuery().getData();

            List<String> list_with_dataBD  = logicForTestABI.worksWithTestAPI("",
                    chatID, data);
            if(!logicForTestABI.getUserStatesForTest(chatID).equals("awaiting_testABI_11")){
                sendMessage(chatID, list_with_dataBD.get(0), list_with_dataBD);

            }
            else{
                sendMessage(chatID, "Поздравляю, вы прошли тест. Чтобы узнать результат напишите /testres");
                logicForTestABI.removeUserStatesForTest(chatID);
            }


        }
        else if (update.hasCallbackQuery() && update.getCallbackQuery() != null) {
            String data = update.getCallbackQuery().getData();
            sendMessage(update.getCallbackQuery().getFrom().getId(), checkWhatTodo(data), data);

        }

        if (update.hasMessage() && update.getMessage() != null) {
            String messageText = update.getMessage().getText();
            Long userId = update.getMessage().getChatId();
            String currentState = userStates.get(userId);
            if ("/question".equals(messageText) || "awaiting_email".equals(currentState) || "awaiting_question".equals(currentState)) {
                sendMessage(userId, emailLogic.worksWithMail(update, messageText, userId, currentState, userStates, userMails, emailSender));
            }
            else if("/testAbit".equals(messageText)){
                logicForTestABI.worksWithTestAPI(messageText, userId, "100");
                sendMessage(userId, botLogic.handleMessage(messageText), messageText);
            }
            else if("/testres".equals(messageText)){
                sendMessage(update.getMessage().getChatId(), logicForTestABI.getResult(update.getMessage().getChatId()));
            }
            else {
                sendMessage(update.getMessage().getChatId(), botLogic.handleMessage(messageText), messageText);

            }
        }
    }
    /**
     * Отправляет сообщение в указанный чат с заданным текстом и данными для клавиатуры.
     *
     * @param chatId Идентификатор чата, в который будет отправлено сообщение.
     * @param textToSend Текст сообщения, которое будет отправлено.
     * @param list_with_dataBD Список данных, используемых для настройки клавиатуры, связанной с сообщением.
     */
    void sendMessage(long chatId, String textToSend, List<String> list_with_dataBD) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        KeyboardLogic keyboardLogicObj = new KeyboardLogic();
        keyboardLogicObj.keyboardforTestABI(message, list_with_dataBD);
        try {

            execute(message);
        } catch (TelegramApiException e) {
            // TODO: Логирование ошибки отправки сообщения
        }
    }
    /**
     * Отправляет сообщение пользователю с указанным ID и текстом.
     *
     * @param chatId     ID чата, куда отправляется сообщение.
     * @param textToSend Текст сообщения.
     * @param data       Дополнительные данные для обработки клавиатуры.
     */
    void sendMessage(long chatId, String textToSend, String data) {

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));


        DepartInfoBD DepartInfoBD = new DepartInfoBD();
        textToSend = DepartInfoBD.takeInfo(data,textToSend);

        message.setText(textToSend);

        KeyboardLogic keyboardLogicObj = new KeyboardLogic();
        keyboardLogicObj.keyboards(message, data);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            // TODO: Логирование ошибки отправки сообщения
        }
    }

    /**
     * Отправляет сообщение пользователю без дополнительных параметров.
     *
     * @param chatId     ID чата, куда отправляется сообщение.
     * @param textToSend Текст сообщения.
     */
    void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        if (textToSend == null) {
            textToSend = "Сообщение не может быть пустым."; // Сообщение по умолчанию
        }
        message.setText(textToSend);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            // Обработка исключения (опционально: логирование)
        }
    }

     public String getBotUsername() {return botName;}

     public String getBotToken() {return botToken;}
}

