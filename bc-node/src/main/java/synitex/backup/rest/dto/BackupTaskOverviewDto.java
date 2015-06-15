package synitex.backup.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class BackupTaskOverviewDto {

    @JsonProperty("source")
    private SourceDto source;

    @JsonProperty("destination")
    private DestinationDto destination;

    @JsonProperty("sourceSize")
    private SizeDto sourceSize;

    @JsonProperty("destinationSize")
    private SizeDto destinationSize;

    @JsonProperty("backupHistoryItems")
    private List<BackupHistoryItemDto> backupHistoryItems;

    public SourceDto getSource() {
        return source;
    }

    public void setSource(SourceDto source) {
        this.source = source;
    }

    public DestinationDto getDestination() {
        return destination;
    }

    public void setDestination(DestinationDto destination) {
        this.destination = destination;
    }

    public SizeDto getSourceSize() {
        return sourceSize;
    }

    public void setSourceSize(SizeDto sourceSize) {
        this.sourceSize = sourceSize;
    }

    public SizeDto getDestinationSize() {
        return destinationSize;
    }

    public void setDestinationSize(SizeDto destinationSize) {
        this.destinationSize = destinationSize;
    }

    public List<BackupHistoryItemDto> getBackupHistoryItems() {
        return backupHistoryItems;
    }

    public void setBackupHistoryItems(List<BackupHistoryItemDto> backupHistoryItems) {
        this.backupHistoryItems = backupHistoryItems;
    }
}
