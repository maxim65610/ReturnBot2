package io.proj3ct.ReturnBot1;

import java.util.HashMap;
import java.util.Map;

public class DataForDepartment {
    private Map<Long, String> numberForEditDepartment = new HashMap<>();
    private Map<Long, String> nameForEditDepartment = new HashMap<>();
    private Map<Long, String> textForEditDepartment = new HashMap<>();
    private Map<Long, String> instituteForNewDepartment = new HashMap<>();
    private Map<Long, String> nameForNewDepartment = new HashMap<>();
    private Map<Long, String> textForNewDepartment = new HashMap<>();
    private Map<Long, String> numberForDeleteDepartment = new HashMap<>();
    public void setNumberForEditDepartment (Long key, String value) {
        this.numberForEditDepartment.put(key, value);
    }
    public void setNameForEditDepartment(Long key, String value) {
        this.nameForEditDepartment.put(key, value);
    }
    public void setTextForEditDepartment(Long key, String value) {
        this.textForEditDepartment.put(key, value);
    }
    public String getNumberForEditDepartment (Long userId) {
        return numberForEditDepartment.get(userId);
    }
    public String getNameForEditDepartment (Long userId) {
        return nameForEditDepartment.get(userId);
    }
    public String getTextForEditDepartment (Long userId) {
        return textForEditDepartment.get(userId);
    }
    public void setInstituteForNewDepartment(Long key, String value) {
        this.instituteForNewDepartment.put(key, value);
    }
    public void setNameForNewDepartment(Long key, String value) {
        this.nameForNewDepartment.put(key, value);
    }
    public void setTextForNewDepartment(Long key, String value) {
        this.textForNewDepartment.put(key, value);
    }
    public String getInstituteForNewDepartment(Long userId) {
        return instituteForNewDepartment.get(userId);
    }
    public String getNameForNewDepartment(Long userId) {
        return nameForNewDepartment.get(userId);
    }
    public String getTextForNewDepartment(Long userId) {
        return textForNewDepartment.get(userId);
    }
    public void setNumberForDeleteDepartment(Long key, String value) {
        this.numberForDeleteDepartment.put(key, value);
    }
    public String getNumberForDeleteDepartment(Long userId) {
        return numberForDeleteDepartment.get(userId);
    }

}
