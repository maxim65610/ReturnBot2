package io.proj3ct.ReturnBot1;

import java.util.ArrayList;
import java.util.List;

/**
 * Контроллер логики обработки сообщений и взаимодействия с пользователями.
 * Обрабатывает различные типы входящих обновлений и управляет состояниями пользователей.
 */
public class LogicController {
    private final LogicForTestABI logicForTestABI = new LogicForTestABI();
    private DepartmentsInfo departmentsInfo = new DepartmentsInfo();
    private final EmailLogic emailLogic = new EmailLogic();
    private final String username = System.getenv("mail"); // Ваша почта
    private final String password = System.getenv("passwordForMail");
    private final EmailSender emailSender = new EmailSender(username, password);
    private final LogicForChangeDataUsers logicForChangeDataUsers = new LogicForChangeDataUsers();
    private final UsersData usersData = new UsersData();
    private final DatabaseConnection databaseConnection = new DatabaseConnection();
    private final LogicAndDataForRegistrationUsers logicAndDataForRegistrationUsers
            = new LogicAndDataForRegistrationUsers();

    // Добавляем конструктор, который принимает зависимости
    public LogicController() {

    }
    // Добавляем конструктор, который принимает зависимости
    public LogicController(DepartmentsInfo departmentsInfo) {
        this.departmentsInfo = departmentsInfo;
    }
    /**
     * Проверяет, что делать с переданными данными для клавиатуры.
     * @param data Входные данные для обработки.
     * @return Обработанное сообщение или исходные данные.
     */
    private String checkWhatTodo(String data) {
        return switch (data) {
            case "ИЕНИМ" -> MessageConstants.INST_IENIM_COMMAND_RESPONSE;
            case "РТФ" -> MessageConstants.INST_RTF_COMMAND_RESPONSE;
            case "ХТИ" -> MessageConstants.INST_CHTI_COMMAND_RESPONSE;
            default -> data;
        };
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
                listForWorkWithKeyboardAndMessage = logicForTestABI.worksWithTestABI("", userId, messageText);
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
                listForWorkWithKeyboardAndMessage.add(emailLogic.worksWithMail
                        (messageText, userId, emailSender,
                                emailLogic,databaseConnection));
            }
            else if("/authorization".equals(messageText) || (!logicAndDataForRegistrationUsers.
                    getUserStatesForRegistration(userId).equals("0"))){
                listForWorkWithKeyboardAndMessage.add(logicAndDataForRegistrationUsers.worksWithRegistration
                        (messageText, userId,emailSender, logicAndDataForRegistrationUsers));
            }
            else if("/userDataChange".equals(messageText) || (!logicForChangeDataUsers.
                    getUserStatesForChangeData(userId).equals("0"))){
                listForWorkWithKeyboardAndMessage.add(logicForChangeDataUsers.worksWithChangeData
                        (messageText, userId, emailSender));
            }
            else if("/userInfo".equals(messageText)){
                if(usersData.checkUserIdExistsInRegistrationDataTable(userId,databaseConnection)) {
                    listForWorkWithKeyboardAndMessage.add(usersData.getRegistrationData(userId,
                            logicAndDataForRegistrationUsers.getDatabaseConnection()));
                }
                else {
                    listForWorkWithKeyboardAndMessage.add(MessageConstants.NO_REGISTRATION);
                }
            }
            else if("/userDataDell".equals(messageText)){
                usersData.deleteData(userId,
                        logicAndDataForRegistrationUsers.getDatabaseConnection());
                listForWorkWithKeyboardAndMessage.add(MessageConstants.DATA_DELETED);
            }
            else if("/testAbit".equals(messageText)){
                logicForTestABI.worksWithTestABI(messageText, userId, "100");
                listForWorkWithKeyboardAndMessage.add(MessageConstants.TEST_ABIT_COMMAND_RESPONSE);
                listForWorkWithKeyboardAndMessage.add(messageText);
            }
            else if("/testres".equals(messageText)){
                listForWorkWithKeyboardAndMessage.add(logicForTestABI.getResult(userId));
            }
            else if("/work".equals(messageText)){
                listForWorkWithKeyboardAndMessage.add(MessageConstants.WORK_COMMAND_RESPONSE);
                listForWorkWithKeyboardAndMessage.add(messageText);
            }
            else {
                listForWorkWithKeyboardAndMessage.add(MessageConstants.DEFAULT_RESPONSE);
                listForWorkWithKeyboardAndMessage.add(messageText);
            }
        }
        return listForWorkWithKeyboardAndMessage;
    }
}
