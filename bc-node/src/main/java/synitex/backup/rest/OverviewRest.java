package synitex.backup.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import synitex.backup.db.tables.records.BackupHistoryRecord;
import synitex.backup.model.BackupSource;
import synitex.backup.model.Destination;
import synitex.backup.model.SizeTimed;
import synitex.backup.prop.AppProperties;
import synitex.backup.rest.dto.BackupHistoryItemDto;
import synitex.backup.rest.dto.DestinationDto;
import synitex.backup.rest.dto.OverviewDto;
import synitex.backup.rest.dto.SizeDto;
import synitex.backup.rest.dto.SourceDto;
import synitex.backup.rest.dto.SourceOverviewDto;
import synitex.backup.service.IBackupHistoryService;
import synitex.backup.service.IBackupSourceService;
import synitex.backup.service.IDestinationService;
import synitex.backup.service.ISizeService;
import synitex.backup.util.RsyncUtil;

import java.util.List;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

@RestController
public class OverviewRest {

    private final ISizeService sizeService;
    private final AppProperties appProperties;
    private final IDestinationService destinationProvider;
    private final IBackupHistoryService backupHistoryService;
    private final IBackupSourceService backupSourceService;

    @Autowired
    public OverviewRest(ISizeService sizeService,
                        AppProperties appProperties,
                        IDestinationService destinationProvider,
                        IBackupHistoryService backupHistoryService,
                        IBackupSourceService backupSourceService) {
        this.sizeService = sizeService;
        this.appProperties = appProperties;
        this.destinationProvider = destinationProvider;
        this.backupHistoryService = backupHistoryService;
        this.backupSourceService = backupSourceService;
    }

    @RequestMapping(RestUrls.OVERVIEW)
    public OverviewDto overview() {
        OverviewDto dto = new OverviewDto();
        dto.setAppId(appProperties.getId());

        List<SourceOverviewDto> sourcesOverviewDtos = backupSourceService.list().stream()
                .map(this::mapToOverview)
                .collect(toList());
        dto.setSourceOverviews(sourcesOverviewDtos);

        return dto;
    }

    private SourceOverviewDto mapToOverview(BackupSource source) {
        SourceOverviewDto dto = new SourceOverviewDto();

        Destination destination = destinationProvider.find(source.getDestination());
        DestinationDto destinationDto = mapDestination(destination);
        dto.setDestination(destinationDto);

        SourceDto sourceDto = new SourceDto();
        sourceDto.setId(source.getName());
        sourceDto.setPath(source.getPath());
        dto.setSource(sourceDto);

        Page<BackupHistoryRecord> historyPage = backupHistoryService.findBySource(source.getName(), new PageRequest(0, 10));
        List<BackupHistoryItemDto> backupHistoryDtos = StreamSupport.stream(historyPage.spliterator(), false)
                .map(this::mapBackupHistoryItem)
                .collect(toList());
        dto.setBackupHistoryItems(backupHistoryDtos);

        return dto;
    }

    private BackupHistoryItemDto mapBackupHistoryItem(BackupHistoryRecord record) {
        BackupHistoryItemDto dto = new BackupHistoryItemDto();
        dto.setStartedAt(record.getStartedAt());
        dto.setFinishedAt(record.getFinishedAt());
        dto.setSuccessful(record.getExitCode() == 0);
        dto.setTransferedFilesSize(record.getTransferedFilesSize());
        dto.setErrorMessage(RsyncUtil.getErrorByExitCode(record.getExitCode()));
        return dto;
    }

    private DestinationDto mapDestination(Destination d) {
        DestinationDto dto = new DestinationDto();
        dto.setId(d.getId());
        dto.setName(d.getName());
        dto.setType(d.getType().name());
        return dto;
    }

    private SizeDto mapSize(SizeTimed size) {
        if(size == null) {
            return null;
        }
        SizeDto dto = new SizeDto();
        dto.setTime(size.getTime());
        dto.setSize(size.getSize());
        dto.setMeasure(size.getMeasure().getLabel());
        return dto;
    }

}
