package synitex.backup.model;

public class BackupResult {

    public static final BackupResult RUNTIME_EXCEPTION = new BackupResult(-1);

    private int exitCode;
    private long duration;
    private int filesCount;
    private long transferedBytes;

    public BackupResult(int exitCode) {
        this.exitCode = exitCode;
    }

    public BackupResult(int exitCode, long duration, int filesCount, long transferedBytes) {
        this.exitCode = exitCode;
        this.duration = duration;
        this.filesCount = filesCount;
        this.transferedBytes = transferedBytes;
    }

    public boolean success() {
        return exitCode == 0;
    }

    public int getExitCode() {
        return exitCode;
    }

    public long getDuration() {
        return duration;
    }

    public int getFilesCount() {
        return filesCount;
    }

    public long getTransferedBytes() {
        return transferedBytes;
    }
}
