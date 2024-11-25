package io.proj3ct.ReturnBot1.keybords;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс для хранения и управления данными о факультетах.
 * Данные хранятся в виде карт, где ключом является идентификатор пользователя (Long),
 * а значениями - соответствующая информация о факультете.
 */
public class DataForDepartment {
    /** Map для хранения номеров факультетов, которые нужно редактировать, по идентификатору пользователя */
    private Map<Long, String> numberForEditDepartment = new HashMap<>();
    /** Map для хранения названий факультетов, которые нужно редактировать, по идентификатору пользователя */
    private Map<Long, String> nameForEditDepartment = new HashMap<>();
    /** Map для хранения текстов факультетов, которые нужно редактировать, по идентификатору пользователя */
    private Map<Long, String> textForEditDepartment = new HashMap<>();
    /** Map для хранения институтов для новых факультетов, по идентификатору пользователя */
    private Map<Long, String> instituteForNewDepartment = new HashMap<>();
    /** Map для хранения названий новых факультетов, по идентификатору пользователя */
    private Map<Long, String> nameForNewDepartment = new HashMap<>();
    /** Map для хранения текстов новых факультетов, по идентификатору пользователя */
    private Map<Long, String> textForNewDepartment = new HashMap<>();
    /** Map для хранения номеров факультетов, которые нужно удалить, по идентификатору пользователя */
    private Map<Long, String> numberForDeleteDepartment = new HashMap<>();

    /**
     * Устанавливает номер факультета для редактирования.
     *
     * @param key   идентификатор пользователя
     * @param value номер факультета
     */
    public void setNumberForEditDepartment(Long key, String value) {
        this.numberForEditDepartment.put(key, value);
    }
    /**
     * Устанавливает название факультета для редактирования.
     *
     * @param key   идентификатор пользователя
     * @param value название факультета
     */
    public void setNameForEditDepartment(Long key, String value) {
        this.nameForEditDepartment.put(key, value);
    }
    /**
     * Устанавливает текст факультета для редактирования.
     *
     * @param key   идентификатор пользователя
     * @param value текст факультета
     */
    public void setTextForEditDepartment(Long key, String value) {
        this.textForEditDepartment.put(key, value);
    }
    /**
     * Получает номер факультета для редактирования.
     *
     * @param userId идентификатор пользователя
     * @return номер факультета, или null, если не найдено
     */
    public String getNumberForEditDepartment(Long userId) {
        return numberForEditDepartment.get(userId);
    }
    /**
     * Получает название факультета для редактирования.
     *
     * @param userId идентификатор пользователя
     * @return название факультета, или null, если не найдено
     */
    public String getNameForEditDepartment(Long userId) {
        return nameForEditDepartment.get(userId);
    }
    /**
     * Получает текст факультета для редактирования.
     *
     * @param userId идентификатор пользователя
     * @return текст факультета, или null, если не найдено
     */
    public String getTextForEditDepartment(Long userId) {
        return textForEditDepartment.get(userId);
    }
    /**
     * Устанавливает институт для нового факультета.
     *
     * @param key   идентификатор пользователя
     * @param value название института
     */
    public void setInstituteForNewDepartment(Long key, String value) {
        this.instituteForNewDepartment.put(key, value);
    }
    /**
     * Устанавливает название нового факультета.
     *
     * @param key   идентификатор пользователя
     * @param value название нового факультета
     */
    public void setNameForNewDepartment(Long key, String value) {
        this.nameForNewDepartment.put(key, value);
    }
    /**
     * Устанавливает текст для нового факультета.
     *
     * @param key   идентификатор пользователя
     * @param value текст нового факультета
     */
    public void setTextForNewDepartment(Long key, String value) {
        this.textForNewDepartment.put(key, value);
    }
    /**
     * Получает институт для нового факультета.
     *
     * @param userId идентификатор пользователя
     * @return название института, или null, если не найдено
     */
    public String getInstituteForNewDepartment(Long userId) {
        return instituteForNewDepartment.get(userId);
    }
    /**
     * Получает название нового факультета.
     *
     * @param userId идентификатор пользователя
     * @return название нового факультета, или null, если не найдено
     */
    public String getNameForNewDepartment(Long userId) {
        return nameForNewDepartment.get(userId);
    }
    /**
     * Получает текст для нового факультета.
     *
     * @param userId идентификатор пользователя
     * @return текст нового факультета, или null, если не найдено
     */
    public String getTextForNewDepartment(Long userId) {
        return textForNewDepartment.get(userId);
    }
    /**
     * Устанавливает номер факультета для удаления.
     *
     * @param key   идентификатор пользователя
     * @param value номер факультета
     */
    public void setNumberForDeleteDepartment(Long key, String value) {
        this.numberForDeleteDepartment.put(key, value);
    }
    /**
     * Получает номер факультета для удаления.
     *
     * @param userId идентификатор пользователя
     * @return номер факультета, или null, если не найдено
     */
    public String getNumberForDeleteDepartment(Long userId) {
        return numberForDeleteDepartment.get(userId);
    }
}
