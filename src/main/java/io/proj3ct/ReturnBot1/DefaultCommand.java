package io.proj3ct.ReturnBot1;

import java.util.ArrayList;
import java.util.List;

public class DefaultCommand implements Command {
    @Override
    public List<String> execute(long userId, String messageText) {
        List<String> response = new ArrayList<>();
        response.add(MessageConstants.DEFAULT_RESPONSE);
        response.add(messageText);
        return response;
    }
}
