package io.proj3ct.ReturnBot1.keybords;

import io.proj3ct.ReturnBot1.baseClasses.EnvironmentService;
import io.proj3ct.ReturnBot1.baseClasses.MessageConstants;
import io.proj3ct.ReturnBot1.datebase.DatabaseConnection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Логика для редактирования данных факультетов.
 * Этот класс обрабатывает состояние пользователей и управляет процессом редактирования факультетов.
 */
public class LogicForEditDepartmentData {
    /** Объект для хранения данных о факультетах. */
    private DataForDepartment dataForDepartment = new DataForDepartment();
    /** Map для хранения состояний пользователей*/
    private final Map<Long, String> userStatesForEditDepartment = new HashMap<>();
    /** Объект для получения информации о факультетах. */
    private DepartmentsInfo departmentsInfo = new DepartmentsInfo();
    /** Объект для работы с клавиатурами. */
    private KeyboardsData keyboardsData = new KeyboardsData();
    /** Объект для подключения к базе данных. */
    private DatabaseConnection databaseConnection = new DatabaseConnection();
    /** Объект для получения данных из переменных окружения. */
    private EnvironmentService environmentService = new EnvironmentService();
    private  Map<Long, String> messageTextForUser = new HashMap<>();
    public LogicForEditDepartmentData(DatabaseConnection databaseConnection,DataForDepartment dataForDepartment,
                                      DepartmentsInfo departmentsInfo,
                                      KeyboardsData keyboardsData, EnvironmentService environmentService) {
        this.databaseConnection = databaseConnection;
        this.dataForDepartment = dataForDepartment;
        this.departmentsInfo = departmentsInfo;
        this.keyboardsData = keyboardsData;
        this.environmentService = environmentService;
    }
    public LogicForEditDepartmentData(){

    }
    /**
     * Получает текущее состояние пользователя для редактирования факультета.
     *
     * @param chatID идентификатор чата пользователя
     * @return текущее состояние пользователя или "0", если состояние не найдено
     */
    public String getUserStatesForEditDepartment(Long chatID) {
        return userStatesForEditDepartment.getOrDefault(chatID, "0");
    }
    /**
     * Проверяет введённый пароль и обновляет состояние пользователя.
     *
     * @param messageText текст сообщения от пользователя
     * @param userId идентификатор пользователя
     * @return сообщение для пользователя о результате проверки пароля
     */
    private String checkValidPasswordInput(String messageText, Long userId) {
        if (messageText.equals(environmentService.getPassword())) {
            userStatesForEditDepartment.put(userId, "awaiting_numberForEdit");
            return getAllDepartmentsFromBd();
        } else {
            userStatesForEditDepartment.remove(userId);
            return MessageConstants.BAD_PASSWORD_COMMAND_RESPONSE;
        }
    }
    /**
     * Получает все факультеты из базы данных и формирует сообщение для пользователя.
     *
     * @return строка с сообщением, содержащим все факультеты
     */
    private String getAllDepartmentsFromBd(){
        String messageTextForUser = "";
        String[] allNamesDepartmentsWithId = departmentsInfo.getAllNamesWithId();
        messageTextForUser += MessageConstants.EDIT_DEPARTMENT_COMMAND_RESPONSE + "\n";
        for(String namesDepartmentsWithId : allNamesDepartmentsWithId){
            messageTextForUser += namesDepartmentsWithId + "\n";
        }
        return messageTextForUser;
    }

    private boolean checkValidNumberForDelete(String messageTextForUser, String messageText){
        List<Integer> numbers = new ArrayList<>();

        // Регулярное выражение для извлечения чисел
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(messageTextForUser);

        // Найдем все числа и добавим их в список
        while (matcher.find()) {
            numbers.add(Integer.parseInt(matcher.group()));
        }

        try {
            int messageTextInt = Integer.parseInt(messageText);
            if(numbers.contains(messageTextInt)){
                return false;
            }
        } catch (NumberFormatException e) {
            return true;
        }
        return true;
        //
    }
    /**
     * Обрабатывает команды пользователя для редактирования факультета.
     *
     * @param messageText текст сообщения от пользователя
     * @param userId идентификатор пользователя
     * @return ответ пользователю в зависимости от состояния и команды
     */
    public String worksWithEditDepartment(String messageText, Long userId) {
        String currentState = userStatesForEditDepartment.get(userId);
        if ("/edit_department_data".equals(messageText)) {
            userStatesForEditDepartment.put(userId, "awaiting_password");
            return MessageConstants.PASSWORD_COMMAND_RESPONSE;
        } else if ("awaiting_password".equals(currentState)) {
            messageTextForUser.put(userId,checkValidPasswordInput(messageText, userId));
            return messageTextForUser.get(userId);
        } else if ("awaiting_numberForEdit".equals(currentState)) {
            if(checkValidNumberForDelete(messageTextForUser.get(userId), messageText)){
                userStatesForEditDepartment.remove(userId);
                return MessageConstants.UN_CORRECT_NUMBER_COMMAND_RESPONSE;
            }
            dataForDepartment.setNumberForEditDepartment(userId, messageText);
            userStatesForEditDepartment.put(userId, "awaiting_newDepartment");
            return MessageConstants.NEW_DEPARTMENT_COMMAND_RESPONSE;
        }
        else if ("awaiting_newDepartment".equals(currentState)) {
            dataForDepartment.setNameForEditDepartment(userId, messageText);
            userStatesForEditDepartment.put(userId, "awaiting_textForDepartment");
            return MessageConstants.TEXT_FOR_DEPARTMENT_COMMAND_RESPONSE;
        }
        else if("awaiting_textForDepartment".equals(currentState)){
            dataForDepartment.setTextForEditDepartment(userId, messageText);
            userStatesForEditDepartment.remove(userId);
            keyboardsData.updateData(userId,databaseConnection, dataForDepartment);
            return MessageConstants.CHANGE_DEPARTMENT_COMMAND_RESPONSE;
        }
        return MessageConstants.DEFAULT_RESPONSE;
    }
}
