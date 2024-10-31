package io.proj3ct.ReturnBot1;


import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;

import java.util.HashMap;
import java.util.Map;


/**
 * Главный класс приложения для инициализации и запуска Telegram-бота.
 * В этом классе происходит установка необходимых параметров, создание
 * экземпляров логики бота и самого бота, а также запуск бота.
 */
public class Main {

    public static void main(String[] args) {
        // Инициализация токена для базы данных из переменных окружения.
        String BOT_TOKEN = System.getenv("tgToken");
        /*
         * Запуск бота.
         * Инициализируется TelegramBotsApi и регистрируется созданный бот.
         */
        try  (TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication()) {
            botsApplication.registerBot(BOT_TOKEN, new TelegramBot(BOT_TOKEN));
            Thread.currentThread().join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
