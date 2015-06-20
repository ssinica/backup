package synitex.backup.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import synitex.backup.prop.AppProperties;
import synitex.backup.rest.AbstractRest;
import synitex.backup.rest.dto.BackupTaskOverviewDto;
import synitex.backup.service.IBackupHistoryService;
import synitex.backup.service.IBackupSourceService;
import synitex.backup.service.IBackupTaskService;
import synitex.backup.service.IDestinationService;
import synitex.backup.service.IScheduler;
import synitex.backup.service.ISizeHistoryService;
import synitex.backup.service.ISizeService;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Controller
public class WebController extends AbstractRest {

    @Autowired
    public WebController(ISizeService sizeService,
                         AppProperties appProperties,
                         IDestinationService destinationProvider,
                         IBackupHistoryService backupHistoryService,
                         IBackupSourceService backupSourceService,
                         IBackupTaskService taskService,
                         ISizeHistoryService sizeHistoryService,
                         IScheduler scheduler) {
        super(sizeService,
                appProperties,
                destinationProvider,
                backupHistoryService,
                backupSourceService,
                taskService,
                sizeHistoryService,
                scheduler);
    }

    @RequestMapping("/")
    public String index(Model model) {
        List<BackupTaskOverviewDto> sourcesOverviewDtos = taskService.list().stream()
                .map(this::mapToOverview)
                .collect(toList());
        model.addAttribute("taskOverviews", sourcesOverviewDtos);
        model.addAttribute("appId", appProperties.getId());
        return "index";
    }

    @RequestMapping("/execute")
    public String execute(@RequestParam(value = "sourceId", required = true) String sourceId,
                          @RequestParam(value = "destinationId", required = true) String destinationId,
                          Model model) {
        scheduler.adhocBackupTask(sourceId, destinationId);
        return index(model);
    }

}
