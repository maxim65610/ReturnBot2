package io.proj3ct.ReturnBot1;

/**
 * Класс, отвечающий за основную логику работы Telegram-бота.
 * Включает методы для обработки команд пользователя и отправки электронных писем.
 */
public class messageLogic {

    /**
     * Метод, который возвращает стандартный ответ бота на нераспознанные команды.
     * @return сообщение с информацией о функционале бота.
     */
    private String defaultCommandReceived() {
        return "Привет, этот бот может помочь тебе понять куда ты хочешь поступить," +
                " пожалуйста пользуйся кнопками. Если у тебя остались вопросы, можешь воспользоваться командой /question." +
                " Если хотите начать работу напишите /work";
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


    /**
     * Метод, который возвращает ответ бота для кнопок института ИЕНИМ.
     * @return сообщение со списком факультетов института ИЕНИМ.
     */
    private String testAbitCommandReceived() {
        return "Вот все факультеты которые есть в институте ИЕНИМ:";
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
