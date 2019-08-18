package command;

import model.Message;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Arthur Kupriyanov
 */
class NamedCommandTest {
    private NamedCommand c = new NamedCommand() {
        @Override
        public Message execute(Message message) {
            return null;
        }

        @Override
        public String getName() {
            return "test command";
        }

        @Override
        public List<String> getAliases() {
            List<String> aliases =new ArrayList<>();
            aliases.add("command");
            aliases.add("test");

            return aliases;
        }
    };

    @Test
    void checkCommand() {
        c.setIgnoreCase(true);
        assertTrue(c.checkCommand("test command"));
        assertTrue(c.checkCommand("teSt comMand"));
        assertTrue(c.checkCommand("command"));
        assertTrue(c.checkCommand("test"));
        assertFalse(c.checkCommand("another command"));
        assertFalse(c.checkCommand("command test"));
    }

    @Test
    void checkCommand_notIgnoreCase(){
        c.setIgnoreCase(false);
        assertTrue(c.checkCommand("test command"));
        assertTrue(c.checkCommand("command"));
        assertFalse(c.checkCommand("teSt comMand"));
        assertFalse(c.checkCommand("command test"));
        c.setIgnoreCase(true);
    }
}