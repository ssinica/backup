package synitex.backup.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BackupHistoryItemDto {

    @JsonProperty("startedAt")
    private long startedAt;

    @JsonProperty("finishedAt")
    private long finishedAt;

    @JsonProperty("successful")
    private boolean successful;

    @JsonProperty("transferedFilesSize")
    private long transferedFilesSize;

    @JsonProperty("errorMessage")
    private String errorMessage;

    public long getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(long startedAt) {
        this.startedAt = startedAt;
    }

    public long getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(long finishedAt) {
        this.finishedAt = finishedAt;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public long getTransferedFilesSize() {
        return transferedFilesSize;
    }

    public void setTransferedFilesSize(long transferedFilesSize) {
        this.transferedFilesSize = transferedFilesSize;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
