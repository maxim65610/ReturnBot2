package io.proj3ct.ReturnBot1;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args) {
        /**
         * Инициализация токена и имени
         */
        String BOT_TOKEN = System.getenv("tgToken");
        String BOT_NAME = System.getenv("tgName");

        /**
         * Создаем два объекта классов LogicBrain и TelegramBot
         */
        LogicBrain botLogic = new LogicBrain();
        TelegramBot bot = new TelegramBot(BOT_NAME, BOT_TOKEN,botLogic);

        /**
         * Запуск бота
         */
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
