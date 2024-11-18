package io.proj3ct.ReturnBot1.baseClasses;

import io.proj3ct.ReturnBot1.departmentsAndTest.LogicForTestABI;
import io.proj3ct.ReturnBot1.datebase.DatabaseConnection;
import io.proj3ct.ReturnBot1.departmentsAndTest.DepartmentsInfo;
import io.proj3ct.ReturnBot1.dispatch.LogicAndDataForDispatch;
import io.proj3ct.ReturnBot1.mail.EmailLogic;
import io.proj3ct.ReturnBot1.mail.EmailSender;
import io.proj3ct.ReturnBot1.registration.LogicAndDataForRegistrationUsers;
import io.proj3ct.ReturnBot1.registration.LogicForChangeDataUsers;
import io.proj3ct.ReturnBot1.registration.UsersData;

import java.util.ArrayList;
import java.util.List;

/**
 * Контроллер логики обработки сообщений и взаимодействия с пользователями.
 * Обрабатывает различные типы входящих обновлений и управляет состояниями пользователей.
 */
public class LogicController {
    private final LogicForTestABI logicForTestABI = new LogicForTestABI();
    private final TextForMessage textForMessage = new TextForMessage();
    private final DepartmentsInfo departmentsInfo = new DepartmentsInfo();
    private final EmailLogic emailLogic = new EmailLogic();
    private final String username = System.getenv("mail"); // Ваша почта
    private final String password = System.getenv("passwordForMail");
    private final EmailSender emailSender = new EmailSender(username, password);
    private final LogicForChangeDataUsers logicForChangeDataUsers = new LogicForChangeDataUsers();
    private final UsersData usersData = new UsersData();
    private final DatabaseConnection databaseConnection = new DatabaseConnection();
    private final LogicAndDataForRegistrationUsers logicAndDataForRegistrationUsers
            = new LogicAndDataForRegistrationUsers();
    private final LogicAndDataForDispatch logicAndDataForDispatch = new LogicAndDataForDispatch();
    /**
     * Проверяет, что делать с переданными данными для клавиатуры.
     * @param data Входные данные для обработки.
     * @return Обработанное сообщение или исходные данные.
     */
    private String checkWhatTodo(String data) {
        if (data.equals("ИЕНИМ") || data.equals("РТФ") || data.equals("ХТИ")) {
            return textForMessage.setTheText(data);
        } else {
            return data;
        }
    }
    /**
     * Обрабатывает обновления и генерирует ответные сообщения.
     * @param userId ID пользователя, отправившего сообщение.
     * @return Список строк, представляющих сообщения и опции для клавиатуры.
     */
    public List<String> handleMessage(long userId, String messageText,boolean flagForKeyboard) {
        List<String> listForWorkWithKeyboardAndMessage = new ArrayList<>();
        if (flagForKeyboard){
            if (!(logicForTestABI.getUserStatesForTest(userId).equals("0"))) {
                listForWorkWithKeyboardAndMessage = logicForTestABI.getDataBd("", userId, messageText);
                if (logicForTestABI.getUserStatesForTest(userId).equals("awaiting_testABI_11")) {
                    logicForTestABI.removeUserStatesForTest(userId);
                }
            }
            else if (departmentsInfo.extract(messageText) == null){
                listForWorkWithKeyboardAndMessage.add(checkWhatTodo(messageText));
                listForWorkWithKeyboardAndMessage.add(messageText);
            }
            else {
                listForWorkWithKeyboardAndMessage.add(departmentsInfo.extract(messageText));
                listForWorkWithKeyboardAndMessage.add(messageText);
            }
        }
        else{
            if ("/question".equals(messageText) || (!(emailLogic.getUserStatesForEmail(userId).equals("0")))) {
                listForWorkWithKeyboardAndMessage.add(emailLogic.getWorksWithMail
                        (messageText, userId, emailSender,
                                emailLogic,databaseConnection));
            }
            else if("/authorization".equals(messageText) || (!logicAndDataForRegistrationUsers.
                    getUserStatesForRegistration(userId).equals("0"))){
                listForWorkWithKeyboardAndMessage.add(logicAndDataForRegistrationUsers.worksWithRegistration
                        (messageText, userId,emailSender, logicAndDataForRegistrationUsers));
            }
            else if("/newDispatсh".equals(messageText) || (!logicAndDataForDispatch.
                    getUserStatesForNewDispatch(userId).equals("0"))){
                listForWorkWithKeyboardAndMessage.add(logicAndDataForDispatch.worksWithNewDispatch
                        (messageText, userId));
            }
            else if("/userdatachange".equals(messageText) || (!logicForChangeDataUsers.
                    getUserStatesForChangeData(userId).equals("0"))){
                listForWorkWithKeyboardAndMessage.add(logicForChangeDataUsers.worksWithChangeData
                        (messageText, userId, emailSender));
            }
            else if("/userinfo".equals(messageText)){
                listForWorkWithKeyboardAndMessage.add(usersData.takeData(userId,
                                logicAndDataForRegistrationUsers.getDatabaseConnection()));
            }
            else if("/userdatadell".equals(messageText)){
                usersData.deleteData(userId,
                        logicAndDataForRegistrationUsers.getDatabaseConnection());
                listForWorkWithKeyboardAndMessage.add("Ваши данные успешно удалены");
            }
            else if("/testabit".equals(messageText)){
                logicForTestABI.getDataBd(messageText, userId, "100");
                listForWorkWithKeyboardAndMessage.add(textForMessage.setTheText(messageText));
                listForWorkWithKeyboardAndMessage.add(messageText);
            }
            else if("/testres".equals(messageText)){
                listForWorkWithKeyboardAndMessage.add(logicForTestABI.getResult(userId));
            }
            else {
                listForWorkWithKeyboardAndMessage.add(textForMessage.setTheText(messageText));
                listForWorkWithKeyboardAndMessage.add(messageText);
            }
        }
        return listForWorkWithKeyboardAndMessage;
    }
}
