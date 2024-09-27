package io.proj3ct.ReturnBot1;

public class LogicBrain {

    /**
     * Обрабатывает команду /start, полученную от пользователя.
     *

     */
    private String startCommandReceived() {
        String answer = "Привет, я могу повторять за тобой.";
        return answer;
    }

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
