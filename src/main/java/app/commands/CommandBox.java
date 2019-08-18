package app.commands;

import annotations.Command;
import command.NamedCommand;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Arthur Kupriyanov
 */
class CommandBox {
    private final static Logger log = Logger.getLogger(CommandBox.class);
    private static Set<command.NamedCommand> commands;
    static {
        commands = new HashSet<>();
        ClassPathScanningCandidateComponentProvider scanner =
                new ClassPathScanningCandidateComponentProvider(false);

        scanner.addIncludeFilter(new AnnotationTypeFilter(Command.class));

        log.info("SEARCH ANNOTATED COMMANDS ...");

        for (BeanDefinition bd : scanner.findCandidateComponents("command")) {
            log.info("Found annotated command : " + bd.getBeanClassName());
            try {
                commands.add((NamedCommand) Class.forName(bd.getBeanClassName()).getConstructor().newInstance());
                log.info(bd.getBeanClassName() + " added to CommandBox");
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                log.error(e.getMessage(), e);
            } catch (InstantiationException e) {
                log.error("Instantiate error : " + e.getMessage(), e);
            } catch (ClassCastException e){
                log.error("Class cast exception : " + e.getMessage(), e);
                log.error("Please make your command extends NamedCommand");
            } catch (ClassNotFoundException e){
                log.warn("Could not resolve class object for bean definition", e);
            }
        }
    }
    public Set<NamedCommand> getCommands(){
        return commands;
    }
}
