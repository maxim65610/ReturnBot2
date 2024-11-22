package io.proj3ct.ReturnBot1.baseClasses;

/**
 * Константы для сообщений, используемых ботом.
 * Данный класс используется для хранения текстовых ответов бота.
 */
public class MessageConstants {
    /** Ответ бота по умолчанию на нераспознанные команды. */
    public static final String DEFAULT_RESPONSE = """
        Привет, этот бот может помочь тебе понять куда ты хочешь поступить, 
        пожалуйста пользуйся кнопками.
        Если у тебя остались вопросы, можешь воспользоваться командой /question.
        Если хотите начать работу, напишите /work.
        Также у тебя есть возможность пройти тест на то, какое направление вам больше подходит, 
        просто напиши /test_abit.
        Если вы хотите получить больший функционал бота воспользуйтесь /authorization.
        """;

    /** Ответ бота на команду, запрашивающую список институтов. */
    public static final String WORK_COMMAND_RESPONSE = "Вот все институты, у которых ты можешь посмотреть факультеты:";

    /** Ответ бота для кнопок института ИЕНИМ. */
    public static final String INST_IENIM_COMMAND_RESPONSE = "Вот все факультеты, которые есть в институте ИЕНИМ:";

    /** Ответ бота для кнопок института РТФ. */
    public static final String INST_RTF_COMMAND_RESPONSE = "Вот все факультеты, которые есть в институте РТФ:";

    /** Ответ бота для кнопок института ХТИ. */
    public static final String INST_CHTI_COMMAND_RESPONSE = "Вот все факультеты, которые есть в институте ХТИ:";

    /** Ответ бота для начала работы с тестированием по выбору факультета. */
    public static final String TEST_ABIT_COMMAND_RESPONSE = """
        Вы начали проходить тестирование по выбору факультета,
        выберите один предмет из этих трех:
        """;

    /** Ответ бота на запрос вопроса. */
    public static final String QUESTION_COMMAND_RESPONSE = "Пожалуйста, отправьте свой вопрос:";

    /** Ответ бота на некорректную почту. */
    public static final String NOT_CORRECT_MAIL_COMMAND_RESPONSE = "Адрес электронной почты был указан неправильно, отправьте его ещё раз.";

    /** Ответ бота на то, что пользователь прошел тест. */
    public static final String END_TEST_ABI_COMMAND_RESPONSE = """
        Поздравляю, вы прошли тест. 
        Чтобы узнать результат напишите /test_res.
        """;

    /** Ответ бота на то, какой результат у пользователя после прохождения теста. */
    public static final String RESULT_TEST_ABI_COMMAND_RESPONSE = "Вам больше всего подходит факультет:";

    /** Ответ бота на начало авторизации. */
    public static final String REGISTRATION_COMMAND_RESPONSE = """
        Здравствуйте, начинаем.
        Всего будет 4 пункта, которые вы должны указать: имя, фамилию, класс, почту. 
        Введите имя:
        """;

    /** Ответ бота на успешную авторизацию. */
    public static final String SUCCESSFUL_REGISTRATION_COMMAND_RESPONSE = """
        Авторизация окончена успешно.
        Если хотите проверить данные воспользуйтесь /user_info.
        Если хотите удалить данные воспользуйтесь /user_data_dell.
        Если хотите поменять данные воспользуйтесь /user_data_change.
        """;

    /** Сообщение о некорректном классе. */
    public static final String UN_SUCCESSFUL_CLASS = "Вы ввели некорректный класс, введите класс заново.";

    /** Ответ бота на замену данных. */
    public static final String CHANGEDATA_COMMAND_RESPONSE = """
        Выберите данные, которые хотите поменять:
        /user_data_change_name - поменять имя
        /user_data_change_surname - поменять фамилию
        /user_data_change_class - поменять класс
        /user_data_change_mail - поменять почту.
        """;

    /** Ответ бота на то, что пользователь уже прошел регистрацию. */
    public static final String AUTHORISATION_COMMAND_RESPONSE = """
        Вы уже зарегистрированы!!!
        Если хотите проверить данные воспользуйтесь /user_info.
        Если хотите удалить данные воспользуйтесь /user_data_dell.
        Если хотите поменять данные воспользуйтесь /user_data_change.
        """;

    /** Сообщение с просьбой ввести фамилию. */
    public static final String ENTER_SURNAME = "Введите фамилию:";

    /** Сообщение с просьбой ввести имя. */
    public static final String ENTER_NAME = "Введите имя:";

    /** Сообщение с просьбой ввести класс. */
    public static final String ENTER_CLASS = "Введите класс:";

    /** Сообщение с просьбой ввести адрес электронной почты. */
    public static final String ENTER_MAIL = "Введите почту:";

    /** Сообщение, информирующее пользователя о том, что имя успешно изменено. */
    public static final String SUCCESSFUL_NAME = "Имя успешно изменено.";

    /** Сообщение, информирующее пользователя о том, что фамилия успешно изменена. */
    public static final String SUCCESSFUL_SURNAME = "Фамилия успешно изменена.";

    /** Сообщение, информирующее пользователя о том, что почта успешно изменена. */
    public static final String SUCCESSFUL_MAIL = "Почта успешно изменена.";

    /** Сообщение, информирующее пользователя о том, что класс успешно изменен. */
    public static final String SUCCESSFUL_CLASS = "Класс успешно изменен.";
    /**
     * Сообщение, которое отображается пользователю, если он не прошел регистрацию.
     */
    public static final String NO_REGISTRATION = "Вы не прошли регистрацию";
    /**
     * Сообщение, которое отображается пользователю, когда его вопрос успешно отправлен.
     */
    public static final String MAIL_SEND = "Ваш вопрос отправлен";
    /**
     * Сообщение, которое отображается пользователю, если он пытается использовать функцию,
     * которая недоступна до завершения регистрации.
     */
    public static final String NOT_AVAILABLE = "Эта функция недоступна, пока вы не зарегистрируетесь";
    /**
     * Сообщение, которое отображается пользователю, когда его данные удаленны.
     */
    public static final String DATA_DELETED = "Ваши данные успешно удалены";

    public static final String NEW_DISPATCH = "Введите пароль:";

    public static final String BAD_PASSWORD_NEW_DISPATCH = "Пароль не верный, в доступе отказано";

    public static final String SUCCESSFUL_PASSWORD_NEW_DISPATCH = "Пароль верный, введите текст рассылки:";

    public static final String DISPATCH_TIME = "Введите дату отправки (в формате dd.MM.yyyy):";

    public static final String DISPATCH_TIME_BAD = "Вы ввели некорректные данные:";

    public static final String DISPATCH_CATEGORY = "Введите категорию (обычная / приемная комиссия):";

    public static final String DISPATCH_DEPARTMENT= "Введите факультет:";

    public static final String NEW_DISPATCH_SUCCESSFUL= "Рассылка успешно добавлена";
    public static final String DISPATCH_ON_COMMAND_RESPONSE = "Вы подписались на рассылку, бот будет отправлять Вам важную информацию";
    public static final String DISPATCH_OFF_COMMAND_RESPONSE = "Вы отписались от рассылки";

}
