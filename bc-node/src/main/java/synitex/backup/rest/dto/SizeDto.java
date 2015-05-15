package synitex.backup.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SizeDto {

    @JsonProperty("size")
    private long size;

    @JsonProperty("label")
    private String label;

    @JsonProperty("time")
    private long time;

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
