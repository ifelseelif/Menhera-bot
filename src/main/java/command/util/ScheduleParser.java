package command.util;

import command.Schedule;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class ScheduleParser {

    private static final String BASE_URL = "http://www.ifmo.ru/ru/schedule/0/%s/raspisanie_zanyatiy_%s.htm";
    private static final String[] days =
            {"7day", "1day", "2day", "3day", "4day", "5day", "6day"};
    private static GregorianCalendar calendar = new GregorianCalendar();
    private static Document doc;
    private static String result;

    public static String parseSchedule(String group, Schedule.Day day) throws IOException {
        doc = Jsoup.connect(String.format(BASE_URL, group, group)).get();
        switch (day) {
            case TODAY:
                result = "Расписание на сегодня \n";
                parseDay(getDay(0));
                break;
            case TOMORROW:
                result = "Расписание на завтра \n";
                parseDay(getDay(1));
                break;
            case DAY_AFTER_TOMORROW:
                result = "Расписание на после завтра \n";
                parseDay(getDay(2));
                break;
            case WEEK:
                result = "Расписание на неделю \n";
                for (int i = 0; i < 7; i++) {
                    String curDay = getDay(i);
                    if (curDay == "7day" && i != 0) {
                        break;
                    } else {
                        try {
                            parseDay(curDay);
                        } catch (IOException e) {
                        }
                    }
                    result += "\n\n";
                }
                break;
        }
        return result;
    }

    private static void parseDay(String day) throws IOException {
        Element element = doc.getElementById(day);
        if (element == null) {
            throw new IOException("smth");
        }
        Elements elements = element.select("tr");
        for (Element e : elements) {
            result +=
                    e.select(".time").select("span").text() +
                            e.select(".room").text() +
                            e.select(".lesson").text() +
                            "\n";
        }
    }

    private static String getDay(int difDay) {
        return days[(calendar.get(Calendar.DAY_OF_WEEK) - 1 + difDay) % 7];
    }

}
