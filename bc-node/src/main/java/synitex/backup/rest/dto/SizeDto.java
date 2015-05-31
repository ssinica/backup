package synitex.backup.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SizeDto {

    @JsonProperty("size")
    private long size;

    @JsonProperty("sizeFormatted")
    private String sizeFormatted;

    @JsonProperty("time")
    private long time;

    @JsonProperty("timeFormatted")
    private String timeFormatted;

    @JsonProperty("timeElapsedFormatted")
    private String timeElapsedFormatted;

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getSizeFormatted() {
        return sizeFormatted;
    }

    public void setSizeFormatted(String sizeFormatted) {
        this.sizeFormatted = sizeFormatted;
    }

    public String getTimeFormatted() {
        return timeFormatted;
    }

    public void setTimeFormatted(String timeFormatted) {
        this.timeFormatted = timeFormatted;
    }

    public String getTimeElapsedFormatted() {
        return timeElapsedFormatted;
    }

    public void setTimeElapsedFormatted(String timeElapsedFormatted) {
        this.timeElapsedFormatted = timeElapsedFormatted;
    }
}
