package command;

import annotations.Command;
import model.Message;

/**
 * @author Arthur Kupriyanov
 */
@Command
public class Hello extends NamedCommand {
    @Override
    public Message execute(Message message) {
        return new Message("Hey there!", message.getTo(), message.getFrom(), null);
    }

    @Override
    public String getName() {
        return "hello";
    }
}
