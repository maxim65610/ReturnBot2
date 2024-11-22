package io.proj3ct.ReturnBot1.Command;

import io.proj3ct.ReturnBot1.departmentsAndTest.LogicForTestABI;
import io.proj3ct.ReturnBot1.baseClasses.MessageConstants;

import java.util.ArrayList;
import java.util.List;
/**
 * Команда для обработки запросов на тестирование и получения результатов.
 */
public class TestAbitCommand implements Command {
    private final LogicForTestABI logicForTestABI= new LogicForTestABI();
    /**
     * Выполняет команду для обработки тестирования или получения результата.
     *
     * @param userId ID пользователя, отправившего сообщение
     * @param messageText текст сообщения
     * @param flagForKeyboard флаг для отображения клавиатуры
     * @return Список строк с результатом тестирования или сообщением
     */
    @Override
    public List<String> execute(long userId, String messageText, boolean flagForKeyboard) {
        List<String> response = new ArrayList<>();
        if ("/test_abit".equals(messageText)) {
            logicForTestABI.worksWithTestABI(messageText, userId, "100");
            response.add(MessageConstants.TEST_ABIT_COMMAND_RESPONSE);
            response.add(messageText);
        }
        else if("/test_res".equals(messageText)) {
            response.add(logicForTestABI.getResult(userId));
        }
        else if(flagForKeyboard){
            if (!(logicForTestABI.getUserStatesForTest(userId).equals("0"))) {
                response = logicForTestABI.worksWithTestABI("", userId, messageText);
                if (logicForTestABI.getUserStatesForTest(userId).equals("awaiting_testABI_11")) {
                    logicForTestABI.removeUserStatesForTest(userId);
                }
            }
        }
        return response;
    }
}
