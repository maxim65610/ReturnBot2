package io.proj3ct.ReturnBot1.dispatch;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс для хранения данных рассылки.
 * Хранит текст, время, категорию и отдел диспетча, связанные с уникальными идентификаторами.
 */
public class DispatchDataStorage {
    // Хранит текст рассылки, связанный с уникальными идентификаторами чатов
    private final Map<Long, String> dispatchText = new HashMap<>();
    // Хранит время рассылки, связанное с уникальными идентификаторами чатов
    private final Map<Long, String> dispatchTime = new HashMap<>();
    // Хранит категорию рассылки, связанную с уникальными идентификаторами чатов
    private final Map<Long, String> dispatchCategory = new HashMap<>();
    // Хранит отдел рассылки, связанный с уникальными идентификаторами чатов
    private final Map<Long, String> dispatchDepartment = new HashMap<>();

    /**
     * Получает текст рассылки для указанного идентификатора чата.
     *
     * @param chatID идентификатор чата
     * @return текст рассылки или null, если не найдено
     */
    public String getDispatchText(Long chatID) {
        return dispatchText.get(chatID);
    }

    /**
     * Получает время рассылки для указанного идентификатора чата.
     *
     * @param chatID идентификатор чата
     * @return время рассылки или null, если не найдено
     */
    public String getDispatchTime(Long chatID) {
        return dispatchTime.get(chatID);
    }

    /**
     * Получает категорию рассылки для указанного идентификатора чата.
     *
     * @param chatID идентификатор чата
     * @return категория рассылки или null, если не найдено
     */
    public String getDispatchCategory(Long chatID) {
        return dispatchCategory.get(chatID);
    }

    /**
     * Получает отдел рассылки для указанного идентификатора чата.
     *
     * @param chatID идентификатор чата
     * @return отдел рассылки или null, если не найдено
     */
    public String getDispatchDepartment(Long chatID) {
        return dispatchDepartment.get(chatID);
    }

    /**
     * Устанавливает текст рассылки для указанного идентификатора чата.
     *
     * @param chatID идентификатор чата
     * @param text текст рассылки
     */
    public void setDispatchText(Long chatID, String text) {
        this.dispatchText.put(chatID, text);
    }

    /**
     * Устанавливает время рассылки для указанного идентификатора чата.
     *
     * @param chatID идентификатор чата
     * @param time время рассылки
     */
    public void setDispatchTime(Long chatID, String time) {
        this.dispatchTime.put(chatID, time);
    }

    /**
     * Устанавливает категорию рассылки для указанного идентификатора чата.
     *
     * @param chatID идентификатор чата
     * @param category категория рассылки
     */
    public void setDispatchCategory(Long chatID, String category) {
        this.dispatchCategory.put(chatID, category);
    }

    /**
     * Устанавливает отдел рассылки для указанного идентификатора чата.
     *
     * @param chatID идентификатор чата
     * @param department отдел рассылки
     */
    public void setDispatchDepartment(Long chatID, String department) {
        this.dispatchDepartment.put(chatID, department);
    }
}
