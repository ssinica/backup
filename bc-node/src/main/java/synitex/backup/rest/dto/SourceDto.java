package synitex.backup.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SourceDto {

    @JsonProperty("id")
    String id;

    @JsonProperty("path")
    String path;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
