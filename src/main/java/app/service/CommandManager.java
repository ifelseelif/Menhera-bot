package app.service;

import command.Command;
import command.NamedCommand;
import model.Message;
import model.MessageSourceType;

import java.util.Optional;


/**
 * @author Arthur Kupriyanov
 */
public class CommandManager {
    private static final CommandBox commandBox = new CommandBox();
    public static Command getCommand(String name){
        Optional<NamedCommand> commandOpt = commandBox.getCommands().stream().filter(c -> c.checkCommand(name)).findFirst();
        return commandOpt.isPresent()?commandOpt.get(): m -> new Message("Не понимаю вашей команды", m.getFrom(), null, MessageSourceType.VK);
    }
}
