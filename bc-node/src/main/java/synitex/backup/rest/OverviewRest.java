package synitex.backup.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import synitex.backup.prop.AppProperties;
import synitex.backup.rest.dto.OverviewDto;
import synitex.backup.rest.dto.BackupTaskOverviewDto;
import synitex.backup.service.IBackupHistoryService;
import synitex.backup.service.IBackupSourceService;
import synitex.backup.service.IBackupTaskService;
import synitex.backup.service.IDestinationService;
import synitex.backup.service.ISizeHistoryService;
import synitex.backup.service.ISizeService;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
public class OverviewRest extends AbstractRest {


    @Autowired
    public OverviewRest(ISizeService sizeService,
                        AppProperties appProperties,
                        IDestinationService destinationProvider,
                        IBackupHistoryService backupHistoryService,
                        IBackupSourceService backupSourceService,
                        IBackupTaskService taskService,
                        ISizeHistoryService sizeHistoryService) {
        super(
                sizeService,
                appProperties,
                destinationProvider,
                backupHistoryService,
                backupSourceService,
                taskService,
                sizeHistoryService);
    }

    @RequestMapping(RestUrls.OVERVIEW)
    public OverviewDto overview() {
        OverviewDto dto = new OverviewDto();
        dto.setAppId(appProperties.getId());

        List<BackupTaskOverviewDto> sourcesOverviewDtos = taskService.list().stream()
                .map(this::mapToOverview)
                .collect(toList());
        dto.setTaskOverviews(sourcesOverviewDtos);

        return dto;
    }

}
