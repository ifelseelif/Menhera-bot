package command;

import annotations.Command;
import model.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Arthur Kupriyanov
 */
@Command
public class Bye extends NamedCommand{
    @Override
    public String getName() {
        return "bye";
    }

    @Override
    public Message execute(Message message) {

        Message newMessage = Message.reverseMessage(message);
        newMessage.setMessage("Bye bye!");
        return newMessage;
    }

    @Override
    public List<String> getAliases() {
        List<String> list = new ArrayList<>();
        list.add("away");
        list.add("good night");
        return list;
    }
}
