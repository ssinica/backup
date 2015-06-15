package synitex.backup.util;

import java.util.HashMap;
import java.util.Map;

public class RsyncUtil {

    private static final Map<Integer, String> errors = new HashMap<>();
    static{
        errors.put(1,"Syntax or usage error");
        errors.put(2, "Protocol incompatibility");
        errors.put(3, "Errors selecting input/output files, dirs");
        errors.put(4, "Requested action not supported: an attempt was made to manipulate 64-bit files on a platform that cannot support them; or an option was specified that is  supported  by the client and not by the server.");
        errors.put(5, "Error starting client-server protocol");
        errors.put(6, "Daemon unable to append to log-file");
        errors.put(10, "Error in socket I/O");
        errors.put(11, "Error in file I/O");
        errors.put(12, "Error in rsync protocol data stream");
        errors.put(13, "Errors with program diagnostics");
        errors.put(14, "Error in IPC code");
        errors.put(20, "Received SIGUSR1 or SIGINT");
        errors.put(21, "Some error returned by waitpid");
        errors.put(22, "Error allocating core memory buffers");
        errors.put(23, "Partial transfer due to error");
        errors.put(24, "Partial transfer due to vanished source files");
        errors.put(25, "The --max-delete limit stopped deletions");
        errors.put(30, "Timeout in data send/receive");
        errors.put(35, "Timeout waiting for daemon connection");
    }

    private RsyncUtil() {}

    public static String getErrorByExitCode(int exitCode) {
        if(exitCode <= 0) {
            return null;
        }
        if(errors.containsKey(exitCode)) {
            return errors.get(exitCode);
        } else {
            return String.format("Rsync exited with code %s.", exitCode);
        }
    }

}
