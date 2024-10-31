package io.proj3ct.ReturnBot1;

/**
 * Возвращает текст для сообщения для некоторых команд для Telegram-бота.
 * Включает метод для обработки команд пользователя.
 */
public class TextForMessage {

    /**
     * Обрабатывает некоторых сообщения от пользователя и возвращает соответствующий ответ.
     * @param messageText сообщение от пользователя.
     * @return ответ бота на введенное сообщение.
     */
    public String handleMessage(String messageText) {
        switch (messageText) {
            case "/testAbit":
                return MessageConstants.TEST_ABIT_COMMAND_RESPONSE;
            case "/work":
                return MessageConstants.WORK_COMMAND_RESPONSE;
            case "/question":
                return MessageConstants.QUESTION_COMMAND_RESPONSE;
            case "correctMail":
                return MessageConstants.CORRECT_MAIL_COMMAND_RESPONSE;
            case "notСorrectMail":
                return MessageConstants.NOT_CORRECT_MAIL_COMMAND_RESPONSE;
            case "questionHasBeenSend":
                return MessageConstants.QUESTION_HAS_BEEN_SEND_COMMAND_RESPONSE;
            case "userPassedTest":
                return MessageConstants.END_TEST_ABI_COMMAND_RESPONSE;
            case "resultTestABI":
                return MessageConstants.RESULT_TEST_ABI_COMMAND_RESPONSE;
            case "ИЕНИМ":
                return MessageConstants.INST_IENIM_COMMAND_RESPONSE;
            case "РТФ":
                return MessageConstants.INST_RTF_COMMAND_RESPONSE;
            case "ХТИ":
                return MessageConstants.INST_CHTI_COMMAND_RESPONSE;
            default:
                return MessageConstants.DEFAULT_RESPONSE;
        }
    }
}

