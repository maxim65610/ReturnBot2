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


        String username = System.getenv("mail"); // Ваша почта
        String password = System.getenv("passwordForMail"); // Ваш пароль (или App Password)
        // emailSender.sendEmail("maximmanveiler@gmail.com", "Тема сообщения", "Текст сообщения");
        EmailSender emailSender = new EmailSender(username, password);

        botLogic.setEmailSender(emailSender);
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
