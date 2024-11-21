package io.proj3ct.ReturnBot1;

import java.util.List;

public interface Command {
    List<String> execute(long userId, String messageText);
}
