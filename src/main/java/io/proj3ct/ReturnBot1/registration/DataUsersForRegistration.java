package io.proj3ct.ReturnBot1.registration;

import java.util.HashMap;
import java.util.Map;

public class DataUsersForRegistration {
    // Хранит имена пользователей, связанные с их уникальными идентификаторами
    private final Map<Long, String> nameUser = new HashMap<>();
    // Хранит фамилии пользователей, связанные с их уникальными идентификаторами
    private final Map<Long, String> surnameUser = new HashMap<>();
    // Хранит классы пользователей, связанные с их уникальными идентификаторами
    private final Map<Long, String> schoolClassUser = new HashMap<>();
    // Хранит адреса электронной почты пользователей, связанные с их уникальными идентификаторами
    private final Map<Long, String> mailUser = new HashMap<>();
    /**
     * Получает имя пользователя по его уникальному идентификатору.
     *
     * @param userId уникальный идентификатор пользователя
     * @return имя пользователя или null, если пользователь не найден
     */
    public String getNameUser (Long userId) {
        return nameUser.get(userId);
    }
    /**
     * Получает фамилию пользователя по его уникальному идентификатору.
     *
     * @param userId уникальный идентификатор пользователя
     * @return фамилия пользователя или null, если пользователь не найден
     */
    public String getSurnameUser (Long userId) {
        return surnameUser.get(userId);
    }
    /**
     * Получает класс пользователя по его уникальному идентификатору.
     *
     * @param userId уникальный идентификатор пользователя
     * @return класс пользователя или null, если пользователь не найден
     */
    public String getSchoolClassUser (Long userId) {
        return schoolClassUser.get(userId);
    }
    /**
     * Получает адрес электронной почты пользователя по его уникальному идентификатору.
     *
     * @param userId уникальный идентификатор пользователя
     * @return адрес электронной почты пользователя или null, если пользователь не найден
     */
    public String getMailUser (Long userId) {
        return mailUser.get(userId);
    }
    /**
     * Добавляет или обновляет имя пользователя по его уникальному идентификатору.
     *
     * @param key   уникальный идентификатор пользователя
     * @param value новое имя пользователя
     */
    public void setNameUser (Long key, String value) {
        this.nameUser.put(key, value);
    }
    /**
     * Добавляет или обновляет фамилию пользователя по его уникальному идентификатору.
     *
     * @param key   уникальный идентификатор пользователя
     * @param value новая фамилия пользователя
     */
    public void setSurnameUser (Long key, String value) {
        this.surnameUser.put(key, value);
    }
    /**
     * Добавляет или обновляет класс пользователя по его уникальному идентификатору.
     *
     * @param key   уникальный идентификатор пользователя
     * @param value новый класс пользователя
     */
    public void setSchoolClassUser (Long key, String value) {
        this.schoolClassUser.put(key, value);
    }
    /**
     * Добавляет или обновляет адрес электронной почты пользователя по его уникальному идентификатору.
     *
     * @param key   уникальный идентификатор пользователя
     * @param value новый адрес электронной почты пользователя
     */
    public void setMailUser (Long key, String value) {
        this.mailUser.put(key, value);
    }
}
