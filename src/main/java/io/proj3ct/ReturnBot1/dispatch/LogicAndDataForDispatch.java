package io.proj3ct.ReturnBot1.dispatch;

import io.proj3ct.ReturnBot1.datebase.DatabaseConnection;
import io.proj3ct.ReturnBot1.datebase.DatebaseTables;
import io.proj3ct.ReturnBot1.baseClasses.TextForMessage;

import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Класс для обработки логики и данных диспетча.
 * Хранит информацию о текстах, времени, категории и отделе диспетча.
 */
public class LogicAndDataForDispatch {
    private final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

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
