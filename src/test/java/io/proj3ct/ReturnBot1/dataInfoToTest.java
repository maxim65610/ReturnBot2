package io.proj3ct.ReturnBot1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
/**
 * Класс для тестирования функциональности класса {@link dataInfoTo}.
 * Содержит методы для проверки корректности возвращаемых данных.
 */
public class dataInfoToTest {

    @Test

    /**
     * Тест для случая, когда пользователь ввел /start
     */
    void testDataReturn() {
        dataInfoTo infoObj = new dataInfoTo();
        String textToSend = infoObj.takeInfo("sdfsdfsdfsdf");
        Assertions.assertEquals("sdfsdfsdfsdf", textToSend);
    }

}
