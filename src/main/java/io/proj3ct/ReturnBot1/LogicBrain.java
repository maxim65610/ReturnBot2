package io.proj3ct.ReturnBot1;

/**
 * класс логики бота
 */
public class LogicBrain {

    /**
     * метод, который возвращает ответ бота
     */
    private String startCommandReceived() {
        String answer = "Привет, я могу повторять за тобой.";
        return answer;
    }

    /**
     * метод, который реализует основную логику работы бота
     * @param messageText сообщение от пользователя
     */
    public String slogic(String messageText){
        switch (messageText) {
            case "/start":
                return startCommandReceived();
            case "/help":
                return startCommandReceived();
            default:
                 return "Вы ввели: " + messageText;
        }
    }
}
