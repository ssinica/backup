package synitex.backup.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class OverviewDto {

    @JsonProperty("applicationId")
    private String appId;

    @JsonProperty("sourceOverviews")
    private List<SourceOverviewDto> sourceOverviews;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public List<SourceOverviewDto> getSourceOverviews() {
        return sourceOverviews;
    }

    public void setSourceOverviews(List<SourceOverviewDto> sourceOverviews) {
        this.sourceOverviews = sourceOverviews;
    }
}
