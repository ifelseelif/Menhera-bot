package app.service;

import command.NamedCommand;
import model.Message;

import java.util.Optional;


/**
 * @author Arthur Kupriyanov
 */
public class CommandManager {
    private static final CommandBox commandBox = new CommandBox();
    public static NamedCommand getCommand(String name){
        Optional<NamedCommand> commandOpt = commandBox.getCommands().stream().filter(c -> c.checkCommand(name)).findFirst();
        return commandOpt.orElse(new UnknownCommand());
    }

    private static final class UnknownCommand extends NamedCommand{

        @Override
        public Message execute(Message message) {
            Message msg = Message.reverseMessage(message);
            msg.setMessage("Не понимаю вашей команды");
            return msg;
        }
    }
}
