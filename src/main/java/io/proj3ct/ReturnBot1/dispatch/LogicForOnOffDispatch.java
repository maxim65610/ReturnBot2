package io.proj3ct.ReturnBot1.dispatch;

import io.proj3ct.ReturnBot1.baseClasses.MessageConstants;
import io.proj3ct.ReturnBot1.datebase.DatabaseConnection;
import io.proj3ct.ReturnBot1.registration.UsersData;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
/**
 * Класс, реализующий логику включения и выключения режима диспетча для пользователей,
 * а также формирование сообщений для пользователей на основе данных рассылки.
 * Используется для управления рассылками в зависимости от состояний пользователей и текущей даты.
 */
public class LogicForOnOffDispatch {
    /**
     * Форматтер для преобразования строковых значений в даты и наоборот в формате "dd.MM.yyyy".
     */
    private final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    /**
     * Объект для взаимодействия с базой данных.
     * Используется для установления соединения с базой данных и выполнения SQL-запросов.
     */
    private final DatabaseConnection databaseConnection = new DatabaseConnection();
    /**
     * Объект для работы с данными пользователей.
     * Содержит методы для проверки наличия пользователя в базе данных и изменения статуса рассылки.
     */
    private final UsersData usersData = new UsersData();
    /**
     * Объект для работы с данными рассылок.
     * Предоставляет доступ к данным рассылок и помогает получать необходимую информацию для отправки.
     */
    private final DispatchData dispatchData = new DispatchData();
    /**
     * Формирует сообщения для пользователей на основе данных диспетча.
     *
     * @param textToSend       текст для отправки
     * @param categoryDispatch категория диспетча(обычная или приемная комиссия)
     * @param currentDate      текущая дата
     * @param department       департамент
     * @return двумерный массив с идентификаторами пользователей и текстами для отправки
     */
    private String[][] messageToUserWithDispatch(String textToSend, String categoryDispatch,
                                                 LocalDate currentDate, String department) {
        String[][] rowsFromBDRegistration = dispatchData.getUserIdAndDispatchOnOrOff(databaseConnection);
        List<String[]> dispatchList = new ArrayList<>();
        for (int i = 0; i < rowsFromBDRegistration.length; i++) {
            String dispatchOnOrOff = rowsFromBDRegistration[i][1];
            String[] userIdAndDispatchText = new String[2];
            if (dispatchOnOrOff.equals("True")) {
                String userId = rowsFromBDRegistration[i][0];
                if (categoryDispatch.equals("приемная комиссия")) {
                    String yearEndSchoolUser = dispatchData.getUserYearEndSchool(Long.valueOf(userId), databaseConnection);
                    String currentYear = String.valueOf(currentDate).substring(0, 4);
                    if (currentYear.equals(yearEndSchoolUser)) {
                        String departmentUser = dispatchData.getUserResultTest(Long.valueOf(userId), databaseConnection);
                        if (departmentUser.equals("-")) {
                            userIdAndDispatchText[0] = userId;
                            userIdAndDispatchText[1] = "Приглашаем вас стать студентом УРФУ";
                            dispatchList.add(userIdAndDispatchText);
                        } else if (departmentUser.equals(department)) {
                            userIdAndDispatchText[0] = userId;
                            userIdAndDispatchText[1] = textToSend;
                            dispatchList.add(userIdAndDispatchText);
                        }
                    }
                } else {
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
     * Проверяет наличие диспетчей на текущую дату в базе данных.
     *
     * @return двумерный массив, содержащий идентификаторы пользователей и тексты для отправки
     */
    public String[][] checkDateForDispatch() {
        String[][] allRowsFromBdDispatch = dispatchData.getAllDispatchData(databaseConnection);
        List<String[]> userIdAndTextToSendList = new ArrayList<>();
        for (int i = 0; i < allRowsFromBdDispatch.length; i++) {
            String date = allRowsFromBdDispatch[i][2];
            String textToSend = allRowsFromBdDispatch[i][1];
            String categoryDispatch = allRowsFromBdDispatch[i][3];
            String department = allRowsFromBdDispatch[i][4];
            LocalDate inputDate = LocalDate.parse(date, DATE_FORMATTER);
            LocalDate currentDate = LocalDate.now();
            if (inputDate.equals(currentDate)) {
                String[][] userIdAndTextToSendArray = messageToUserWithDispatch(textToSend,
                        categoryDispatch, currentDate, department);
                for (String[] row : userIdAndTextToSendArray) {
                    userIdAndTextToSendList.add(row);
                }
            }
        }
        return userIdAndTextToSendList.toArray(new String[0][0]);
    }
    /**
     * Включает режим диспетча для пользователя.
     *
     * @param userId идентификатор пользователя
     * @return сообщение о результате операции
     */
    public String dispatchOn(Long userId) {
        if (!usersData.checkUserIdExistsInRegistrationDataTable(userId, databaseConnection)) {
            return "Эта функция недоступна, пока вы не зарегистрируетесь";
        }
        usersData.changeDispatchStatusOn(userId, databaseConnection);
        return MessageConstants.DISPATCH_ON_COMMAND_RESPONSE;
    }
    /**
     * Выключает режим диспетча для пользователя.
     *
     * @param userId идентификатор пользователя
     * @return сообщение о результате операции
     */
    public String dispatchOff(Long userId) {
        if (!usersData.checkUserIdExistsInRegistrationDataTable(userId, databaseConnection)) {
            return "Эта функция недоступна, пока вы не зарегистрируетесь";
        }
        usersData.changeDispatchStatusOff(userId, databaseConnection);
        return MessageConstants.DISPATCH_OFF_COMMAND_RESPONSE;
    }
}
