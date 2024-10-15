package io.proj3ct.ReturnBot1;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

/**
 * Класс, отвечающий за основную логику работы Telegram-бота.
 * Включает методы для обработки команд пользователя и отправки электронных писем.
 */
public class MessageLogic {

    /**
     * Метод, который возвращает стандартный ответ бота на нераспознанные команды.
     * @return сообщение с информацией о функционале бота.
     */
    private String defaultCommandReceived() {
        return "Привет, этот бот может помочь тебе понять куда ты хочешь поступить," +
                " пожалуйста пользуйся кнопками. Если у тебя остались вопросы, можешь воспользоваться командой /question." +
                " Если хотите начать работу напишите /work. Также у тебя есть возможность пройти тест на то, какое направление " +
                "вам больше подходит, просто напишите /test";
    }

    /**
     * Метод, который возвращает ответ бота для команды, запрашивающей список институтов.
     * @return сообщение со списком институтов.
     */
    private String workCommandReceived() {
        return "Вот все институты у которых ты можешь посмотреть факультеты:";
    }

    /**
     * Метод, который возвращает ответ бота для кнопок института ИЕНИМ.
     * @return сообщение со списком факультетов института ИЕНИМ.
     */
    private String inst1CommandReceived() {
        return "Вот все факультеты которые есть в институте ИЕНИМ:";
    }

    /**
     * Метод, который возвращает ответ бота для кнопок института РТФ.
     * @return сообщение со списком факультетов института РТФ.
     */
    private String inst2CommandReceived() {
        return "Вот все факультеты которые есть в институте РТФ:";
    }

    /**
     * Метод, который возвращает ответ бота для кнопок института ХТИ.
     * @return сообщение со списком факультетов института ХТИ.
     */
    private String inst3CommandReceived() {
        return "Вот все факультеты которые есть в институте ХТИ:";
    }


    private String testAbitCommandReceived() {
        return "Вы начали проходить тестирование по выбору факультета, выберите один предмет из этих трех:";
    }



    private EmailSender emailSender;

