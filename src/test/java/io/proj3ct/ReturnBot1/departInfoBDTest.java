package io.proj3ct.ReturnBot1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
/**
 * Класс для тестирования функциональности класса {@link DepartInfoBD}.
 * Содержит методы для проверки корректности возвращаемых данных.
 */
public class departInfoBDTest {

    @Test

    /**
     * Тест для случая, когда пользователь ввел /start
     */
    void testDataReturn() {
        DepartInfoBD infoObj = new DepartInfoBD();
        String textToSend = infoObj.takeInfo("sdfsdfsdfsdf", "sdfsdfsdfsdf");
        Assertions.assertEquals("sdfsdfsdfsdf", textToSend);
    }

}
