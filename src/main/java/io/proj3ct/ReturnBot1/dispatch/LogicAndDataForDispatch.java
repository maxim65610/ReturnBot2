package io.proj3ct.ReturnBot1.dispatch;

import io.proj3ct.ReturnBot1.datebase.DatabaseConnection;
import io.proj3ct.ReturnBot1.datebase.DatebaseTables;
import io.proj3ct.ReturnBot1.baseClasses.TextForMessage;
import io.proj3ct.ReturnBot1.registration.UsersData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Класс для обработки логики и данных диспетча.
 * Хранит информацию о текстах, времени, категории и отделе диспетча,
 * а также взаимодействует с базой данных для создания и получения данных о диспетчах.
 * Обрабатывает команды пользователей для создания и получения диспетчей,
 * включая проверку прав доступа, состояния пользователя.
 */
public class LogicAndDataForDispatch {
    private final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private final Map<Long, String> dispatchText  = new HashMap<>();
    private final Map<Long, String> dispatchTime  = new HashMap<>();
    private final Map<Long, String> dispatchCategory  = new HashMap<>();
    private final Map<Long, String> dispatchDepartment  = new HashMap<>();
    private final Map<Long, String> userStatesForNewDispatch = new HashMap<>();
    private DatabaseConnection databaseConnection = new DatabaseConnection();
    private TextForMessage textForMessage = new TextForMessage();
    private final DatebaseTables datebaseTables = new DatebaseTables(databaseConnection);
    private UsersData usersData = new UsersData();
    private final DispatchData dispatchData = new DispatchData();

