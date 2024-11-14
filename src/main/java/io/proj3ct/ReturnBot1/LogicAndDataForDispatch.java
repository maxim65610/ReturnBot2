package io.proj3ct.ReturnBot1;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LogicAndDataForDispatch {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private final Map<Long, String> dispatchText  = new HashMap<>();
    private final Map<Long, String> dispatchTime  = new HashMap<>();
    private final Map<Long, String> dispatchCategory  = new HashMap<>();
    private final Map<Long, String> dispatchDepartment  = new HashMap<>();
    private final Map<Long, String> userStatesForNewDispatch = new HashMap<>();
    private final DatabaseConnection databaseConnection = new DatabaseConnection();
    private final TextForMessage textForMessage = new TextForMessage();
    private final DatebaseTables datebaseTables = new DatebaseTables(databaseConnection);
    private DispatchData dispatchData = new DispatchData();
    /**
     * Получает объект подключения к базе данных.
     * @return объект DatabaseConnection
     */
    public DatabaseConnection getDatabaseConnection() {return databaseConnection;}

    public String getDispatchText(Long chatID) {return dispatchText .get(chatID);}

    public String getDispatchTime (Long chatID) {return dispatchTime .get(chatID);}

    public String getDispatchCategory (Long chatID) {return dispatchCategory .get(chatID);}

    public String getDispatchDepartment (Long chatID) {return dispatchDepartment .get(chatID);}

    public void setDispatchData(DispatchData dispatchData) {this.dispatchData =  dispatchData;}


    public String getUserStatesForNewDispatch(Long chatID) {
        return userStatesForNewDispatch.getOrDefault(chatID, "0");
    }

    public String worksWithNewDispatch(String messageText, Long userId,
                                       LogicAndDataForDispatch logicAndDataForDispatch) {
        Long dispatchID = dispatchData.generateNewId(databaseConnection);
        String currentState = userStatesForNewDispatch.get(userId);
        datebaseTables.createDispatchDataTable();
        if ("/newDispatсh".equals(messageText)) {
            userStatesForNewDispatch.put(userId, "awaiting_dispatchPassword");
            return textForMessage.setTheText("newDispatсh");
        } else if ("awaiting_dispatchPassword".equals(currentState)) {
            if (messageText.equals("1345")) {
                userStatesForNewDispatch.remove(userId);
                userStatesForNewDispatch.put(userId, "awaiting_dispatchText "); // Обновляем состояние
                return textForMessage.setTheText("goodPassword");
            }
            else{
                userStatesForNewDispatch.remove(userId);
                return textForMessage.setTheText("passwordBad");
            }
        } else if ("awaiting_dispatchText ".equals(currentState)) {
            dispatchText.put(dispatchID, messageText);
            userStatesForNewDispatch.remove(userId);
            userStatesForNewDispatch.put(userId, "awaiting_dispatchTime "); // Обновляем состояние
            return textForMessage.setTheText("dispatchTime");
        } else if ("awaiting_dispatchTime ".equals(currentState)) {
            try {
                LocalDate date = LocalDate.parse(messageText, DATE_FORMATTER);
                dispatchTime.put(dispatchID, messageText);
                userStatesForNewDispatch.remove(userId);
                userStatesForNewDispatch.put(userId, "awaiting_dispatchCategory ");
                return textForMessage.setTheText("dispatchCategory");
            } catch (DateTimeParseException e) {
                return textForMessage.setTheText("badTime");
            }
        } else if ("awaiting_dispatchCategory ".equals(currentState)) {
            if (messageText.equals("обычная") || messageText.equals("приемная комиссия")) {
                dispatchCategory.put(dispatchID, messageText);
                userStatesForNewDispatch.remove(userId);
                userStatesForNewDispatch.put(userId, "awaiting_dispatchDepartment ");
                return textForMessage.setTheText("dispatchDepartment");
            }
            else{
                return textForMessage.setTheText("badTime");
            }
        } else if ("awaiting_dispatchDepartment ".equals(currentState)) {
            dispatchDepartment.put(dispatchID, messageText);
            userStatesForNewDispatch.remove(userId);
            dispatchData.insertData(dispatchID, this, databaseConnection);
            return textForMessage.setTheText("dispatchEnd");
        }
        System.out.println("Текущее состояние: " + currentState);
        return textForMessage.setTheText("newDispatсh");
    }

}
