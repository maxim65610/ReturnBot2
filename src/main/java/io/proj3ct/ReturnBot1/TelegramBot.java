package io.proj3ct.ReturnBot1;


import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс, представляющий Telegram-бота, который наследует функциональность
 * от TelegramLongPollingBot. Обрабатывает обновления от пользователей, управляет
 * состояниями пользователей и отправляет сообщения.
 */
public class TelegramBot extends TelegramLongPollingBot {
    private final String botName;
    private final String botToken;
    private final LogicBrain botLogic;

    // Хранит состояния пользователей
    Map<Long, String> userStates = new HashMap<>();
    // Хранит электронные адреса пользователей
    Map<Long, String> userMails = new HashMap<>();

    /**
     * Конструктор класса TelegramBot.
     *
     * @param name  Имя бота.
     * @param token Токен бота.
     * @param logic Логика бота для обработки команд.
     */
    public TelegramBot(String name, String token, LogicBrain logic) {
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
        if (update.hasCallbackQuery() && update.getCallbackQuery() != null) {
            String data = update.getCallbackQuery().getData();
            sendMessage(update.getCallbackQuery().getFrom().getId(), checkWhatTodo(data), data);
        }
        if (update.hasMessage() && update.getMessage() != null) {
            String messageText = update.getMessage().getText();
            Long userId = update.getMessage().getChatId();
            String currentState = userStates.get(userId);
            if ("/question".equals(messageText) || "awaiting_email".equals(currentState) || "awaiting_question".equals(currentState)) {
                worksWithMail(update, messageText, userId, currentState);
            } else {
                sendMessage(update.getMessage().getChatId(), botLogic.slogic(messageText), messageText);
            }
        }
    }

    /**
     * Обрабатывает ввод пользователя, связанный с электронной почтой и вопросами.
     *
     * @param update      Обновление, полученное от Telegram.
     * @param messageText Сообщение, введенное пользователем.
     * @param userId     ID пользователя.
     * @param currentState Текущее состояние пользователя.
     */
    public void worksWithMail(Update update, String messageText, Long userId, String currentState) {
        if ("/question".equals(messageText)) {
            userStates.put(userId, "awaiting_email");
            sendMailMessage(userId, botLogic.slogic(messageText));
        } else if ("awaiting_email".equals(currentState)) {
            String mailUser = update.getMessage().getText();
            String anwserhandleEmailInput = botLogic.handleEmailInput(mailUser);
            sendMailMessage(userId, anwserhandleEmailInput);
            if (anwserhandleEmailInput.equals("Почта указана корректно, напишите ваш вопрос")) {
                userMails.put(userId, mailUser);
                userStates.put(userId, "awaiting_question");
            } else {
                userMails.remove(userId, mailUser);
            }
        } else if ("awaiting_question".equals(currentState)) {
            String question = update.getMessage().getText();
            String mailUser = userMails.get(userId);
            botLogic.sendMail(mailUser, question);
            userStates.remove(userId);
            userMails.remove(userId);
            sendMailMessage(userId, "Ваш вопрос отправлен");
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
     * Отправляет сообщение пользователю без дополнительных параметров.
     *
     * @param chatId     ID чата, куда отправляется сообщение.
     * @param textToSend Текст сообщения.
     */
    void sendMailMessage(long chatId, String textToSend) {
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

