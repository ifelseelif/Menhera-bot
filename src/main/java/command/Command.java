package command;

import model.Message;

/**
 * @author Arthur Kupriyanov
 */
@FunctionalInterface
public interface Command {
    Message execute(Message message);
}
