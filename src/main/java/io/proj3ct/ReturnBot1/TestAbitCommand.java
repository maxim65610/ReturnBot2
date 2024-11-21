package io.proj3ct.ReturnBot1;

import java.util.ArrayList;
import java.util.List;

public class TestAbitCommand implements Command {
    private final LogicForTestABI logicForTestABI= new LogicForTestABI();

    @Override
    public List<String> execute(long userId, String messageText) {
        List<String> response = new ArrayList<>();
        if ("/testAbit".equals(messageText)) {
            logicForTestABI.worksWithTestABI(messageText, userId, "100");
            response.add(MessageConstants.TEST_ABIT_COMMAND_RESPONSE);
            response.add(messageText);
        }
        return response;
    }
}
