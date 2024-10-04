package io.proj3ct.ReturnBot1;


public class LogicBrain {

    /**
     * метод, который возвращает ответ бота
     */
    private String questionCommandReceived() {
        String answer = "Привет, эта функция пока, что находиться в разработка(((. Если хотите начать работу напишите /work";
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
    private String inst1CommandReceived() {
        String answer = "Вот все факультеты которые есть в институте ИЕНИМ:";
        return answer;
    }
    private String inst2CommandReceived() {
        String answer = "Вот все факультеты которые есть в институте РТФ:";
        return answer;
    }
    private String inst3CommandReceived() {
        String answer = "Вот все факультеты которые есть в институте ХТИ:";
        return answer;
    }

    /**
     * метод, который реализует основную логику работы бота
     * @param messageText сообщение от пользователя
     */
    public String slogic(String messageText){
        switch (messageText) {
            case "/start":
                return defaultCommandReceived();
            case "/help":
                return defaultCommandReceived();
            case "/question":
                return questionCommandReceived();
            case "/work":
                return workCommandReceived();
            case "ИЕНИМ":
                return inst1CommandReceived();
            case "РТФ":
                return inst2CommandReceived();
            case "ХТИ":
                return inst3CommandReceived();
            default:
                return defaultCommandReceived();
        }
    }
}
