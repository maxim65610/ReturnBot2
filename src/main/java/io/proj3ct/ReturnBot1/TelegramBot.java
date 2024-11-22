package io.proj3ct.ReturnBot1;

import io.proj3ct.ReturnBot1.baseClasses.KeyboardLogic;
import io.proj3ct.ReturnBot1.baseClasses.LogicController;
import io.proj3ct.ReturnBot1.dispatch.LogicAndDataForDispatch;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * Telegram-бота, который наследует функциональность
 * от LongPollingSingleThreadUpdateConsumer. Обрабатывает обновления от пользователей, управляет
 * состояниями пользователей и отправляет сообщения.
 */
public class TelegramBot implements LongPollingSingleThreadUpdateConsumer {
    private final TelegramClient telegramClient;
    private final String botToken;
    private final LogicController logicController = new LogicController();
    private final List<BotCommand> listCommands;
    private final LogicAndDataForDispatch logicAndDataForDispatch= new LogicAndDataForDispatch();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    /**
     * Конструктор класса TelegramBot.
     *
     * @param token Токен бота для подключения к Telegram API.
     */
    public TelegramBot(String token) {
        botToken = token;
        telegramClient = new OkHttpTelegramClient(botToken);
        listCommands = new ArrayList<>();
        listCommands.add(new BotCommand("/work","Посмотреть описания факультетов"));
        listCommands.add(new BotCommand("/authorization", "Пройти регистрацию"));
        listCommands.add(new BotCommand("/test_abit", "Пройти тест на определение факультета"));
        listCommands.add(new BotCommand("/question", "Задать вопрос"));
        listCommands.add(new BotCommand("/user_data_change", "Изменить данные, веденные при регистрации"));
        listCommands.add(new BotCommand("/user_data_dell", "Удалить данные, веденные при регистрации"));
        listCommands.add(new BotCommand("/user_info", "Узнать информацию, которую вы ввели при регистрации"));
        listCommands.add(new BotCommand("/dispatch_on", "Подписаться на рассылку"));
        listCommands.add(new BotCommand("/dispatch_off", "Отписаться от рассылки"));


        timerWithPeriodicityOfDay();
    }
    /**
     * Метод для обработки обновлений от пользователей.
     *
     * @param update Объект Update, содержащий информацию о полученном обновлении.
     */
    @Override
    public void consume(Update update) {
        long userId;
        boolean flagForKeyboard = false;
        String messageText;
        if (update.hasCallbackQuery() && update.getCallbackQuery() != null) {
            userId = update.getCallbackQuery().getFrom().getId();
            flagForKeyboard = true;
            messageText = update.getCallbackQuery().getData();
        } else if (update.hasMessage() && update.getMessage() != null) {
            userId = update.getMessage().getChatId();
            messageText = update.getMessage().getText();
        } else {
            return; // Неизвестный тип обновления
        }
        sendMessage(userId, logicController.handleMessage(userId, messageText, flagForKeyboard));
    }
    /**
     * Метод для запуска таймера, который периодически запускает метод sendMessageForDispatch.
     */
    private void timerWithPeriodicityOfDay() {
        scheduler.scheduleAtFixedRate(this::sendMessageForDispatch, 0, 1, TimeUnit.DAYS);
    }
    /**
     * Метод для отправки сообщений пользователям на основе данных для рассылки.
     */
    private void sendMessageForDispatch(){
        String[][] userIdAndTextToSendDataArray = logicAndDataForDispatch.checkDateForDispatch();
        for(int i = 0; i < userIdAndTextToSendDataArray.length; i++){
            Long userId = Long.parseLong(userIdAndTextToSendDataArray[i][0]);
            String textToSend = userIdAndTextToSendDataArray[i][1];
            SendMessage message = createMessage(userId, textToSend);
            executeMessage(message);
        }
    }
    /**
     * Метод для отправки сообщения пользователю с возможностью добавления клавиатуры.
     *
     * @param chatId             Идентификатор чата пользователя.
     * @param listForWorkWithKeyboard Список строк для обработки с клавиатурой.
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
     * Метод для создания сообщения SendMessage для отправки пользователю.
     *
     * @param chatId Идентификатор чата пользователя.
     * @param text   Текст сообщения.
     * @return Созданное сообщение SendMessage.
     */
    private SendMessage createMessage(long chatId, String text) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .build();
    }
    /**
     * Метод для выполнения отправки сообщения через Telegram API.
     *
     * @param message Сообщение, которое нужно отправить.
     */
    private void executeMessage(SendMessage message) {
        try {
            telegramClient.execute((new SetMyCommands(listCommands, new BotCommandScopeDefault(),null)));
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            System.out.println("Ошибка отправки сообщения: " + e.getMessage());
        }
    }
}