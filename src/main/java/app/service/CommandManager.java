package app.service;

import command.NamedCommand;
import java.util.Optional;


/**
 * @author Arthur Kupriyanov
 */
public class CommandManager {
    private static final CommandBox commandBox = new CommandBox();
    public static NamedCommand getCommand(String name){
        Optional<NamedCommand> commandOpt = commandBox.getCommands().stream().filter(c -> c.checkCommand(name)).findFirst();
        return commandOpt.orElse(null);
    }
}
