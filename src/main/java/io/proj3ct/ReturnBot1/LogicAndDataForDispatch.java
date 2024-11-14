package io.proj3ct.ReturnBot1;
import java.util.HashMap;
import java.util.Map;

public class LogicAndDataForDispatch {
    private final Map<Long, String> dispatchPassword  = new HashMap<>();
    private final Map<Long, String> dispatchText  = new HashMap<>();
    private final Map<Long, String> dispatchTime  = new HashMap<>();
    private final Map<Long, String> dispatchCategory  = new HashMap<>();
    private final Map<Long, String> dispatchDepartment  = new HashMap<>();
    private final Map<Long, String> userStatesForNewDispatch = new HashMap<>();
    private DatabaseConnection databaseConnection = new DatabaseConnection();
    private TextForMessage textForMessage = new TextForMessage();
    private final DatebaseTables datebaseTables = new DatebaseTables(databaseConnection);
    private DispatchData dispatchData = new DispatchData();
    /**
     * Получает объект подключения к базе данных.
     * @return объект DatabaseConnection
     */
    public DatabaseConnection getDatabaseConnection() {return databaseConnection;}

    public String getDispatchPassword (Long chatID) {return dispatchPassword .get(chatID);}

    public String getDispatchText(Long chatID) {return dispatchText .get(chatID);}

    public String getDispatchTime (Long chatID) {return dispatchTime .get(chatID);}

    public String getDispatchCategory (Long chatID) {return dispatchCategory .get(chatID);}

    public String getDispatchDepartment (Long chatID) {return dispatchDepartment .get(chatID);}

    public void setDispatchData(DispatchData dispatchData) {this.dispatchData =  dispatchData;}

    public void setDatabaseConnection(DatabaseConnection databaseConnection) {this.databaseConnection = databaseConnection;}

    public void setTextForMessage(TextForMessage textForMessage) {this.textForMessage = textForMessage;
    }

    public void setUserStatesForNewDispatch(Long userId, String state) {userStatesForNewDispatch.put(userId, state);}

    public String getUserStatesForNewDispatch(Long chatID) {
        return userStatesForNewDispatch.getOrDefault(chatID, "0");
    }

    public String worksWithNewDispatch(String messageText, Long userId,
                                       LogicAndDataForDispatch logicAndDataForDispatch) {

        String currentState = userStatesForNewDispatch.get(userId);
        if ("/newDispatсh".equals(messageText)) {
            datebaseTables.createRegistrationDataTable();
            userStatesForNewDispatch.put(userId, "awaiting_dispatchPassword ");
            return textForMessage.setTheText("newDispatсh");

        } else if ("awaiting_dispatchPassword ".equals(currentState)) {
                if (messageText.equals("1345")) {
                    System.out.println(messageText);
                    dispatchText.put(userId, messageText);
                    userStatesForNewDispatch.remove(userId);
                    userStatesForNewDispatch.put(userId, "awaiting_dispatchText");
                    return textForMessage.setTheText("goodPassword");
                } else {
                    return textForMessage.setTheText("passwordBad");
                }

        } else if ("awaiting_dispatchText".equals(currentState)) {
            dispatchText.put(userId, messageText);
            userStatesForNewDispatch.remove(userId);
            userStatesForNewDispatch.put(userId, "awaiting_dispatchTime ");
            return textForMessage.setTheText("dispatchText");
        } else if ("awaiting_dispatchTime ".equals(currentState)) {
            dispatchTime .put(userId, messageText);
            userStatesForNewDispatch.remove(userId);
            userStatesForNewDispatch.put(userId, "awaiting_dispatchCategory ");
            return textForMessage.setTheText("dispatchTime");
        } else if ("awaiting_dispatchCategory ".equals(currentState)) {
            dispatchCategory .put(userId, messageText);
            userStatesForNewDispatch.remove(userId);
            userStatesForNewDispatch.put(userId, "awaiting_dispatchDepartment ");
            return textForMessage.setTheText("dispatchCategory");
        } else if ("awaiting_dispatchDepartment ".equals(currentState)) {
            dispatchDepartment .put(userId, messageText);
            userStatesForNewDispatch.remove(userId);
            userStatesForNewDispatch.put(userId, "awaiting_dispatchEnd ");
            return textForMessage.setTheText("dispatchDepartment");
        }
        else if ("awaiting_dispatchEnd ".equals(currentState)) {
            userStatesForNewDispatch.remove(userId);
            return textForMessage.setTheText("dispatchEnd");
        }
        return textForMessage.setTheText("newDispatсh");
    }
}
