package command;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Arthur Kupriyanov
 */
@Getter
@Setter
public abstract class NamedCommand implements Command {
    private boolean ignoreCase = true;

    public String getName(){
        return this.getClass().getSimpleName();
    }

    public List<String> getAliases(){
        List<String> arr = new ArrayList<>();
        arr.add(getName());
        return arr;
    };

    public boolean checkCommand(String command){
        if (!ignoreCase) return command.equals(getName()) || getAliases().contains(command);
        else return command.toLowerCase().equals(getName().toLowerCase()) || getAliases().stream().map(String::toLowerCase).collect(Collectors.toList()).contains(command.toLowerCase());
    }
}
