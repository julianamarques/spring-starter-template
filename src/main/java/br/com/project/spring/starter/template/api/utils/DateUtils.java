package br.com.project.spring.starter.template.api.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

public class DateUtils {
    public static LocalDateTime convertDateToLocalDateTime(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public static String formatDate(String pattern, LocalDateTime date) {
        return Objects.nonNull(date) ? DateTimeFormatter.ofPattern(pattern).format(date) : null;
    }

    public static String formatDate(String pattern, Date date) {
        return Objects.nonNull(date) ? new SimpleDateFormat(pattern).format(date) : null;
    }
}
