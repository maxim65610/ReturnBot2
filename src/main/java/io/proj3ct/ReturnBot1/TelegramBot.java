package io.proj3ct.ReturnBot1;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс, представляющий Telegram-бота, который наследует функциональность
 * от TelegramLongPollingBot. Обрабатывает обновления от пользователей, управляет
 * состояниями пользователей и отправляет сообщения.
 */
public class TelegramBot implements LongPollingSingleThreadUpdateConsumer {
    private final TelegramClient telegramClient;
    private final String botToken;
    private final CommonMessageLogic botLogic;
    private final EmailSender emailSender;
    private final EmailLogic emailLogic;

    // Хранит состояния пользователей
    private Map<Long, String> userStates = new HashMap<>();
    // Хранит электронные адреса пользователей
    private  Map<Long, String> userMails = new HashMap<>();
    // Хранит логику для теста
    private LogicForTestABI logicForTestABI = new LogicForTestABI();
    /**
     * Конструктор класса TelegramBot.
     *
     * @param token Токен бота.
     * @param logic Логика бота для обработки команд.
     */
    public TelegramBot(String token, CommonMessageLogic logic, EmailSender emailSender, EmailLogic emailLogic) {
        botToken = token;
        botLogic = logic;
        this.emailSender = emailSender;
        this.emailLogic = emailLogic;
        telegramClient = new OkHttpTelegramClient(botToken);
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
    // чтобы избежать ошибок if((update.hasCallbackQuery() && update.getCallbackQuery() != null)
    @Override
    public void consume(Update update) {
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

        SendMessage message = SendMessage // Create a message object
                .builder()
                .chatId(chatId)
                .text(textToSend)
                .build();

        KeyboardLogic keyboardLogicObj = new KeyboardLogic();
        keyboardLogicObj.keyboardforTestABI(message, list_with_dataBD);
        try {
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            System.out.println("Ошибка извлечения данных: " + e.getMessage());
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
        DepartInfoBD DepartInfoBD = new DepartInfoBD();
        textToSend = DepartInfoBD.takeInfo(data,textToSend);

        SendMessage message = SendMessage // Create a message object
                .builder()
                .chatId(chatId)
                .text(textToSend)
                .build();

        KeyboardLogic keyboardLogicObj = new KeyboardLogic();
        keyboardLogicObj.keyboards(message, data);
        try {
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            System.out.println("Ошибка извлечения данных: " + e.getMessage());
        }
    }
    /**
     * Отправляет сообщение пользователю без дополнительных параметров.
     *
     * @param chatId     ID чата, куда отправляется сообщение.
     * @param textToSend Текст сообщения.
     */
    void sendMessage(long chatId, String textToSend) {

        if (textToSend == null) {
            textToSend = "Сообщение не может быть пустым."; // Сообщение по умолчанию
        }

        SendMessage message = SendMessage // Create a message object
                .builder()
                .chatId(chatId)
                .text(textToSend)
                .build();
        try {
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            System.out.println("Ошибка извлечения данных: " + e.getMessage());
        }
    }
}

