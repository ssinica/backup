package synitex.backup.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SizeDto {

    @JsonProperty("size")
    private long size;

    @JsonProperty("measure")
    private String measure;

    @JsonProperty("time")
    private long time;

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
