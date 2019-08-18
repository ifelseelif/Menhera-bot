package command;

import model.Message;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Arthur Kupriyanov
 */
class ScheduleTest {
    private Schedule schedule = new Schedule();
    @Test
    public void execute(){
        Message m = new Message("расписание у группы P3112", null, null, null);
        schedule.execute(m);
        Message m2 = new Message("расписание у группы P3112 завтра", null, null, null);
        schedule.execute(m2);
        Message m3 = new Message("расписание группы P3112 на неделю", null, null, null);
        schedule.execute(m3);
    }
}