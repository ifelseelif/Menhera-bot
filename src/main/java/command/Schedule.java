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
        message.setMessage(message.getMessage().replace(" ", ""));
        Pattern pattern = Pattern.compile("расписание(у*группы)*.+");
        Matcher m = pattern.matcher(message.getMessage());
        String groupAndConfig = "";
        if (m.find()) {
            groupAndConfig = message.getMessage().replaceAll("расписание(у*группы)*", "");
        }

        Config config = getConfig(groupAndConfig);


        return getSchedule(config, message);
    }

    private Message getSchedule(Config config, Message message) {
        String schedule = null;
        try {

            log.info("Получено расписание " + (schedule = ScheduleParser.parseSchedule(config.group, config.day)));
        } catch (IOException e) {
            log.error("Не удалось получить расписание", e);
        }
        Message callbackMsg = Message.reverseMessage(message);
        callbackMsg.setMessage(schedule);
        return callbackMsg;
    }


    private Config getConfig(String context) {

        return new Config(getGroup(context), getDay(context), getParity(context));
    }

    private Week getParity(String context) {

        Pattern pattern = Pattern.compile("(четн.+)|(нечетн.+)");
        Matcher m = pattern.matcher(context);

        if (m.find()) {
            if (m.group().matches("четн.+")) {
                return Week.PARITY;
            } else {
                return Week.NOT_PARITY;
            }
        } else {
            return Week.NOT_STATED;
        }

    }

    private Day getDay(String context) {
        Pattern pattern = Pattern.compile("(((на)*завтра)|((на)*послезавтра)|(нанеделю))+");
        Matcher m = pattern.matcher(context);

        if (m.find()) {
            if (m.group().contains("послезавтра")) return Day.DAY_AFTER_TOMORROW;
            else if (m.group().contains("завтра")) return Day.TOMORROW;
            else if (m.group().contains("нанеделю")) return Day.WEEK;
            else return Day.TODAY;
        } else {
            return Day.NOT_STATED;
        }
    }

    private String getGroup(String context) {
        Pattern pattern = Pattern.compile(".+[0-9]+");
        Matcher m = pattern.matcher(context);
        if (m.find()) {
            return m.group();
        } else {
            return null;
        }
    }

    @ToString
    private final class Config {
        Config(String group, Day day) {
            this.group = group;
            this.day = day;
            this.week = Week.NOT_STATED;
        }

        Config(String group, Day day, Week week) {
            this.group = group;
            this.day = day;
            this.week = week;
        }

        public final String group;
        public final Day day;
        public final Week week;
    }

    public enum Day {
        TOMORROW, DAY_AFTER_TOMORROW, WEEK, TODAY, NOT_STATED
    }

    public enum Week {
        PARITY, NOT_PARITY, NOT_STATED
    }

    private class Pair {
        private String name;
        private String time;
        private String location;
        private String teacher;
        private String lecturRoom;
    }
}


