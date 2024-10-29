package io.proj3ct.ReturnBot1;


import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


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
        CommonMessageLogic botLogic = new CommonMessageLogic();
        EmailLogic emailLogic = new EmailLogic();

        /*
         * Запуск бота.
         * Инициализируется TelegramBotsApi и регистрируется созданный бот.
         */

        try  (TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication()) {
            botsApplication.registerBot(BOT_TOKEN, new TelegramBot(BOT_TOKEN, botLogic, emailSender, emailLogic));
            // Ensure this prcess wait forever
            Thread.currentThread().join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
