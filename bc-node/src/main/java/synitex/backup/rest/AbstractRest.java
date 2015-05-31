package synitex.backup.rest;

import synitex.backup.db.tables.records.BackupHistoryRecord;
import synitex.backup.model.BackupSource;
import synitex.backup.model.Destination;
import synitex.backup.model.SizeTimed;
import synitex.backup.prop.AppProperties;
import synitex.backup.rest.dto.BackupHistoryItemDto;
import synitex.backup.rest.dto.DestinationDto;
import synitex.backup.rest.dto.SizeDto;
import synitex.backup.rest.dto.SourceDto;
import synitex.backup.rest.dto.SourceOverviewDto;
import synitex.backup.service.IBackupHistoryService;
import synitex.backup.service.IBackupSourceService;
import synitex.backup.service.IDestinationService;
import synitex.backup.service.ISizeService;
import synitex.backup.util.RsyncUtil;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

public abstract class AbstractRest {

    protected final ISizeService sizeService;
    protected final AppProperties appProperties;
    protected final IDestinationService destinationProvider;
    protected final IBackupHistoryService backupHistoryService;
    protected final IBackupSourceService backupSourceService;

    public AbstractRest(ISizeService sizeService,
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

    protected SourceOverviewDto mapToOverview(BackupSource source) {
        SourceOverviewDto dto = new SourceOverviewDto();

        Destination destination = destinationProvider.find(source.getDestination());
        DestinationDto destinationDto = mapDestination(destination);
        dto.setDestination(destinationDto);

        SizeTimed destinationSize = sizeService.size(source, destination);
        dto.setDestinationSize(mapSize(destinationSize));

        SourceDto sourceDto = new SourceDto();
        sourceDto.setId(source.getName());
        sourceDto.setPath(source.getPath());
        dto.setSource(sourceDto);

        SizeTimed sourceSize = sizeService.size(source);
        dto.setSourceSize(mapSize(sourceSize));

        List<BackupHistoryRecord> historyRecords = backupHistoryService.findBySourceForLastWeek(source.getName());
        List<BackupHistoryItemDto> backupHistoryDtos = StreamSupport.stream(historyRecords.spliterator(), false)
                .map(this::mapBackupHistoryItem)
                .collect(toList());
        dto.setBackupHistoryItems(backupHistoryDtos);

        return dto;
    }

    protected BackupHistoryItemDto mapBackupHistoryItem(BackupHistoryRecord record) {
        BackupHistoryItemDto dto = new BackupHistoryItemDto();
        dto.setStartedAt(record.getStartedAt());
        dto.setStartedAtFormatted(formatDateTime(record.getStartedAt()));
        dto.setElapsedFromStartFormatted(formatDuration(record.getStartedAt(), System.currentTimeMillis()));
        dto.setFinishedAt(record.getFinishedAt());
        dto.setFinishedAtFormatted(formatDateTime(record.getFinishedAt()));
        dto.setSuccessful(record.getExitCode() == 0);
        dto.setTransferedFilesSize(record.getTransferedFilesSize());
        dto.setTransferedFilesSizeFormatted(formatSize(record.getTransferedFilesSize(), true));
        dto.setErrorMessage(RsyncUtil.getErrorByExitCode(record.getExitCode()));
        dto.setDuration(formatDuration(record.getStartedAt(), record.getFinishedAt()));
        return dto;
    }

    private String formatDuration(long startedAt, long finishedAt) {
        Duration duration = Duration.between(Instant.ofEpochMilli(startedAt), Instant.ofEpochMilli(finishedAt));

        long days = duration.toDays();
        duration = duration.minusDays(days);

        long hours = duration.toHours();
        duration = duration.minusHours(hours);

        long minutes = duration.toMinutes();
        duration = duration.minusMinutes(minutes);

        long seconds = duration.getSeconds();
        duration = duration.minusSeconds(seconds);
        seconds = days > 0L || hours > 0L ? 0L : seconds;

        long milis = seconds > 0l || hours > 0L || days > 0L ? 0L : duration.toMillis();

        String s = ((days > 0L ? days + (days > 1L ? " days" : " day") : "")
                + (hours > 0L ? " " + hours + (hours > 1L ? " hours" : " hour") : "")
                + (minutes > 0L ? " " + minutes + " min" : "")
                + (seconds > 0l ? " " + seconds + " sec" : "")
                + (milis > 0l ? " " + milis + " ms" : ""))
                .trim();
        return "".equals(s) ? "moment" : s;
    }

    protected String formatSize(long bytes, boolean si) {
        if(bytes < 0) return "undefined";
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }

    protected String formatDateTime(long time) {
        Instant instant = Instant.ofEpochMilli(time);
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        return dateTime.format(formatter);
    }

    protected DestinationDto mapDestination(Destination d) {
        DestinationDto dto = new DestinationDto();
        dto.setId(d.getId());
        dto.setName(d.getName());
        dto.setType(d.getType().name());
        return dto;
    }

    protected SizeDto mapSize(SizeTimed s) {
        if(s == null) {
            return null;
        }
        SizeDto dto = new SizeDto();
        dto.setTime(s.getTime());
        dto.setTimeFormatted(formatDateTime(s.getTime()));
        dto.setSize(s.getSize());
        dto.setSizeFormatted(formatSize(s.getSize(), true));
        dto.setTimeElapsedFormatted(formatDuration(s.getTime(), System.currentTimeMillis()));
        return dto;
    }

}
