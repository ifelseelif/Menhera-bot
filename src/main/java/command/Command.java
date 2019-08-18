package command;

import model.Message;

/**
 * @author Arthur Kupriyanov
 */
public interface Command {
    Message execute(Message message);
}
