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
    private final TextForCommonMessage botLogic;
    private final EmailSender emailSender;
    private final EmailLogic emailLogic;
    // Хранит логику для теста
    private LogicForTestABI logicForTestABI = new LogicForTestABI();
    private LogicСontroller logicСontroller = new LogicСontroller();
    /**
     * Конструктор класса TelegramBot.
     *
     * @param token Токен бота.
     * @param logic Логика бота для обработки команд.
     */
    public TelegramBot(String token, TextForCommonMessage logic, EmailSender emailSender, EmailLogic emailLogic) {
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
        if(update.hasCallbackQuery() && update.getCallbackQuery() != null){
            logicСontroller.messageHandlerForKeyboard(update , emailSender);
            sendMessage(update.getCallbackQuery().getFrom().getId(),
                    logicСontroller.getListForWorkWithKeyboardAndMessage(update.getCallbackQuery().getFrom().getId()));
        }
        if (update.hasMessage() && update.getMessage() != null){
            logicСontroller.messageHandlerForKeyboard(update , emailSender);
            sendMessage(update.getMessage().getChatId(),
                    logicСontroller.getListForWorkWithKeyboardAndMessage(update.getMessage().getChatId()));
        }

    }
    /**
     * Отправляет сообщение в указанный чат с заданным текстом и данными для клавиатуры.
     *
     * @param chatId Идентификатор чата, в который будет отправлено сообщение.

     */
    void sendMessage(long chatId, List<String> listForWorkWithKeyboard) {
        if(listForWorkWithKeyboard.size() == 1){
                sendMessage(chatId, listForWorkWithKeyboard.get(0));
                return;
        }
        if(listForWorkWithKeyboard.size() == 2){
            SendMessage message = SendMessage // Create a message object
                    .builder()
                    .chatId(chatId)
                    .text(listForWorkWithKeyboard.get(0))
                    .build();

            KeyboardLogic keyboardLogicObj = new KeyboardLogic();
            keyboardLogicObj.keyboards(message, listForWorkWithKeyboard.get(1));
            try {
                telegramClient.execute(message);
            } catch (TelegramApiException e) {
                System.out.println("Ошибка извлечения данных: " + e.getMessage());
            }
        }
        else if(!listForWorkWithKeyboard.isEmpty()) {
            SendMessage message = SendMessage // Create a message object
                    .builder()
                    .chatId(chatId)
                    .text(listForWorkWithKeyboard.get(0))
                    .build();

            KeyboardLogic keyboardLogicObj = new KeyboardLogic();
            keyboardLogicObj.keyboardforTestABI(message, listForWorkWithKeyboard);
            try {
                telegramClient.execute(message);
            } catch (TelegramApiException e) {
                System.out.println("Ошибка извлечения данных: " + e.getMessage());
            }
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