    /**
     * Метод для установки объекта EmailSender.
     * @param emailSender объект EmailSender, отвечающий за отправку электронных писем.
     */
    public void setEmailSender(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    /**
     * Метод для обработки ввода электронной почты от пользователя.
     * Проверяет корректность формата почты и возвращает соответствующее сообщение.
     * @param email введенный пользователем адрес электронной почты.
     * @return сообщение о корректности введенного адреса электронной почты.
     */
    public String handleEmailInput(String email) {
        if(emailSender.isValidEmail(email)){
            return "Почта указана корректно, напишите ваш вопрос";
        }
        return "Адрес электронной почты был указан неправильно отправьте его ещё раз";
    }

    /**
     * Метод для отправки вопроса на указанный адрес электронной почты.
     * @param mailMessage адрес электронной почты получателя.
     * @param question текст вопроса, который необходимо отправить.
     */
    public void sendMail(String mailMessage, String question) {
        emailSender.sendEmail(emailSender.getUsername(), "Вопрос от абитуриента " + mailMessage, question);
    }

    /**
     * Метод, который возвращает ответ бота, запрашивающий электронную почту у пользователя.
     * @return сообщение с просьбой отправить электронную почту.
     */
    private String questionCommandReceived() {
        return "Пожалуйста, отправьте свою почту";
    }

    /**
     * Обрабатывает ввод пользователя, связанный с электронной почтой и вопросами.
     *
     * @param update      Обновлениe.
     * @param messageText Сообщение, введенное пользователем.
     * @param userId      ID пользователя.
     * @param currentState Текущее состояние пользователя.
     * @param userStates  Состояние пользователя.
     * @param userMails Mail пользователя.
     */
    public  String worksWithMail(Update update, String messageText, Long userId, String currentState, Map<Long, String> userStates, Map<Long, String> userMails) {
        if ("/question".equals(messageText)) {
            userStates.put(userId, "awaiting_email");;
        } else if ("awaiting_email".equals(currentState)) {
            String mailUser = update.getMessage().getText();
            String anwserhandleEmailInput = handleEmailInput(mailUser);
            if(anwserhandleEmailInput.equals("Почта указана корректно, напишите ваш вопрос")) {
                userMails.put(userId, mailUser);
                userStates.put(userId, "awaiting_question");
            } else {
                userMails.remove(userId, mailUser);
            }
            return  anwserhandleEmailInput;

        } else if ("awaiting_question".equals(currentState)) {
            String question = update.getMessage().getText();
            String mailUser = userMails.get(userId);
            sendMail(mailUser, question);
            userStates.remove(userId);
            userMails.remove(userId);
            return "Ваш вопрос отправлен";
        }
        return questionCommandReceived();
    }
    private String[] resultsTestAbi = new String[10];
    RetrieveData retrieveData = new RetrieveData();

    public String[] worksWithTestAPI(String messageText, Long userId,Map<Long, String> userStatesforTest, String data) {
        String currentState = userStatesforTest.get(userId);
        String[] valuesBD = new String[7];
        if(messageText.equals("/test") && userStatesforTest.isEmpty()){
            userStatesforTest.put(userId, "awaiting_testABI_1");
        }
        else if("awaiting_testABI_1".equals(currentState)){
            if(data.equals("100")){
                valuesBD = arrayBDforTestABI("101");

            }
            else if(data.equals("200"))
            {
                valuesBD =arrayBDforTestABI("201");

            }
            else if(data.equals("300")){
                valuesBD = arrayBDforTestABI("301");

            }
            userStatesforTest.remove(userId);
            userStatesforTest.put(userId, "awaiting_testABI_2");
        }
        else if("awaiting_testABI_2".equals(currentState)){
            valuesBD = arrayBDforTestABI(data);
            userStatesforTest.remove(userId);
            userStatesforTest.put(userId, "awaiting_testABI_3");
        }
        else if("awaiting_testABI_3".equals(currentState)){
            valuesBD = arrayBDforTestABI(data);
            userStatesforTest.remove(userId);
            userStatesforTest.put(userId, "awaiting_testABI_4");
        }
        else if("awaiting_testABI_4".equals(currentState)){
            valuesBD = arrayBDforTestABI(data);
            userStatesforTest.remove(userId);
            userStatesforTest.put(userId, "awaiting_testABI_5");
        }
        else if("awaiting_testABI_5".equals(currentState)){
            valuesBD = arrayBDforTestABI(data);
            userStatesforTest.remove(userId);
            userStatesforTest.put(userId, "awaiting_testABI_6");
        }
        else if("awaiting_testABI_6".equals(currentState)){
            valuesBD = arrayBDforTestABI(data);
            userStatesforTest.remove(userId);
            userStatesforTest.put(userId, "awaiting_testABI_7");
        }
        else if("awaiting_testABI_7".equals(currentState)){
            valuesBD = arrayBDforTestABI(data);
            userStatesforTest.remove(userId);
            userStatesforTest.put(userId, "awaiting_testABI_8");
        }
        else if("awaiting_testABI_8".equals(currentState)) {
            valuesBD = arrayBDforTestABI(data);
            userStatesforTest.remove(userId);
            userStatesforTest.put(userId, "awaiting_testABI_9");
        }
        else if("awaiting_testABI_9".equals(currentState)){
            valuesBD = arrayBDforTestABI(data);
            userStatesforTest.remove(userId);
            userStatesforTest.put(userId, "awaiting_testABI_10");
        }


        return valuesBD;
    }
    public String[] arrayBDforTestABI(String data){
        String[] valuesBD = new String[7];
        int intData = Integer.parseInt(data);
        valuesBD[0] = retrieveData.getDataById(intData, "question");
        valuesBD[1] = retrieveData.getDataById(intData, "answer1");
        valuesBD[2] = retrieveData.getDataById(intData, "answer2");
        valuesBD[3] = retrieveData.getDataById(intData, "answer3");

        int cash = intData + 1;
        valuesBD[4] = String.valueOf(cash);
        valuesBD[5] = retrieveData.getDataById(intData, "cash1");
        valuesBD[6] = retrieveData.getDataById(intData, "cash2");
        return valuesBD;
    }
    /**
     * Метод, который реализует основную логику работы бота.
     * Обрабатывает сообщения от пользователя и возвращает соответствующий ответ.
     * @param messageText сообщение от пользователя.
     * @return ответ бота на введенное сообщение.
     */
    public String slogic(String messageText) {
        switch (messageText) {
            case "/start":
            case "/help":
                return defaultCommandReceived();
            case "/question":
                return questionCommandReceived();
            case "/test":
                return testAbitCommandReceived();
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

