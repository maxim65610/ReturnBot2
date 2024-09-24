package io.proj3ct.ReturnBot1.config;

import io.proj3ct.ReturnBot1.service.TelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
/**
 * Класс инициализации бота Telegram.
 *
 * Этот класс отвечает за регистрацию бота в системе при событии
 * обновления контекста приложения. Он использует Spring для
 * автоматического внедрения экземпляра бота и TelegramBotsApi
 * для его регистрации.
 */
public class BotInitializer {

    @Autowired
    TelegramBot bot;

    /**
     * Метод, который вызывается при событии обновления контекста.
     *
     * Этот метод создает экземпляр TelegramBotsApi и регистрирует
     * бот в системе. Если возникает ошибка при регистрации,
     * выбрасывается RuntimeException с причиной ошибки.
     *
     * @throws TelegramApiException если произошла ошибка при взаимодействии с Telegram API.
     */
    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
