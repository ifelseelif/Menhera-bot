package command.util;

import command.Schedule;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ScheduleParserTest {

    @Test
    void getDayOfWeekNow() throws IOException {
        System.out.println(ScheduleParser.parseSchedule("P3212", Schedule.Day.WEEK));
        System.out.println("\n\n");
        //System.out.println(ScheduleParser.parseSchedule("Z3143", Schedule.Day.TODAY));
        //System.out.println(ScheduleParser.parseSchedule("Z3143", Schedule.Day.TOMORROW));
        //System.out.println(ScheduleParser.test());
        //System.out.println(ScheduleParser.parseSchedule("Z3143", Schedule.Day.DAY_AFTER_TOMORROW));





    }
}