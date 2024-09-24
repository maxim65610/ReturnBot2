package io.proj3ct.ReturnBot1.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.beans.factory.annotation.Value;

@Configuration
@PropertySource("application.properties")
/**
 * Класс конфигурации бота, который хранит имя и токен бота.
 * Значения этих параметров считываются из внешнего источника конфигурации,
 */
public class BotConfig {

    @Value("${bot.name}")
    String botName;

    @Value("${bot.token}")
    String token;

    /**
     * Возвращает имя бота.
     *
     * @return Имя бота, заданное в конфигурации.
     */
    public String getBotName() {
        return botName;
    }

    /**
     * Возвращает токен бота.
     *
     * @return Токен бота, заданный в конфигурации.
     */
    public String getToken() {
        return token;
    }
}

