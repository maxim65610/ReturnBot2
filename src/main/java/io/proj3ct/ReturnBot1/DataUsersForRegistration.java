package io.proj3ct.ReturnBot1;

import java.util.HashMap;
import java.util.Map;

public class DataUsersForRegistration {
    private final Map<Long, String> nameUser  = new HashMap<>();
    private final Map<Long, String> surnameUser  = new HashMap<>();
    private final Map<Long, String> schoolClassUser  = new HashMap<>();
    private final Map<Long, String> mailUser  = new HashMap<>();
    public String getNameUser(Long userId) {
        return nameUser.get(userId);
    }
    public String getSurnameUser(Long userId) {
        return surnameUser.get(userId);
    }
    public String  getSchoolClassUser(Long userId) {
        return schoolClassUser.get(userId);
    }
    public String  getMailUser(Long userId) {
        return mailUser.get(userId);
    }
    // Для добавления/обновления конкретных значений
    public void setNameUser(Long key, String value) {
        this.nameUser.put(key, value);
    }
    public void setSurnameUser(Long key, String value) {
        this.surnameUser.put(key, value);
    }
    public void setSchoolClassUser(Long key, String value) {
        this.schoolClassUser.put(key, value);
    }
    public void setMailUser(Long key, String value) {
        this.mailUser.put(key, value);
    }
}
