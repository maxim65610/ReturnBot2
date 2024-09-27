package io.proj3ct.ReturnBot1;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Представляет собой Telegram-бота, который наследует класс TelegramLongPollingBot.
 * Этот бот может отвечать на простые команды, такие как /start и /help,
 * а также повторять текстовые сообщения, отправленные ему.
 */
public class TelegramBot extends TelegramLongPollingBot {
    private final String botName;
    private final String botToken;
    private final LogicBrain botLogic;

    /**
     * Конструктор класса TelegramBot.
     *
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
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            String answer = botLogic.slogic(messageText);
            sendMessage(chatId, answer);
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

    /**
     * Возвращает имя бота, указанное в конфигурации.
     *
     * @return Имя бота.
     */
    @Override
    public String getBotUsername() {
        return botName;
    }

    /**
     * Возвращает токен бота, указанный в конфигурации.
     *
     * @return Токен бота.
     */
    @Override
    public String getBotToken() {
        return botToken;
    }
}