    /**
     * Конструктор по умолчанию.
     */
    public LogicAndDataForDispatch(){
    }
    /**
     * Конструктор для тестов инициализации с пользовательскими зависимостями.
     *
     * @param databaseConnection объект для подключения к базе данных
     * @param usersData объект для работы с данными пользователей
     * @param textForMessage объект для работы с текстами сообщений
     */
    public LogicAndDataForDispatch(DatabaseConnection databaseConnection,
                                   UsersData usersData,TextForMessage textForMessage){
        this.databaseConnection = databaseConnection;
        this.usersData = usersData;
        this.textForMessage = textForMessage;

    }
    /**
     * Получает объект подключения к базе данных.
     *
     * @return объект DatabaseConnection
     */
    public DatabaseConnection getDatabaseConnection() {
        return databaseConnection;
    }
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
     * Получает состояние пользователя для нового диспетча.
     *
     * @param chatID идентификатор чата
     * @return состояние пользователя или "0", если не найдено
     */
    public String getUserStatesForNewDispatch(Long chatID) {
        return userStatesForNewDispatch.getOrDefault(chatID, "0");
    }
    /**
     * Включает режим диспетча для пользователя.
     *
     * @param userId идентификатор пользователя
     * @return сообщение о результате операции
     */
    public String dispatchOn(Long userId){
        if (!usersData.checkUserIdExistsInRegistrationDataTable(userId, databaseConnection)) {
            return "Эта функция недоступна, пока вы не зарегистрируетесь";
        }
        usersData.changeDispatchStatusOn(userId, databaseConnection);
        return textForMessage.setTheText("/dispatchOn");
    }
    /**
     * Проверяет наличие диспетчей на текущую дату в базе данных.
     *
     * @return двумерный массив, содержащий идентификаторы пользователей и тексты для отправки
     */
    public String[][] checkDateForDispatch(){
        String[][] allRowsFromBdDispatch = dispatchData.getAllDispatchData(databaseConnection);
        List<String[]> userIdAndTextToSendList = new ArrayList<>();
        for(int i = 0; i < allRowsFromBdDispatch.length; i++){
            String date = allRowsFromBdDispatch[i][2];
            String textToSend = allRowsFromBdDispatch[i][1];
            String categoryDispatch = allRowsFromBdDispatch[i][3];
            String department = allRowsFromBdDispatch[i][4];
            LocalDate inputDate = LocalDate.parse(date, DATE_FORMATTER);
            LocalDate currentDate = LocalDate.now();
            if(inputDate.equals(currentDate)){
                String[][] userIdAndTextToSendArray= messageToUserWithDispatch(textToSend,
                        categoryDispatch, currentDate, department);
                for(String[] row : userIdAndTextToSendArray) {
                    userIdAndTextToSendList.add(row);
                }
            }
        }
        return userIdAndTextToSendList.toArray(new String[0][0]);
    }
    /**
     * Формирует сообщения для пользователей на основе данных диспетча.
     *
     * @param textToSend текст для отправки
     * @param categoryDispatch категория диспетча(обычная или приемная комиссия)
     * @param currentDate текущая дата
     * @param department департамент
     * @return двумерный массив с идентификаторами пользователей и текстами для отправки
     */
    private String[][] messageToUserWithDispatch(String textToSend, String categoryDispatch,
                                                 LocalDate currentDate, String department){
        String[][] rowsFromBDRegistration = dispatchData.getUserIdAndDispatchOnOrOff(databaseConnection);
        List<String[]> dispatchList = new ArrayList<>();
        for(int i = 0; i < rowsFromBDRegistration.length; i++){
            String dispatchOnOrOff = rowsFromBDRegistration[i][1];
            String[] userIdAndDispatchText = new String[2];
            if(dispatchOnOrOff.equals("True")){
                String userId = rowsFromBDRegistration[i][0];
                if(categoryDispatch.equals("приемная комиссия")){
                    String yearEndSchoolUser = dispatchData.getUserYearEndSchool
                            (Long.valueOf(userId), databaseConnection);
                    String currentYear = String.valueOf(currentDate).substring(0,4);
                    if(currentYear.equals(yearEndSchoolUser)){
                        String departmentUser = dispatchData.getUserResultTest
                                (Long.valueOf(userId), databaseConnection);
                        if(departmentUser.equals("-")){
                            userIdAndDispatchText[0] = userId;
                            userIdAndDispatchText[1] = "Приглашаем вас стать студентом УРФУ";
                            dispatchList.add(userIdAndDispatchText);
                        }
                        else if(departmentUser.equals(department)){
                            userIdAndDispatchText[0] = userId;
                            userIdAndDispatchText[1] = textToSend;
                            dispatchList.add(userIdAndDispatchText);
                        }
                    }
                }
                else{
                    userIdAndDispatchText[0] = userId;
                    userIdAndDispatchText[1] = textToSend;
                    dispatchList.add(userIdAndDispatchText);
                }
            }
        }
        String[][] dispatchDataArray = new String[dispatchList.size()][2];
        for (int i = 0; i < dispatchList.size(); i++) {
            dispatchDataArray[i] = dispatchList.get(i);
        }
        return dispatchDataArray;
    }
    /**
     * Выключает режим диспетча для пользователя.
     *
     * @param userId идентификатор пользователя
     * @return сообщение о результате операции
     */
    public String dispatchOff(Long userId){
        if (!usersData.checkUserIdExistsInRegistrationDataTable(userId, databaseConnection)) {
            return "Эта функция недоступна, пока вы не зарегистрируетесь";
        }
        usersData.changeDispatchStatusOff(userId, databaseConnection);
        return textForMessage.setTheText("/dispatchOff");
    }
    /**
     * Обрабатывает новую команду диспетча от пользователя.
     *
     * @param messageText текст сообщения от пользователя
     * @param userId идентификатор пользователя
     * @return ответное сообщение для пользователя
     */
    public String worksWithNewDispatch(String messageText, Long userId) {
        Long dispatchID = dispatchData.generateNewId(databaseConnection);

        datebaseTables.createDispatchDataTable();
        String currentState = userStatesForNewDispatch.get(userId);

        if ("/newDispatсh".equals(messageText)) {
            userStatesForNewDispatch.put(userId, "awaiting_dispatchPassword");
            return textForMessage.setTheText("newDispatсh");
        } else if ("awaiting_dispatchPassword".equals(currentState)) {
            if (messageText.equals(System.getenv("password"))) {
                userStatesForNewDispatch.remove(userId);
                userStatesForNewDispatch.put(userId, "awaiting_dispatchText"); // Обновляем состояние
                return textForMessage.setTheText("goodPassword");
            } else {
                userStatesForNewDispatch.remove(userId);
                return textForMessage.setTheText("passwordBad");
            }
        } else if ("awaiting_dispatchText".equals(currentState)) {
            dispatchText.put(dispatchID, messageText);
            userStatesForNewDispatch.remove(userId);
            userStatesForNewDispatch.put(userId, "awaiting_dispatchTime"); // Обновляем состояние
            return textForMessage.setTheText("dispatchTime");
        } else if ("awaiting_dispatchTime".equals(currentState)) {
            try {
                LocalDate date = LocalDate.parse(messageText, DATE_FORMATTER);
                dispatchTime.put(dispatchID, messageText);
                userStatesForNewDispatch.remove(userId);
                userStatesForNewDispatch.put(userId, "awaiting_dispatchCategory");
                return textForMessage.setTheText("dispatchCategory");
            } catch (DateTimeParseException e) {
                return textForMessage.setTheText("badTime");
            }
        } else if ("awaiting_dispatchCategory".equals(currentState)) {
            if (messageText.equals("обычная") || messageText.equals("приемная комиссия")) {
                if (messageText.equals("обычная")) {
                    dispatchCategory.put(dispatchID, messageText);
                    dispatchDepartment.put(dispatchID, "-");
                    userStatesForNewDispatch.remove(userId);
                    dispatchData.insertData(dispatchID, this,
                            databaseConnection,dispatchID);
                    return textForMessage.setTheText("dispatchEnd");
                } else {
                    dispatchCategory.put(dispatchID, messageText);
                    userStatesForNewDispatch.remove(userId);
                    userStatesForNewDispatch.put(userId, "awaiting_dispatchDepartment");
                    return textForMessage.setTheText("dispatchDepartment");
                }
            } else {
                return textForMessage.setTheText("badTime");
            }
        } else if ("awaiting_dispatchDepartment".equals(currentState)) {
            dispatchDepartment.put(dispatchID, messageText);
            userStatesForNewDispatch.remove(userId);
            dispatchData.insertData(dispatchID, this,
                    databaseConnection,dispatchID);
            return textForMessage.setTheText("dispatchEnd");
        }

        System.out.println("Текущее состояние: " + currentState);
        return textForMessage.setTheText("newDispatсh");
    }
}
