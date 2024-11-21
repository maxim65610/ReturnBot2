package io.proj3ct.ReturnBot1;

import java.util.ArrayList;
import java.util.List;

public class TestResCommand implements Command {
    private final LogicForTestABI logicForTestABI = new LogicForTestABI();

    @Override
    public List<String> execute(long userId, String messageText) {
        List<String> response = new ArrayList<>();
        if ("/testres".equals(messageText)) {
            response.add(logicForTestABI.getResult(userId));
        }
        return response;
    }
}
