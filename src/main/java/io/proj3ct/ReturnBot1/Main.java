package io.proj3ct.ReturnBot1;


import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;


/**
 * Главный класс приложения для инициализации и запуска Telegram-бота.
 * В этом классе происходит установка необходимых параметров, создание
 * экземпляров логики бота и самого бота, а также запуск бота.
 */
public class Main {

    public static void main(String[] args) {

        // Инициализация токена для базы данных из переменных окружения.
        String BOT_TOKEN = System.getenv("tgToken");

        // Извлечение электронной почты и пароля из переменных окружения
        String username = System.getenv("mail"); // Ваша почта
        String password = System.getenv("passwordForMail"); // Ваш пароль (или App Password)

        // Создание объекта EmailSender для отправки электронных писем
        EmailSender emailSender = new EmailSender(username, password);

        /*
         * Создание объектов классов MessageLogic, EmailLogic TelegramBot.
         * MessageLogic отвечает за логику работы сообщений бота,
         * а TelegramBot - за взаимодействие с Telegram API.
         */
        TextForCommonMessage botLogic = new TextForCommonMessage();
        /*
         * Запуск бота.
         * Инициализируется TelegramBotsApi и регистрируется созданный бот.
         */
        try  (TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication()) {
            botsApplication.registerBot(BOT_TOKEN, new TelegramBot(BOT_TOKEN, botLogic, emailSender));
            // Ensure this prcess wait forever
            Thread.currentThread().join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
