package io.proj3ct.ReturnBot1;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * Главный класс приложения для инициализации и запуска Telegram-бота.
 * В этом классе происходит установка необходимых параметров, создание
 * экземпляров логики бота и самого бота, а также запуск бота.
 */
public class Main {
    public static void main(String[] args) {
        /*
         * Инициализация токена, имени бота и данных для базы данных.
         * Данные извлекаются из переменных окружения.
         */
        String BOT_TOKEN = System.getenv("tgToken");
        String BOT_NAME = System.getenv("tgName");

        // Извлечение электронной почты и пароля из переменных окружения
        String username = System.getenv("mail"); // Ваша почта
        String password = System.getenv("passwordForMail"); // Ваш пароль (или App Password)

        // Создание объекта EmailSender для отправки электронных писем
        EmailSender emailSender = new EmailSender(username, password);

        /*
         * Создание объектов классов MessageLogic, EmailLogic TelegramBot.
         * MessageLogic отвечает за логику работы сообщений бота,
         * EmailLogic отвечает за логику работы с почтой;
         * а TelegramBot - за взаимодействие с Telegram API.
         */
        MessageLogic botLogic = new MessageLogic();
        EmailLogic emailLogic = new EmailLogic();
        TelegramBot bot = new TelegramBot(BOT_NAME, BOT_TOKEN, botLogic, emailSender, emailLogic);


        /*
         * Запуск бота.
         * Инициализируется TelegramBotsApi и регистрируется созданный бот.
         */
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
