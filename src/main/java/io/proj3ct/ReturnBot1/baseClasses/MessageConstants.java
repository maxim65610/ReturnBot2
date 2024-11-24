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
     * Сообщение, которое отображается пользователю, когда его данные удалены.
     */
    public static final String DATA_DELETED = "Ваши данные успешно удалены";
    /**
     * Сообщение, запрашивающее ввод пароля.
     */
    public static final String PASSWORD_COMMAND_RESPONSE = "Введите пароль:";
    /**
     * Сообщение, отображаемое при неверном пароле.
     */
    public static final String BAD_PASSWORD_COMMAND_RESPONSE = "Пароль не верный, в доступе отказано";
    /**
     * Сообщение, отображаемое при успешном вводе пароля, запрашивающее текст рассылки.
     */
    public static final String SUCCESSFUL_PASSWORD_NEW_DISPATCH = "Пароль верный, введите текст рассылки:";
    /**
     * Сообщение, запрашивающее ввод даты отправки рассылки.
     */
    public static final String DISPATCH_TIME = "Введите дату отправки (в формате dd.MM.yyyy):";
    /**
     * Сообщение, отображаемое при некорректном вводе даты.
     */
    public static final String DISPATCH_TIME_BAD = "Вы ввели некорректные данные:";
    /**
     * Сообщение, запрашивающее ввод категории рассылки.
     */
    public static final String DISPATCH_CATEGORY = "Введите категорию (обычная / приемная комиссия):";
    /**
     * Сообщение, запрашивающее ввод факультета.
     */
    public static final String DISPATCH_DEPARTMENT = "Введите факультет:";
    /**
     * Сообщение, отображаемое при успешном добавлении рассылки.
     */
    public static final String NEW_DISPATCH_SUCCESSFUL = "Рассылка успешно добавлена";
    /**
     * Сообщение, подтверждающее подписку на рассылку.
     */
    public static final String DISPATCH_ON_COMMAND_RESPONSE = "Вы подписались на рассылку, бот будет отправлять Вам важную информацию";
    /**
     * Сообщение, подтверждающее отписку от рассылки.
     */
    public static final String DISPATCH_OFF_COMMAND_RESPONSE = "Вы отписались от рассылки";
    /**
     * Сообщение, запрашивающее выбор факультета для замены.
     */
    public static final String EDIT_DEPARTMENT_COMMAND_RESPONSE = "Выберете факультет для замены, напишите номер: ";
    /**
     * Сообщение, запрашивающее ввод нового факультета.
     */
    public static final String NEW_DEPARTMENT_COMMAND_RESPONSE = "Введите новый факультет";
    /**
     * Сообщение, подтверждающее изменение факультета.
     */
    public static final String CHANGE_DEPARTMENT_COMMAND_RESPONSE = "Факультет изменен";
    /**
     * Сообщение, запрашивающее ввод института для добавления факультета.
     */
    public static final String CORRECT_PASSWORD_AND_INSTITUTE_COMMAND_RESPONSE = """
            Пароль верный, введите институт, для которого хотите добавить факультет:""";
    /**
     * Сообщение, запрашивающее ввод текста для факультета.
     */
    public static final String TEXT_FOR_DEPARTMENT_COMMAND_RESPONSE = "Введите текст для этого факультета:";
    /**
     * Сообщение, запрашивающее ввод названия факультета.
     */
    public static final String NAME_DEPARTMENT_INSTITUTE_COMMAND_RESPONSE = "Введите название факультета:";
    /**
     * Сообщение, подтверждающее успешное добавление факультета.
     */
    public static final String SUCCESSFUL_ADD_DEPARTMENT_COMMAND_RESPONSE = "Факультет успешно добавлен";
    /**
     * Сообщение, запрашивающее выбор факультета для удаления.
     */
    public static final String CORRECT_PASSWORD_AND_INSTITUTE_DELETE_COMMAND_RESPONSE = """
            Пароль верный, выберите факультет, который хотите удалить:""";
    /**
     * Сообщение, подтверждающее успешное удаление факультета.
     */
    public static final String SUCCESSFUL_DELETE_DEPARTMENT_COMMAND_RESPONSE = "Факультет успешно удален";


}
