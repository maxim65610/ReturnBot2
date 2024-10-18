package io.proj3ct.ReturnBot1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
/**
 * Класс для тестирования функциональности класса DepartInfoBD.
 * Содержит методы для проверки корректности возвращаемых данных.
 */
public class DepartInfoBDTest {

    @Test

    /**
     * Тест для метода по подтягиванию данных из дб
     */
    void testDataReturn() {

        DepartInfoBD infoObj = new DepartInfoBD();

        String textToSend = infoObj.takeInfo("1442", "sdfsdfsdfsdf");
        Assertions.assertEquals("sdfsdfsdfsdf", textToSend);
    }

}
