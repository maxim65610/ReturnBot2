package io.proj3ct.ReturnBot1;


/**
 * класс логики бота
 */
public class LogicBrain {

    /**
     * метод, который возвращает ответ бота
     */
    private String startCommandReceived() {
        String answer = "Привет, этот бот может помочь тебе понять куда ты хочешь поступить. Если хотите начать работу напишите /work";
        return answer;
    }
    private String defaultCommandReceived() {
        String answer = "Привет, этот бот может помочь тебе понять куда ты хочешь поступить," +
                " пожалуйста пользуйся кнопками. Если у тебя остались вопросы, можешь воспользоваться командой /question." +
                " Если хотите начать работу напишите /work";
        return answer;
    }

    private String workCommandReceived() {
        String answer = "Вот все институты у которых ты можешь посмотреть факультеты:";
        return answer;
    }
    private String instCommandReceived() {
        String answer = "Вот все факультеты которые есть в этом институте:";
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
            case "/work":
                return workCommandReceived();
            case "ИЕНИМ":
                return instCommandReceived();
            case "РТФ":
                return instCommandReceived();
            case "ХТИ":
                return instCommandReceived();
            default:
                return defaultCommandReceived();
        }
    }
}

