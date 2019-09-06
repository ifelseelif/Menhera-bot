package command.util;

import command.Schedule;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @author Kolokolov Artyom
 */
public class ScheduleParser {
    private static final Logger log = Logger.getLogger(ScheduleParser.class);
    private static final String BASE_URL = "http://www.ifmo.ru/ru/schedule/0/%s/raspisanie_zanyatiy_%s.htm";
    private static final String URL = "http://www.ifmo.ru/ru/schedule/0/%s/%s/raspisanie_zanyatiy_%s.htm";
    private static final String[] days =
            {"7day", "1day", "2day", "3day", "4day", "5day", "6day"};
    private static GregorianCalendar calendar = new GregorianCalendar();
    private static Document doc;
    private static String result;
    private static int parityOfWeek;

    public static String parseSchedule(String group, Schedule.Day day) throws IOException {

        doc = Jsoup.connect(String.format(BASE_URL, group, group)).get();
        parityOfWeek = getParityOfWeek();
        doc = Jsoup.connect(String.format(URL, group, parityOfWeek, group)).get();
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

                StringBuilder sbResult = new StringBuilder();
                for (int i = 0; i < 7; i++) {
                    String curDay = getDay(i);
                    if (curDay.equals("7day") && i != 0) {
                        break;
                    } else {
                        try {
                            parseDay(curDay);
                            sbResult.append(result);
                        } catch (IOException ignored) {

                        }
                    }
                    sbResult.append("\n\n");
                }
                result = sbResult.toString();
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
        StringBuilder sb = new StringBuilder();
        for (Element e : elements) {
            sb.append(e.select(".time").select("span").text()).append(e.select(".room").text()).append(e.select(".lesson").text()).append("\n");
        }
        result = sb.toString();
    }

    private static int getParityOfWeek(){
        if(doc.getElementsByClass("schedule-week").select("span").text().contains("Нечетная")){
            return 2;
        } else {
            return 1;
        }
    }

    private static String getDay(int difDay) {
        return days[(calendar.get(Calendar.DAY_OF_WEEK) - 1 + difDay) % 7];
    }

}
