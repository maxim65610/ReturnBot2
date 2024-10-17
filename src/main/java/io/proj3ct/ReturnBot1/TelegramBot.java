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
    private final MessageLogic botLogic;

    // Хранит состояния пользователей
    private Map<Long, String> userStates = new HashMap<>();

    // Хранит электронные адреса пользователей
    private  Map<Long, String> userMails = new HashMap<>();


    private Map<Long, String> userStatesforTest = new HashMap<>();

    private MessageAndKeyboardLogic messageAndKeyboardLogic = new MessageAndKeyboardLogic();
    /**
     * Конструктор класса TelegramBot.
     *
     * @param name  Имя бота.
     * @param token Токен бота.
     * @param logic Логика бота для обработки команд.
     */
    public TelegramBot(String name, String token, MessageLogic logic) {
        botName = name;
        botToken = token;
        botLogic = logic;


    }


    /**
     * Проверяет, что делать в зависимости от введенных данных.
     *
     * @param data Данные, введенные пользователем.
     * @return Ответ на введенные данные.
     */
    public String checkWhatTodo(String data) {
        if (data.equals("ИЕНИМ") || data.equals("РТФ") || data.equals("ХТИ")) {
            return botLogic.slogic(data);
        } else {
            return data;
        }
    }

    /**
     * Обрабатывает обновления от Telegram.
     *
     * @param update Обновление, полученное от Telegram.
     */
    @Override
    public void onUpdateReceived(Update update) {
        if((update.hasCallbackQuery() && update.getCallbackQuery() != null) && (!userStatesforTest.isEmpty())){

            String data = update.getCallbackQuery().getData();
            List<String> list_with_dataBD  = messageAndKeyboardLogic.worksWithTestAPI("", update.getCallbackQuery().getFrom().getId(), userStatesforTest, data);
            sendMessage(update.getCallbackQuery().getFrom().getId(), list_with_dataBD.get(0), list_with_dataBD);
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
                String answer = botLogic.worksWithMail(update, messageText, userId, currentState, userStates, userMails);
                sendMessage(userId, answer);
            }
            else if("/test".equals(messageText)){

                messageAndKeyboardLogic.worksWithTestAPI(messageText, userId, userStatesforTest, "100");
                sendMessage(userId, botLogic.slogic(messageText), messageText);
            }
            else {
                sendMessage(update.getMessage().getChatId(), botLogic.slogic(messageText), messageText);

            }
        }
    }
    void sendMessage(long chatId, String textToSend, List<String> list_with_dataBD) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        KeyboardLogic keyboardLogicObj = new KeyboardLogic();
        keyboardLogicObj.keyboardforTestAPI(message, list_with_dataBD);
        try {

            execute(message);
        } catch (TelegramApiException e) {
            // Обработка исключения (опционально: логирование)
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
            // Обработка исключения (опционально: логирование)
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

    /**
     * Возвращает имя бота.
     *
     * @return Имя бота.
     */
    @Override
    public String getBotUsername() {
        return botName;
    }

    /**
     * Возвращает токен бота.
     *
     * @return Токен бота.
     */
    @Override
    public String getBotToken() {
        return botToken;
    }
}

