package synitex.backup.model;

import com.google.common.base.MoreObjects;

public class BackupResult {

    public static final BackupResult RUNTIME_EXCEPTION = new BackupResult(-1);

    private int exitCode;

    private long transferedFilesSize;
    private long filesSize;
    private long totalTransferedSize;

    public BackupResult(int exitCode) {
        this.exitCode = exitCode;
    }

    public boolean success() {
        return exitCode == 0;
    }

    public int getExitCode() {
        return exitCode;
    }

    public void setExitCode(int exitCode) {
        this.exitCode = exitCode;
    }

    public long getFilesSize() {
        return filesSize;
    }

    public void setFilesSize(long filesSize) {
        this.filesSize = filesSize;
    }

    public long getTransferedFilesSize() {
        return transferedFilesSize;
    }

    public void setTransferedFilesSize(long transferedFilesSize) {
        this.transferedFilesSize = transferedFilesSize;
    }

    public long getTotalTransferedSize() {
        return totalTransferedSize;
    }

    public void setTotalTransferedSize(long totalTransferedSize) {
        this.totalTransferedSize = totalTransferedSize;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(getClass())
                .add("exit code", exitCode)
                .toString();
    }
}
