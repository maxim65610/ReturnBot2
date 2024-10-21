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

        String data = "testData";
        String textToSend = "dataToSend";

        textToSend = infoObj.takeInfo(data, textToSend);
        Assertions.assertEquals("dataToSend", textToSend);
    }

}
