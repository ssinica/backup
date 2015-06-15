package synitex.backup.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class OverviewDto {

    @JsonProperty("applicationId")
    private String appId;

    @JsonProperty("taskOverviews")
    private List<BackupTaskOverviewDto> taskOverviews;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public List<BackupTaskOverviewDto> getTaskOverviews() {
        return taskOverviews;
    }

    public void setTaskOverviews(List<BackupTaskOverviewDto> taskOverviews) {
        this.taskOverviews = taskOverviews;
    }
}
