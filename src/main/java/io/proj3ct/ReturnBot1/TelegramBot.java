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
    /**
     * Поля класса
     */
    private final String botName;
    private final String botToken;
    private final LogicBrain botLogic;

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
    // Хранит состояние пользователей
    private Map<Long, String> userStates = new HashMap<>();

    // Хранит адреса электронной почты пользователей
    private Map<Long, String> userMails = new HashMap<>();
    /**
     * Конструктор класса TelegramBot.
     *
     * @param name имя бота
     * @param token токен бота
     * @param logic объект класса LogicBrain
     */
    public TelegramBot(String name, String token, LogicBrain logic) {
        botName = name;
        botToken = token;
        botLogic = logic;
    }

    /**
     * Вызывается, когда бот получает обновление от Telegram.
     * Этот метод обрабатывает входящие сообщения и выполняет соответствующие действия.
     *
     * @param update Объект обновления, содержащий информацию о входящем сообщении.
     */
    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage() != null) {
            String messageText = update.getMessage().getText();
            Long userId = update.getMessage().getChatId();
            String currentState = userStates.get(userId);
            if("/question".equals(messageText) || "awaiting_email".equals(currentState) || "awaiting_question".equals(currentState)){
                WorkWithMail(update, messageText, userId, currentState);
            }
            else{
                sendMessage(userId, botLogic.slogic(messageText));
            }


        }
    }

    public void WorkWithMail(Update update, String messageText, Long userId, String currentState){
        if (update.hasMessage() && update.getMessage() != null) {
            if ("/question".equals(messageText)){
                userStates.put(userId, "awaiting_email");
                sendMessage(userId, botLogic.slogic(messageText));
            }
            else if ("awaiting_email".equals(currentState)){
                String mailUser = update.getMessage().getText();
                String anwserhandleEmailInput = botLogic.handleEmailInput(mailUser);
                sendMessage(userId, anwserhandleEmailInput);
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
                sendMessage(userId, "Ваш вопрос отправлен");
            }
        }
    }
    /**
     * Отправляет сообщение в указанный чат.
     *
     * @param chatId    ID чата, в который будет отправлено сообщение.
     * @param textToSend Текстовое содержимое сообщения, которое будет отправлено.
     */
    void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            // Обработка исключения (опционально: логирование)
        }
    }

}



