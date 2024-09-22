package io.proj3ct.ReturnBot1.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.beans.factory.annotation.Value;

@Configuration
@PropertySource("application.properties")
public class BotConfig {

    @Value("${bot.name}")
    String botName;

    @Value("${bot.token}")
    String token;
    public String getBotName() {
        return botName;
    }

    // Геттер для token
    public String getToken() {
        return token;
    }
}
