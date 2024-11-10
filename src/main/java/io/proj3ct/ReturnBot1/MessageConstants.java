package io.proj3ct.ReturnBot1;

/**
 * Константы для сообщений, используемых ботом.
 * Данный класс используется для хранения текстовых ответов бота
 */
public class MessageConstants {
    /**
     * Ответ бота по умолчанию на нераспознанные команды.
     */
    public static final String DEFAULT_RESPONSE = "Привет, этот бот может помочь тебе понять куда ты хочешь поступить, " +
            "пожалуйста пользуйся кнопками.\nЕсли у тебя остались вопросы, можешь воспользоваться " +
            "командой /question.\nЕсли хотите начать работу, напишите /work.\nТакже у тебя есть возможность " +
            "пройти тест на то, какое направление вам больше подходит, просто напиши /testAbit." +
            "\nЕсли вы хотите получить больший функционал бота воспользуйтесь /authorization";;
    /**
     * Ответ бота на команду, запрашивающую список институтов.
     */
    public static final String WORK_COMMAND_RESPONSE = "Вот все институты, у которых ты можешь посмотреть факультеты:";
    /**
     * Ответ бота для кнопок института ИЕНИМ.
     */
    public static final String INST_IENIM_COMMAND_RESPONSE = "Вот все факультеты, которые есть в институте ИЕНИМ:";
    /**
     * Ответ бота для кнопок института РТФ.
     */
    public static final String INST_RTF_COMMAND_RESPONSE = "Вот все факультеты, которые есть в институте РТФ:";
    /**
     * Ответ бота для кнопок института ХТИ.
     */
    public static final String INST_CHTI_COMMAND_RESPONSE = "Вот все факультеты, которые есть в институте ХТИ:";
    /**
     * Ответ бота для начала работы с тестированием по выбору факультета.
     */
    public static final String TEST_ABIT_COMMAND_RESPONSE = "Вы начали проходить тестирование по выбору факультета," +
            " выберите один предмет из этих трех:";
    /**
     * Ответ бота на запрос вопроса.
     */
    public static final String QUESTION_COMMAND_RESPONSE = "Пожалуйста, отправьте свой вопрос:";
    /**
     * Ответ бота на корректную потчу
     */
    public static final String CORRECT_MAIL_COMMAND_RESPONSE = "Почта указана корректно, напишите ваш вопрос";
    /**
     * Ответ бота на некорректную потчу
     */
    public static final String NOT_CORRECT_MAIL_COMMAND_RESPONSE = "Адрес электронной почты был указан неправильно " +
            "отправьте его ещё раз";
    /**
     * Ответ бота на то, что вопрос пользователя отправлен на почту
     */
    public static final String QUESTION_HAS_BEEN_SEND_COMMAND_RESPONSE = "Ваш вопрос отправлен";
    /**
     * Ответ бота на то, что пользователь прошел тест
     */
    public static final String END_TEST_ABI_COMMAND_RESPONSE = "Поздравляю, вы прошли тест. " +
            "Чтобы узнать результат напишите /testres" ;
    /**
     * Ответ бота на то, какой результат у пользователя после прождения теста
     */
    public static final String RESULT_TEST_ABI_COMMAND_RESPONSE = "Вам больше всего подходит факультет: ";
    /**
     * Ответ бота на начало авторизации.
     */
    public static final String REGISTRATION_COMMAND_RESPONSE = "Здравствуйте, начинаем." +
            "\nВсего будет 4 пункта, которые " +
            "вы должны указать: имя, фамилию, класс, почту. " +
            "\nВведите имя:\n";
    /**
     * Ответ бота на успешную авторизации.
     */
    public static final String SUCCESSFUL_REGISTRATION_COMMAND_RESPONSE ="Авторизация окончена успешно." +
            "\nЕсли хотите проверить данные воспользуйтесь /userInfo" +
            "\nЕсли хотите удалить данные воспользуйтесь /userDataDell"+
            "\nЕсли хотите поменять данные воспользуйтесь /userDataChange";

    public static final String UN_SUCCESSFUL_CLASS = "Вы ввели некорректный класс, введите класс заново";
    /**
     * Ответ бота на замену даниных.
     */
    public static final String CHANGEDATA_COMMAND_RESPONSE = "Выберите данные, которые хотите поменять:" +
            "\n/userDataChangeName - поменять имя" +
            "\n/userDataChangeSurname - поменять фамилию" +
            "\n/userDataChangeClass - поменять класс" +
            "\n/userDataChangeMail - поменять почту";
    /**
     * Ответ бота на то, что пользователь уже прошел регистрацию.
     */
    public static final String AUTHORISATION_COMMAND_RESPONSE ="Вы уже зарегистрированы!!!" +
            "\nЕсли хотите проверить данные воспользуйтесь /userInfo" +
            "\nЕсли хотите удалить данные воспользуйтесь /userDataDell" +
            "\nЕсли хотите поменять данные воспользуйтесь /userDataChange";
    /**
     * Сообщение, информирующее пользователя о том, что его данные успешно удалены.
     */
    public static final String DEL_DATA_COMMAND_RESPONSE = "Ваши данные успешно удалены";
    /**
     * Сообщение с просьбой ввести фамилию.
     */
    public static final String ENTER_SURNAME = "Введите фамилию:";
    /**
     * Сообщение с просьбой ввести имя.
     */
    public static final String ENTER_NAME = "Введите имя:";
    /**
     * Сообщение с просьбой ввести класс.
     */
    public static final String ENTER_CLASS = "Введите класс:";
    /**
     * Сообщение с просьбой ввести адрес электронной почты.
     */
    public static final String ENTER_MAIL = "Введите почту:";

    /**
     * Сообщение, информирующее пользователя о том, что имя успешно изменено.
     */
    public static final String SUCCESSFUL_NAME = "Имя успешно изменено";

    /**
     * Сообщение, информирующее пользователя о том, что фамилия успешно изменена.
     */
    public static final String SUCCESSFUL_SURNAME = "Фамилия успешно изменена";

    /**
     * Сообщение, информирующее пользователя о том, что почта успешно изменена.
     */
    public static final String SUCCESSFUL_MAIL = "Почта успешно изменена";

    /**
     * Сообщение, информирующее пользователя о том, что класс успешно изменен.
     */
    public static final String SUCCESSFUL_CLASS = "Класс успешно изменен";
}



