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
 * Telegram-бота, который наследует функциональность
 * от LongPollingSingleThreadUpdateConsumer. Обрабатывает обновления от пользователей, управляет
 * состояниями пользователей и отправляет сообщения.
 */
public class TelegramBot implements LongPollingSingleThreadUpdateConsumer {
    private final TelegramClient telegramClient;
    private final String botToken;
    private final LogicController logicController = new LogicController();
    /**
     * Конструктор класса TelegramBot, который инициализирует нового бота Telegram.
     * @param token Токен бота, необходимый для аутентификации.
     */
    public TelegramBot(String token) {
        botToken = token;
        telegramClient = new OkHttpTelegramClient(botToken);
    }
    /**
     * Обрабатывает обновления от Telegram.
     * @param update Обновление, полученное от Telegram.
     */
    @Override
    public void consume(Update update) {
        long userId;
        if (update.hasCallbackQuery() && update.getCallbackQuery() != null) {
            userId = update.getCallbackQuery().getFrom().getId();
        } else if (update.hasMessage() && update.getMessage() != null) {
            userId = update.getMessage().getChatId();
        } else {
            return; // Неизвестный тип обновления
        }
        sendMessage(userId, logicController.getListStringWithTextToSendAndOptionForKeyboard(update, userId));
    }
    /**
     * Подготавливает и отправляет сообщение в указанный чат.
     * Если указаны опции клавиатуры, они будут добавлены к сообщению.
     * @param chatId ID чата, в который будет отправлено сообщение
     * @param listForWorkWithKeyboard список, содержащий текст сообщения и, возможно, опции клавиатуры
     */
    void sendMessage(long chatId, List<String> listForWorkWithKeyboard) {
        String textToSend = "";
        if (!listForWorkWithKeyboard.isEmpty()) {
            textToSend = listForWorkWithKeyboard.get(0);
        }
        SendMessage message = createMessage(chatId, textToSend);
        KeyboardLogic keyboardLogicObj = new KeyboardLogic();
        if (listForWorkWithKeyboard.size() == 2) {
            keyboardLogicObj.keyboards(message, listForWorkWithKeyboard.get(1));
        } else if (listForWorkWithKeyboard.size() > 1) {
            keyboardLogicObj.keyboardforTestABI(message, listForWorkWithKeyboard);
        }

        executeMessage(message);
    }
    /**
     * Создает объект SendMessage.
     * @param chatId ID чата
     * @param text текст сообщения
     * @return созданный объект SendMessage
     */
    private SendMessage createMessage(long chatId, String text) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .build();
    }
    /**
     * Выполняет отправку сообщения.
     * @param message объект SendMessage, который нужно отправить
     */
    private void executeMessage(SendMessage message) {
        try {
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            System.out.println("Ошибка извлечения данных: " + e.getMessage());
        }
    }
}

