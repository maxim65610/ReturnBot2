package io.proj3ct.ReturnBot1;

import java.util.HashMap;
import java.util.Map;

/**
 * Обрабатывает логику взаимодействия с пользователями при работе с /question(функция отправки сообщения
 * от пользователя на почту).
 * Хранит состояния пользователей и их электронные адреса, а также управляет процессом
 * получения вопросов от пользователей.
 */
public class EmailLogic {
    private final TextForMessage textForMessage = new TextForMessage();
    private final Map<Long, String> userStatesForMail = new HashMap<>();
    private final Map<Long, String> userMails = new HashMap<>();
    private UsersData usersData = new UsersData();
    /**
     * Возвращает текущее состояние пользователя по идентификатору.
     * @param chatID Идентификатор чата пользователя.
     * @return Строка с состоянием пользователя или "0", если состояний нет.
     */
    public String getUserStatesForEmail(Long chatID){
        return userStatesForMail.getOrDefault(chatID, "0");
    }
    /**
     * Устанавливает объект UsersData, который будет использоваться для работы с данными пользователей(используется для тестов).
     * @param usersData экземпляр класса UsersData, содержащий информацию о пользователях.
     */
    public void setUsersData(UsersData usersData) {
        this.usersData = usersData;
    }
    /**
     * Обрабатывает сообщения пользователей и управляет состоянием.
     *
     * @param messageText  Текст сообщения пользователя.
     * @param userId      Идентификатор пользователя.
     * @param emailSender  Объект для отправки электронной почты.
     * @param emailLogic   Объект EmailLogic, используемый для управления состоянием.
     * @return Ответ пользователю в зависимости от текущего состояния.
     */
    public String worksWithMail(String messageText, Long userId, EmailSender emailSender, EmailLogic emailLogic,
                                 DatabaseConnection databaseConnection) {
        String currentState = userStatesForMail.get(userId);
        if ("/question".equals(messageText)) {
            if (!usersData.checkUserIdExistsInRegistrationDataTable(userId, databaseConnection)) {
                return "Эта функция недоступна, пока вы не зарегистрируетесь";
            }
            userStatesForMail.put(userId, "awaiting_question");
        } else if ("awaiting_question".equals(currentState)) {
            String mailUser = usersData.getUserMail(userId, databaseConnection);
            emailSender.sendEmail(emailSender.getUsername(), "Вопрос от абитуриента " + mailUser, messageText);
            userStatesForMail.remove(userId);
            userMails.remove(userId);
            return "Ваш вопрос отправлен";
        }
        return textForMessage.setTheText(messageText);
    }
}
