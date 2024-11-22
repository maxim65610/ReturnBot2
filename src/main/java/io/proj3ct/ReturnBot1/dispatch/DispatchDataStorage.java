package io.proj3ct.ReturnBot1.dispatch;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс для хранения данных диспетча.
 * Хранит текст, время, категорию и отдел диспетча, связанные с уникальными идентификаторами.
 */
public class DispatchDataStorage {
    // Хранит текст диспетча, связанный с уникальными идентификаторами чатов
    private final Map<Long, String> dispatchText = new HashMap<>();
    // Хранит время диспетча, связанное с уникальными идентификаторами чатов
    private final Map<Long, String> dispatchTime = new HashMap<>();
    // Хранит категорию диспетча, связанную с уникальными идентификаторами чатов
    private final Map<Long, String> dispatchCategory = new HashMap<>();
    // Хранит отдел диспетча, связанный с уникальными идентификаторами чатов
    private final Map<Long, String> dispatchDepartment = new HashMap<>();

    /**
     * Получает текст диспетча для указанного идентификатора чата.
     *
     * @param chatID идентификатор чата
     * @return текст диспетча или null, если не найдено
     */
    public String getDispatchText(Long chatID) {
        return dispatchText.get(chatID);
    }

    /**
     * Получает время диспетча для указанного идентификатора чата.
     *
     * @param chatID идентификатор чата
     * @return время диспетча или null, если не найдено
     */
    public String getDispatchTime(Long chatID) {
        return dispatchTime.get(chatID);
    }

    /**
     * Получает категорию диспетча для указанного идентификатора чата.
     *
     * @param chatID идентификатор чата
     * @return категория диспетча или null, если не найдено
     */
    public String getDispatchCategory(Long chatID) {
        return dispatchCategory.get(chatID);
    }

    /**
     * Получает отдел диспетча для указанного идентификатора чата.
     *
     * @param chatID идентификатор чата
     * @return отдел диспетча или null, если не найдено
     */
    public String getDispatchDepartment(Long chatID) {
        return dispatchDepartment.get(chatID);
    }

    /**
     * Устанавливает текст диспетча для указанного идентификатора чата.
     *
     * @param chatID идентификатор чата
     * @param text текст диспетча
     */
    public void setDispatchText(Long chatID, String text) {
        this.dispatchText.put(chatID, text);
    }

    /**
     * Устанавливает время диспетча для указанного идентификатора чата.
     *
     * @param chatID идентификатор чата
     * @param time время диспетча
     */
    public void setDispatchTime(Long chatID, String time) {
        this.dispatchTime.put(chatID, time);
    }

    /**
     * Устанавливает категорию диспетча для указанного идентификатора чата.
     *
     * @param chatID идентификатор чата
     * @param category категория диспетча
     */
    public void setDispatchCategory(Long chatID, String category) {
        this.dispatchCategory.put(chatID, category);
    }

    /**
     * Устанавливает отдел диспетча для указанного идентификатора чата.
     *
     * @param chatID идентификатор чата
     * @param department отдел диспетча
     */
    public void setDispatchDepartment(Long chatID, String department) {
        this.dispatchDepartment.put(chatID, department);
    }
}
