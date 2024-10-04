package io.proj3ct.ReturnBot1;

/**
 * Класс логики бота
 */
public class LogicBrain {

    /**
     * Метод, который возвращает стартовый ответ бота
     */
    private String questionCommandReceived() {
        return "Привет, эта функция пока, что находиться в разработка(((. Если хотите начать работу напишите /work";
    }

    /**
     * Метод, который возвращает обычный ответ бота
     */
    private String defaultCommandReceived() {
        return "Привет, этот бот может помочь тебе понять куда ты хочешь поступить," +
                " пожалуйста пользуйся кнопками. Если у тебя остались вопросы, можешь воспользоваться командой /question." +
                " Если хотите начать работу напишите /work";
    }

    /**
     * Метод, который возвращает ответ бота, для главного ряда кнопок
     */
    private String workCommandReceived() {
        return  "Вот все институты у которых ты можешь посмотреть факультеты:";
    }

    /**
     * Метод, который возвращает ответ бота, для кнопок института ИЕНИМ
     */
    private String inst1CommandReceived() {
        return "Вот все факультеты которые есть в институте ИЕНИМ:";
    }

    /**
     * Метод, который возвращает ответ бота, для кнопок института РТФ
     */
    private String inst2CommandReceived() {
        return "Вот все факультеты которые есть в институте РТФ:";
    }

    /**
     * Метод, который возвращает ответ бота, для кнопок института ХТИ
     */
    private String inst3CommandReceived() {
        return "Вот все факультеты которые есть в институте ХТИ:";
    }

    /**
     * Метод, который реализует основную логику работы бота
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

