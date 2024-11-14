package io.proj3ct.ReturnBot1;

import java.util.HashMap;
import java.util.Map;

/**
 * Возвращает текст для сообщения для некоторых команд для бота.
 * (Надеюсь этот класс не сильно нарушает ооп, он очень удобный
 * и используется чуть ли не в каждом классе, чтобы в них
 * ооп не выходил погулять)
 */
public class TextForMessage {
    private Map<String, String> messageMap;
    /**
     * Конструктор, который инициализирует словарь сообщений.
     * Внутри вызывает метод для заполнения словаря данными.
     */
    public TextForMessage() {
        messageMap = new HashMap<>();
        initializeMessageMap();
    }
    /**
     * Метод для получения текста сообщения по ключу (команде).
     *
     * @param messageText Ключ (команда), по которому нужно найти соответствующий ответ.
     * @return Соответствующий текст сообщения или null, если команда не найдена.
     */
    public String getMessageMap(String messageText){
        return messageMap.get(messageText);
    }
    /**
     * Инициализация словаря сообщений с заранее определенными значениями.
     * Заполняет поле {@link #messageMap} соответствующими парами ключ-значение,
     * где ключ — это команда, а значение — текст сообщения для ответа.
     */
    private void initializeMessageMap() {
        messageMap.put("/testAbit", MessageConstants.TEST_ABIT_COMMAND_RESPONSE);
        messageMap.put("/work", MessageConstants.WORK_COMMAND_RESPONSE);
        messageMap.put("/question", MessageConstants.QUESTION_COMMAND_RESPONSE);
        messageMap.put("/authorization", MessageConstants.REGISTRATION_COMMAND_RESPONSE);
        messageMap.put("authorization", MessageConstants.REGISTRATION_COMMAND_RESPONSE);
        messageMap.put("/userDataDell", MessageConstants.DEL_DATA_COMMAND_RESPONSE);
        messageMap.put("/userDataChange", MessageConstants.CHANGEDATA_COMMAND_RESPONSE);
        messageMap.put("userDataChange", MessageConstants.CHANGEDATA_COMMAND_RESPONSE);
        messageMap.put("correctMail", MessageConstants.CORRECT_MAIL_COMMAND_RESPONSE);
        messageMap.put("notСorrectMail", MessageConstants.NOT_CORRECT_MAIL_COMMAND_RESPONSE);
        messageMap.put("questionHasBeenSend", MessageConstants.QUESTION_HAS_BEEN_SEND_COMMAND_RESPONSE);
        messageMap.put("userPassedTest", MessageConstants.END_TEST_ABI_COMMAND_RESPONSE);
        messageMap.put("resultTestABI", MessageConstants.RESULT_TEST_ABI_COMMAND_RESPONSE);
        messageMap.put("registration", MessageConstants.AUTHORISATION_COMMAND_RESPONSE);
        messageMap.put("successful_name", MessageConstants.SUCCESSFUL_NAME);
        messageMap.put("successful_surname", MessageConstants.SUCCESSFUL_SURNAME);
        messageMap.put("successful_mail", MessageConstants.SUCCESSFUL_MAIL);
        messageMap.put("successful_class", MessageConstants.SUCCESSFUL_CLASS);
        messageMap.put("mail", MessageConstants.ENTER_MAIL);
        messageMap.put("surname", MessageConstants.ENTER_SURNAME);
        messageMap.put("name", MessageConstants.ENTER_NAME);
        messageMap.put("class", MessageConstants.ENTER_CLASS);
        messageMap.put("clas_bad", MessageConstants.UN_SUCCESSFUL_CLASS);
        messageMap.put("successfulReg", MessageConstants.SUCCESSFUL_REGISTRATION_COMMAND_RESPONSE);
        messageMap.put("ИЕНИМ", MessageConstants.INST_IENIM_COMMAND_RESPONSE);
        messageMap.put("РТФ", MessageConstants.INST_RTF_COMMAND_RESPONSE);
        messageMap.put("ХТИ", MessageConstants.INST_CHTI_COMMAND_RESPONSE);
    }
    /**
     * Возвращает сообщение для отправки пользователю
     */
    public String setTheText(String messageText) {
        String response = getMessageMap(messageText);
        if (response == null) {
            return MessageConstants.DEFAULT_RESPONSE;
        }
        return response;
    }

}

