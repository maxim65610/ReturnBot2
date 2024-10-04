package io.proj3ct.ReturnBot1;

/**
 * класс логики бота
 */
public class LogicBrain {

    /**
     * метод, который возвращает ответ бота
     */

    private EmailSender emailSender;
    // Метод для установки EmailSender
    public void setEmailSender(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    public String handleEmailInput(String email) {
        // Здесь вы можете добавить проверку формата email и отправку сообщения
        // Сбрасываем состояние после обработки

        if(emailSender.isValidEmail(email)){
            return "Почта указана корректно, напишите ваш вопрос";
        }
        return "Адрес электронной почты был указан неправильно отправьте его ещё раз";

    }
    public void sendMail(String mailMessage, String question){
        emailSender.sendEmail(emailSender.getUsername(), "Вопрос от абитуриента " + mailMessage, question);

    }
    private String questionCommandReceived() {
        String answer = "Пожалуйста, отправьте свою почту";
        // Замените на адрес получателя и тему/текст сообщения
        return answer;
    }
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
            case "/question":
                return questionCommandReceived();
            default:
                 return "Вы ввели: " + messageText;
        }
    }
}
