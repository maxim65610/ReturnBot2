package io.proj3ct.ReturnBot1.service;

import io.proj3ct.ReturnBot1.config.BotConfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
/**
 * Представляет собой Telegram-бота, который наследует класс TelegramLongPollingBot.
 * Этот бот может отвечать на простые команды, такие как /start и /help,
 * а также повторять текстовые сообщения, отправленные ему.
 */
public class TelegramBot extends TelegramLongPollingBot {

    final BotConfig config;

    /**
     * Конструктор класса TelegramBot.
     *
     * @param config Объект конфигурации, содержащий имя и токен бота.
     */
    public TelegramBot(BotConfig config) {
        this.config = config;
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
            switch (messageText) {
                case "/start":
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;
                case "/help":
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;
                default:
                    sendMessage(chatId, "Вы ввели: " + messageText);
            }
        }
    }

    /**
     * Обрабатывает команду /start, полученную от пользователя.
     *
     * @param chatId ID чата, в котором была получена команда.
     * @param name   Имя пользователя, который отправил команду.
     */
    private void startCommandReceived(long chatId, String name) {
        String answer = "Привет, я могу повторять за тобой.";
        sendMessage(chatId, answer);
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
        return config.getBotName();
    }

    /**
     * Возвращает токен бота, указанный в конфигурации.
     *
     * @return Токен бота.
     */
    @Override
    public String getBotToken() {
        return config.getToken();
    }
}

