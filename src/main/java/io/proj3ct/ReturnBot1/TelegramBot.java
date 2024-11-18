package io.proj3ct.ReturnBot1;

import io.proj3ct.ReturnBot1.baseClasses.KeyboardLogic;
import io.proj3ct.ReturnBot1.baseClasses.LogicController;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
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
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    public TelegramBot(String token) {
        botToken = token;
        telegramClient = new OkHttpTelegramClient(botToken);
        listCommands = new ArrayList<>();
        listCommands.add(new BotCommand("/work","Посмотреть описания факультетов"));
        listCommands.add(new BotCommand("/authorization", "Пройти регистрацию"));
        listCommands.add(new BotCommand("/testabit", "Пройти тест на определение факультета"));
        listCommands.add(new BotCommand("/question", "Задать вопрос"));
        listCommands.add(new BotCommand("/userdatachange", "Изменить данные, веденные при регистрации"));
        listCommands.add(new BotCommand("/userdatadell", "Удалить данные, веденные при регистрации"));
        listCommands.add(new BotCommand("/userinfo", "Узнать информацию, которую вы ввели при регистрации"));

        //startScheduledTask();
    }

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

    //public void startScheduledTask() {
        //scheduler.scheduleAtFixedRate(this::checkAndSendScheduledMessages, 0, 10, TimeUnit.SECONDS);
    //}
    //public void checkAndSendScheduledMessages(){
        //System.out.println("11");
    //}
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

    private SendMessage createMessage(long chatId, String text) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .build();
    }

    private void executeMessage(SendMessage message) {
        try {
            telegramClient.execute((new SetMyCommands(listCommands, new BotCommandScopeDefault(),null)));
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            System.out.println("Ошибка отправки сообщения: " + e.getMessage());
        }
    }
}


