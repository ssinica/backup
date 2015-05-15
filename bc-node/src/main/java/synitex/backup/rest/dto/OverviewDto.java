package synitex.backup.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class OverviewDto {

    @JsonProperty("applicationId")
    private String appId;

    @JsonProperty("destinations")
    private List<DestinationDto> destinations;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public List<DestinationDto> getDestinations() {
        return destinations;
    }

    public void setDestinations(List<DestinationDto> destinations) {
        this.destinations = destinations;
    }
}
