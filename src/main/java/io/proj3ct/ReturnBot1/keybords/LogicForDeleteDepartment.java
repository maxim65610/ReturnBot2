package io.proj3ct.ReturnBot1.keybords;

import io.proj3ct.ReturnBot1.baseClasses.MessageConstants;
import io.proj3ct.ReturnBot1.datebase.DatabaseConnection;

import java.util.HashMap;
import java.util.Map;

public class LogicForDeleteDepartment {
    /** Объект для хранения данных о факультетах. */
    private DataForDepartment dataForDepartment = new DataForDepartment();
    /** Map для хранения состояний пользователей*/
    private final Map<Long, String> userStatesForDeleteDepartment = new HashMap<>();
    /** Объект для получения информации о факультетах. */
    private DepartmentsInfo departmentsInfo = new DepartmentsInfo();
    /** Объект для работы с клавиатурами. */
    private KeyboardsData keyboardsData = new KeyboardsData();
    /** Объект для подключения к базе данных. */
    private DatabaseConnection databaseConnection = new DatabaseConnection();

    public LogicForDeleteDepartment(DatabaseConnection databaseConnection,DataForDepartment dataForDepartment,
                                    DepartmentsInfo departmentsInfo,KeyboardsData keyboardsData) {
        this.databaseConnection = databaseConnection;
        this.dataForDepartment = dataForDepartment;
        this.departmentsInfo = departmentsInfo;
        this.keyboardsData = keyboardsData;

    }

    public LogicForDeleteDepartment() {
    }

    /**
     * Получает текущее состояние пользователя для удаления факультета.
     *
     * @param chatID идентификатор чата пользователя
     * @return текущее состояние пользователя или "0", если состояние не найдено
     */
    public String getUserStatesForDeleteDepartment(Long chatID) {
        return userStatesForDeleteDepartment.getOrDefault(chatID, "0");
    }
    /**
     * Проверяет введённый пароль и обновляет состояние пользователя.
     *
     * @param messageText текст сообщения от пользователя
     * @param userId идентификатор пользователя
     * @return сообщение для пользователя о результате проверки пароля
     */
    private String checkValidPasswordInput(String messageText, Long userId) {
        if (messageText.equals(System.getenv("password"))) {
            userStatesForDeleteDepartment.put(userId, "awaiting_numberForDelete");
            return getAllDepartmentsFromBd();
        } else {
            userStatesForDeleteDepartment.remove(userId);
            return MessageConstants.BAD_PASSWORD_COMMAND_RESPONSE;
        }
    }
    /**
     * Получает все факультеты из базы данных и формирует сообщение для пользователя.
     *
     * @return строка с сообщением, содержащим все факультеты
     */
    public String getAllDepartmentsFromBd(){
        String messageTextForUser = "";
        String[] allNamesDepartmentsWithId = departmentsInfo.getAllNamesWithId();
        messageTextForUser += MessageConstants.CORRECT_PASSWORD_AND_INSTITUTE_DELETE_COMMAND_RESPONSE + "\n";
        for(String namesDepartmentsWithId : allNamesDepartmentsWithId){
            messageTextForUser += namesDepartmentsWithId + "\n";
        }
        return messageTextForUser;
    }
    /**
     * Обрабатывает команды пользователя для удаления факультета.
     *
     * @param messageText текст сообщения от пользователя
     * @param userId идентификатор пользователя
     * @return ответ пользователю в зависимости от состояния и команды
     */
    public String worksWithDeleteDepartment(String messageText, Long userId) {
        String currentState = userStatesForDeleteDepartment.get(userId);
        if ("/delete_department_data".equals(messageText)) {
            userStatesForDeleteDepartment.put(userId, "awaiting_password");
            return MessageConstants.PASSWORD_COMMAND_RESPONSE;
        } else if ("awaiting_password".equals(currentState)) {
            return checkValidPasswordInput(messageText, userId);
        }
        else if("awaiting_numberForDelete".equals(currentState)){
            dataForDepartment.setNumberForDeleteDepartment(userId, messageText);
            userStatesForDeleteDepartment.remove(userId);
            keyboardsData.deleteData(userId,databaseConnection,dataForDepartment);
            return MessageConstants.SUCCESSFUL_DELETE_DEPARTMENT_COMMAND_RESPONSE;
        }
        return MessageConstants.DEFAULT_RESPONSE;
    }
}
