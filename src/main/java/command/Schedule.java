package command;

import annotations.Command;
import command.util.ScheduleParser;
import lombok.ToString;
import model.Message;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Arthur Kupriyanov
 * @author Kolokolov Artyom
 */
@Command
public class Schedule extends NamedCommand {

    private final Logger log = Logger.getLogger(Schedule.class);

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


        return getSchedule(config, message);
    }

    private Message getSchedule(Config config, Message message) {
        String schedule = null;
        try {

            log.info("Получено расписание" + (schedule = ScheduleParser.parseSchedule(config.group, config.day)));
        } catch (IOException e) {
            log.error("Не удалось получить расписание", e);
        }
        Message callbackMsg = Message.reverseMessage(message);
        callbackMsg.setMessage(schedule);
        return callbackMsg;
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

    public enum Day {
        TOMORROW, DAY_AFTER_TOMORROW, WEEK, TODAY
    }
}


