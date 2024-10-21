package io.proj3ct.ReturnBot1;


/**
 * Класс, отвечающий за основную логику работы Telegram-бота.
 * Включает методы для обработки команд пользователя.
 */
public class MessageLogic {

    /**
     * Метод, который возвращает стандартный ответ бота на нераспознанные команды.
     * @return сообщение с информацией о функционале бота.
     */
    private String defaultCommandReceived() {
        return MessageConstants.DEFAULT_RESPONSE;
    }

    /**
     * Метод, который возвращает ответ бота для команды, запрашивающей список институтов.
     * @return сообщение со списком институтов.
     */
    private String workCommandReceived() {
        return MessageConstants.WORK_COMMAND_RESPONSE;
    }

    /**
     * Метод, который возвращает ответ бота для кнопок института ИЕНИМ.
     * @return сообщение со списком факультетов института ИЕНИМ.
     */
    private String instIENIMCommandReceived() {
        return MessageConstants.INST_IENIM_COMMAND_RESPONSE;
    }

    /**
     * Метод, который возвращает ответ бота для кнопок института РТФ.
     * @return сообщение со списком факультетов института РТФ.
     */
    private String instRTFCommandReceived() {
        return MessageConstants.INST_RTF_COMMAND_RESPONSE;
    }

    /**
     * Метод, который возвращает ответ бота для кнопок института ХТИ.
     * @return сообщение со списком факультетов института ХТИ.
     */
    private String instCHTICommandReceived() {
        return MessageConstants.INST_CHTI_COMMAND_RESPONSE;
    }

    /**
     * Метод, который возвращает ответ бота для начала работы с testAbit.
     * @return сообщение для начала работы с testAbit.
     */
    private String testAbitCommandReceived() {
        return MessageConstants.TEST_ABIT_COMMAND_RESPONSE;
    }

    /**
     * Метод, который реализует основную логику работы бота.
     * Обрабатывает сообщения от пользователя и возвращает соответствующий ответ.
     * @param messageText сообщение от пользователя.
     * @return ответ бота на введенное сообщение.
     */
    public String handleMessage(String messageText) {
        switch (messageText) {
            case "/testAbit":
                return testAbitCommandReceived();
            case "/work":
                return workCommandReceived();
            case "ИЕНИМ":
                return instIENIMCommandReceived();
            case "РТФ":
                return instRTFCommandReceived();
            case "ХТИ":
                return instCHTICommandReceived();
            default:
                return defaultCommandReceived();
        }
    }

}

