package io.proj3ct.ReturnBot1.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import io.proj3ct.ReturnBot1.config.BotConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class TelegramBotTest {

        private TelegramBot bot;
        private BotConfig config;

    @BeforeEach
    public void setUp(){
        config = mock(BotConfig.class);
        when(config.getBotName()).thenReturn("TestBot");
        when(config.getToken()).thenReturn("123456:ABC-DEF1234ghIkl-zyx57W2v1u123ew11");

        bot = new TelegramBot(config);
    }

    @Test
    public void testgetBotUsername(){
        assertEquals(bot.getBotUsername(), "TestBot");
    }

    @Test
    public void testgetBotToken(){
        assertEquals(bot.getBotToken(), "123456:ABC-DEF1234ghIkl-zyx57W2v1u123ew11");
    }
}

