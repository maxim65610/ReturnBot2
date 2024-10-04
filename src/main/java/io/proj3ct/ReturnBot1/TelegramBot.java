package io.proj3ct.ReturnBot1;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Представляет собой Telegram-бота, который наследует класс TelegramLongPollingBot.
 * Этот бот может отвечать на простые команды, такие как /start и /help и /question,
 * а также ответить на вопросы абитуриентов.
 */
public class TelegramBot extends TelegramLongPollingBot {
    private final String botName;
    private final String botToken;
    private final LogicBrain botLogic;

    /**
     * Метод, который устанавливает константы botName botToken и логику работы botLogic
     */
    public TelegramBot(String name, String token, LogicBrain logic) {
        botName = name;
        botToken = token;
        botLogic = logic;
    }
    /**
     * Метод, который обрабатывает data, и если в ней хранятся хеши первой клавиатур, то он обрабатывает сообщение через логику
     * иначе просто возвращает хеш
     */
    public String checkWhatTodo(String data){

        if(data.equals("ИЕНИМ")||data.equals("РТФ")||data.equals("ХТИ")){
            return botLogic.slogic(data);
        }
        else {
            return data;
        }
    }
    /**
     * Метод, который обрабатывает полученные сообщения и хеши от клавиатур
     */
    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasCallbackQuery() && update.getCallbackQuery() != null) {
            String data = update.getCallbackQuery().getData();
            sendMessage(update.getCallbackQuery().getFrom().getId(), checkWhatTodo(data), data);
        }
        if (update.hasMessage() && update.getMessage() != null) {
            String messageText = update.getMessage().getText();
            sendMessage(update.getMessage().getChatId(), botLogic.slogic(messageText),messageText);
        }
    }
    /**
     * Метод, который отправляет сообщения, при необходимости добавляет к ним клавиатуры
     */
    void sendMessage(long chatId, String textToSend, String data) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));

        dataInfoTo infoObj = new dataInfoTo();
        textToSend = infoObj.takeInfo(textToSend);
        message.setText(textToSend);

        keyboardLogic keyboardLogicObj = new keyboardLogic();
        keyboardLogicObj.keyboards(message, data);

        try {

            execute(message);
        } catch (TelegramApiException e) {
            // Обработка исключения (опционально: логирование)
        }
    }

    /**
     * Метод, который возвращает botName
     */
    @Override
    public String getBotUsername() {
        return botName;
    }

    /**
     * Метод, который возвращает botToken
     */
    @Override
    public String getBotToken() {
        return botToken;
    }
}
