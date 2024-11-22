package io.proj3ct.ReturnBot1.dispatch;

import io.proj3ct.ReturnBot1.baseClasses.MessageConstants;
import io.proj3ct.ReturnBot1.datebase.DatabaseConnection;
import io.proj3ct.ReturnBot1.datebase.DatebaseTables;
import io.proj3ct.ReturnBot1.registration.UsersData;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс для обработки логики и данных диспетча.
 * Хранит информацию о текстах, времени, категории и отделе диспетча,
 * а также взаимодействует с базой данных для создания и получения данных о диспетчах.
 * Обрабатывает команды пользователей для создания и получения диспетчей,
 * включая проверку прав доступа, состояния пользователя.
 */
public class LogicForNewDispatch {
    private final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private final Map<Long, String> userStatesForNewDispatch = new HashMap<>();
    private final DispatchDataStorage dispatchDataStorage = new DispatchDataStorage();
    private DatabaseConnection databaseConnection = new DatabaseConnection();
    private DatebaseTables datebaseTables = new DatebaseTables(databaseConnection);
    private UsersData usersData = new UsersData();
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
     * Получает состояние пользователя для нового диспетча.
     *
     * @param chatID идентификатор чата
     * @return состояние пользователя или "0", если не найдено
     */
    public String getUserStatesForNewDispatch(Long chatID) {
        return userStatesForNewDispatch.getOrDefault(chatID, "0");
    }

    /**
     * Проверяет введенный пароль и обновляет состояние пользователя.
     *
     * @param messageText текст сообщения от пользователя
     * @param userId идентификатор пользователя
     * @return сообщение о результате проверки пароля
     */
    private String checkValidPasswordInput(String messageText, Long userId) {
        if (messageText.equals(System.getenv("password"))) {
            userStatesForNewDispatch.put(userId, "awaiting_dispatchText");
            return MessageConstants.SUCCESSFUL_PASSWORD_NEW_DISPATCH;
        } else {
            userStatesForNewDispatch.remove(userId);
            return MessageConstants.BAD_PASSWORD_NEW_DISPATCH;
        }
    }
    /**
     * Проверяет введенное время и обновляет состояние пользователя.
     *
     * @param messageText текст сообщения от пользователя
     * @param dispatchID идентификатор диспетча
     * @param userId идентификатор пользователя
     * @return сообщение о результате проверки времени
     */
    private String checkValidTimeInput(String messageText, Long dispatchID, Long userId) {
        try {
            LocalDate.parse(messageText, DATE_FORMATTER);
            dispatchDataStorage.setDispatchTime(dispatchID, messageText);
            userStatesForNewDispatch.put(userId, "awaiting_dispatchCategory");
            return MessageConstants.DISPATCH_CATEGORY;
        } catch (DateTimeParseException e) {
            System.out.println(2);
            return MessageConstants.DISPATCH_TIME_BAD;
        }
    }
    /**
     * Обрабатывает ввод категории диспетча и обновляет состояние пользователя.
     *
     * @param messageText текст сообщения от пользователя
     * @param dispatchID идентификатор диспетча
     * @param userId идентификатор пользователя
     * @return сообщение о результате обработки категории
     */
    private String handleDispatchCategoryInput(String messageText, Long dispatchID, Long userId) {
        if (messageText.equals("обычная") || messageText.equals("приемная комиссия")) {
            dispatchDataStorage.setDispatchCategory(dispatchID, messageText);
            if (messageText.equals("приемная комиссия")) {
                userStatesForNewDispatch.put(userId, "awaiting_dispatchDepartment");
                return MessageConstants.DISPATCH_DEPARTMENT;
            } else {
                dispatchDataStorage.setDispatchDepartment(dispatchID, "-");
                dispatchData.insertData(dispatchID, dispatchDataStorage, databaseConnection, dispatchID);
                return MessageConstants.NEW_DISPATCH_SUCCESSFUL;
            }
        } else {
            System.out.println(1);
            return MessageConstants.DISPATCH_TIME_BAD;
        }
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

        if ("/new_dispatch".equals(messageText)) {
            userStatesForNewDispatch.put(userId, "awaiting_dispatchPassword");
            return MessageConstants.NEW_DISPATCH;
        } else if ("awaiting_dispatchPassword".equals(currentState)) {
            return checkValidPasswordInput(messageText, userId);
        } else if ("awaiting_dispatchText".equals(currentState)) {
            dispatchDataStorage.setDispatchText(dispatchID, messageText);
            userStatesForNewDispatch.put(userId, "awaiting_dispatchTime");
            return MessageConstants.DISPATCH_TIME;
        } else if ("awaiting_dispatchTime".equals(currentState)) {
            return checkValidTimeInput(messageText, dispatchID, userId);
        } else if ("awaiting_dispatchCategory".equals(currentState)) {
            String response = handleDispatchCategoryInput(messageText, dispatchID, userId);
            if (response.equals(MessageConstants.NEW_DISPATCH_SUCCESSFUL)) {
                userStatesForNewDispatch.remove(userId); // Очистка статуса пользователя
            }
            return response;
        } else if ("awaiting_dispatchDepartment".equals(currentState)) {
            dispatchDataStorage.setDispatchDepartment(dispatchID, messageText);
            dispatchData.insertData(dispatchID, dispatchDataStorage, databaseConnection, dispatchID);
            userStatesForNewDispatch.remove(userId); // Очистка статуса пользователя
            return MessageConstants.NEW_DISPATCH_SUCCESSFUL;
        }
        return MessageConstants.NEW_DISPATCH;
    }
}
