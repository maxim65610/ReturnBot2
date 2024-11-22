package io.proj3ct.ReturnBot1;

import io.proj3ct.ReturnBot1.Command.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Контроллер логики, который обрабатывает сообщения пользователей и выполняет команды.
 * Этот класс управляет списком команд и обрабатывает сообщения от пользователей. Каждое сообщение
 * проходит через серию команд, и если хотя бы одна команда возвращает результат, он немедленно
 * возвращается. Команды могут работать с пользователем и возвращать сообщения, а также
 * опции для клавиатуры.
 */
public class LogicController {
    private final String username = System.getenv("mail"); // Ваша почта
    private final String password = System.getenv("passwordForMail");
    private final EmailSender emailSender = new EmailSender(username, password);
    private final List<Command> commands = new ArrayList<>();

    /**
     * Конструктор по умолчанию, создающий стандартный набор команд.
     */
    public LogicController() {
        commands.add(new QuestionCommand(emailSender));
        commands.add(new AuthorizationCommand(emailSender));
        commands.add(new TestAbitCommand());
        commands.add(new UserDataChangeCommand(emailSender));
        commands.add(new UserDataDeleteCommand());
        commands.add(new UserInfoCommand());
        commands.add(new AuthorizationCommand(emailSender));
        commands.add(new WorkCommand());
        commands.add(new DepartmentsInfoCommand());
        commands.add(new DefaultCommand());
    }
    /**
     * Конструктор, принимающий на вход команды для инициализации(для тестов).
     *
     * @param testAbitCommand команда для тестирования
     * @param defaultCommand команда по умолчанию
     */
    public LogicController(TestAbitCommand testAbitCommand, DefaultCommand defaultCommand){
        commands.add(testAbitCommand);
        commands.add(defaultCommand);
    }
    /**
     * Обрабатывает обновления и генерирует ответные сообщения.
     * Этот метод перебирает все команды в списке и вызывает их метод execute, передавая
     * ID пользователя, текст сообщения и флаг для работы с клавиатурой. Если хотя бы одна команда
     * возвращает непустой список, метод немедленно завершает выполнение и возвращает этот список.
     * Если ни одна команда не возвращает результата, то в любом случае возвращается дефолтная команда.
     *
     * @param userId ID пользователя, отправившего сообщение
     * @param messageText текст сообщения от пользователя
     * @param flagForKeyboard флаг, указывающий, нужно ли возвращать опции для клавиатуры
     * @return Список строк, представляющих сообщения и опции для клавиатуры. Если команда
     *         не сработала, возвращается список с дефолтной командой.
     */
    public List<String> handleMessage(long userId, String messageText,boolean flagForKeyboard) {
        List<String> listForWorkWithKeyboardAndMessage = new ArrayList<>();
        for (Command command : commands) {
            listForWorkWithKeyboardAndMessage = command.execute(userId, messageText, flagForKeyboard);
            if (!listForWorkWithKeyboardAndMessage.isEmpty()) {
                return listForWorkWithKeyboardAndMessage;  // Если команда дала результат, сразу возвращаем его.
            }
        }
        return listForWorkWithKeyboardAndMessage;
    }
}
