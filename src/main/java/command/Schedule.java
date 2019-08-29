package command;

import annotations.Command;
import lombok.ToString;
import model.Message;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Arthur Kupriyanov
 */
@Command
public class Schedule extends NamedCommand {
    @Override
    public String getName() {
        return "расписание";
    }

    @Override
        public Message execute(Message message) {
        message.toLowerCase();
        message.setMessage(message.getMessage().replace(" ",""));
        Pattern pattern = Pattern.compile("расписание(у*группы)*.+");
        Matcher m = pattern.matcher(message.getMessage());
        String groupAndConfig = "";
        if (m.find()){
            groupAndConfig = message.getMessage().replaceAll("расписание(у*группы)*", "");
        }

        Config config = getConfig(groupAndConfig);

        // TODO: Write schedule parser
        System.out.println(config);
        Message msg = getSchedule(config);
        msg.setTo(message.getFrom());
        return msg;
    }

    private Message getSchedule(Config config) {
        return new Message(config.toString(), null, null , null);
    }

    private Config getConfig(String context){
        Pattern pattern = Pattern.compile("(((на)*завтра)|((на)*послезавтра)|(нанеделю))+");
        Matcher m = pattern.matcher(context);

        Day day;
        if (m.find()){
            if (m.group().contains("послезавтра")) day = Day.DAY_AFTER_TOMORROW;
            else if (m.group().contains("завтра")) day = Day.TOMORROW;
            else if (m.group().contains("нанеделю")) day = Day.WEEK;
            else day = Day.TODAY;
        } else {
            day = Day.TODAY;
        }

        return new Config(getGroup(context), day);
    }

    private String getGroup(String context){
        Pattern pattern = Pattern.compile(".+[0-9]+");
        Matcher m = pattern.matcher(context);
        if (m.find()){
            return m.group();
        } else {
            return null;
        }
    }

    @ToString
    private final class Config{
        Config(String group, Day day){
            this.group = group; this.day = day;
        }

        final String group;
        final Day day;
    }

    private enum Day {
        TOMORROW, DAY_AFTER_TOMORROW, WEEK, TODAY
    }
}


