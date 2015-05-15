package synitex.backup.util;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;

import java.util.List;

public class CmdUtils {

    private CmdUtils() {}

    public static List<String> parse(String command) {
        if(Strings.isNullOrEmpty(command)) {
            throw new IllegalArgumentException("Command cannot be empty");
        }
        return Splitter.on(" ").omitEmptyStrings().trimResults().splitToList(command);
    }

}
