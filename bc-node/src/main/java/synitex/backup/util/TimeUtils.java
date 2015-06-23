package synitex.backup.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class TimeUtils {

    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    public static String formatDateTime(long milis) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(milis), ZoneId.systemDefault());
        return DTF.format(dateTime);
    }

}
