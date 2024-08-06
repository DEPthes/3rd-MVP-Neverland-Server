package depth.mvp.thinkerbell.domain.notice.service;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScheduleParser {

    private static final Pattern DATE_PATTERN = Pattern.compile("\\.(\\d{2})\\s(\\d{2})\\s~\\s\\.(\\d{2})\\s(\\d{2})");

    public static LocalDate[] parseDate(String dateRange) {
        Matcher matcher = DATE_PATTERN.matcher(dateRange);

        if (matcher.matches()) {
            int startMonth = Integer.parseInt(matcher.group(1));
            int startDay = Integer.parseInt(matcher.group(2));
            int endMonth = Integer.parseInt(matcher.group(3));
            int endDay = Integer.parseInt(matcher.group(4));

            LocalDate startDate = LocalDate.of(LocalDate.now().getYear(), startMonth, startDay);
            LocalDate endDate = LocalDate.of(LocalDate.now().getYear(), endMonth, endDay);

            return new LocalDate[]{startDate, endDate};
        } else {
            throw new IllegalArgumentException("Invalid date range: " + dateRange);
        }
    }
}
