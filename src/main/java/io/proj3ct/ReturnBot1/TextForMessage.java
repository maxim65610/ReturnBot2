package io.proj3ct.ReturnBot1;

/**
 * Возвращает текст для сообщения для некоторых команд для Telegram-бота.
 * Включает метод для обработки команд пользователя.
 */
public class TextForMessage {
    private final DataForMessageText dataForMessageText = new DataForMessageText();
    /**
     * Обрабатывает некоторых сообщения от пользователя и возвращает соответствующий ответ.
     * @param messageText сообщение от пользователя.
     * @return ответ бота на введенное сообщение.
     */
    public String handleMessage(String messageText) {
        String response = dataForMessageText.getMessageMap(messageText);
        if (response == null) {
            return MessageConstants.DEFAULT_RESPONSE;
        }
        return response;
    }

}

