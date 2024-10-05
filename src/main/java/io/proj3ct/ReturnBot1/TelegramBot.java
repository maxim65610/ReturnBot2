package io.proj3ct.ReturnBot1;


import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;

/**
 * Представляет собой Telegram-бота, который наследует класс TelegramLongPollingBot.
 * Этот бот может отвечать на простые команды, такие как /start и /help,
 * а также повторять текстовые сообщения, отправленные ему.
 */
public class TelegramBot extends TelegramLongPollingBot {
    private final String botName;
    private final String botToken;
    private final LogicBrain botLogic;

    private Map<Long, String> userStates = new HashMap<>();
    private Map<Long, String> userMails = new HashMap<>();
    public TelegramBot(String name, String token, LogicBrain logic) {
        botName = name;
        botToken = token;
        botLogic = logic;
    }

    public String checkWhatTodo(String data){

        if(data.equals("ИЕНИМ")||data.equals("РТФ")||data.equals("ХТИ")){
            return botLogic.slogic(data);
        }
        else {
            return data;
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasCallbackQuery() && update.getCallbackQuery() != null) {
            String data = update.getCallbackQuery().getData();
            sendMessage(update.getCallbackQuery().getFrom().getId(), checkWhatTodo(data), data);
        }
        if (update.hasMessage() && update.getMessage() != null) {
            String messageText = update.getMessage().getText();

            Long userId = update.getMessage().getChatId();
            String currentState = userStates.get(userId);
            if ("/question".equals(messageText)){
                userStates.put(userId, "awaiting_email");
                sendMessage2(userId, botLogic.slogic(messageText));
            }
            else if ("awaiting_email".equals(currentState)){
                String mailUser = update.getMessage().getText();
                String anwserhandleEmailInput = botLogic.handleEmailInput(mailUser);
                sendMessage2(userId, anwserhandleEmailInput);
                if(anwserhandleEmailInput.equals("Почта указана корректно, напишите ваш вопрос")){
                    userMails.put(userId, mailUser);
                    userStates.put(userId, "awaiting_question");
                }
                else{
                    userMails.remove(userId, mailUser);
                }
            }
            else if("awaiting_question".equals(currentState)) {
                String question = update.getMessage().getText();
                String mailUSer = userMails.get(userId);
                botLogic.sendMail(mailUSer, question);
                userStates.remove(userId);
                userMails.remove(userId);
                sendMessage2(userId, "Ваш вопрос отправлен");
            }
            else {
                sendMessage(update.getMessage().getChatId(), botLogic.slogic(messageText), messageText);
            }
        }
    }

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
    void sendMessage2(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            // Обработка исключения (опционально: логирование)
        }
    }


    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}

