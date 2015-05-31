package synitex.backup.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BackupHistoryItemDto {

    @JsonProperty("startedAt")
    private long startedAt;

    @JsonProperty("startedAtFormatted")
    private String startedAtFormatted;

    @JsonProperty("elapsedFromStartFormatted")
    private String elapsedFromStartFormatted;

    @JsonProperty("finishedAt")
    private long finishedAt;

    @JsonProperty("finishedAtFormatted")
    private String finishedAtFormatted;

    @JsonProperty("duration")
    private String duration;

    @JsonProperty("successful")
    private boolean successful;

    @JsonProperty("transferedFilesSize")
    private long transferedFilesSize;

    @JsonProperty("transferedFilesSizeFormatted")
    private String transferedFilesSizeFormatted;

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

    public String getStartedAtFormatted() {
        return startedAtFormatted;
    }

    public void setStartedAtFormatted(String startedAtFormatted) {
        this.startedAtFormatted = startedAtFormatted;
    }

    public String getFinishedAtFormatted() {
        return finishedAtFormatted;
    }

    public void setFinishedAtFormatted(String finishedAtFormatted) {
        this.finishedAtFormatted = finishedAtFormatted;
    }

    public String getTransferedFilesSizeFormatted() {
        return transferedFilesSizeFormatted;
    }

    public void setTransferedFilesSizeFormatted(String transferedFilesSizeFormatted) {
        this.transferedFilesSizeFormatted = transferedFilesSizeFormatted;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getElapsedFromStartFormatted() {
        return elapsedFromStartFormatted;
    }

    public void setElapsedFromStartFormatted(String elapsedFromStartFormatted) {
        this.elapsedFromStartFormatted = elapsedFromStartFormatted;
    }
}
