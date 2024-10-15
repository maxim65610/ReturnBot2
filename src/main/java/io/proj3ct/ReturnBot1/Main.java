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
        /**
         * Инициализация токена, имени бота и данных для базы данных.
         * Данные извлекаются из переменных окружения.
         */
        String BOT_TOKEN = System.getenv("tgToken");
        String BOT_NAME = System.getenv("tgName");
        /**
         * Создание объектов классов LogicBrain и TelegramBot.
         * LogicBrain отвечает за логику работы бота, а TelegramBot
         * - за взаимодействие с Telegram API.
         */
        MessageLogic botLogic = new MessageLogic();
        TelegramBot bot = new TelegramBot(BOT_NAME, BOT_TOKEN, botLogic);

        /**
         * Создание объектов классов databaseConnection.
         * databaseConnection отвечает за подключение к базе данных
         */

        DatabaseConnection databaseConnection = new DatabaseConnection();
        databaseConnection.createAllTable();

        RetrieveData retrieveData = new RetrieveData();
        System.out.println(retrieveData.getDataById(203, "question") );

        DepartInfoBD DepartInfoBD = new DepartInfoBD();
        String info = DepartInfoBD.takeInfo( "14");

        System.out.println(info);
        // Извлечение электронной почты и пароля из переменных окружения
        String username = System.getenv("mail"); // Ваша почта
        String password = System.getenv("passwordForMail"); // Ваш пароль (или App Password)

        // Создание объекта EmailSender для отправки электронных писем
        EmailSender emailSender = new EmailSender(username, password);

        // Установка отправителя электронной почты в логике бота
        botLogic.setEmailSender(emailSender);

        /**
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

