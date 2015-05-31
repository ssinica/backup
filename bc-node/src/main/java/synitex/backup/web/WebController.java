package synitex.backup.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import synitex.backup.prop.AppProperties;
import synitex.backup.rest.AbstractRest;
import synitex.backup.rest.dto.SourceOverviewDto;
import synitex.backup.service.IBackupHistoryService;
import synitex.backup.service.IBackupSourceService;
import synitex.backup.service.IDestinationService;
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
                         IBackupSourceService backupSourceService) {
        super(sizeService,
                appProperties,
                destinationProvider,
                backupHistoryService,
                backupSourceService);
    }

    @RequestMapping("/")
    public String index(Model model) {
        List<SourceOverviewDto> sourcesOverviewDtos = backupSourceService.list().stream()
                .map(this::mapToOverview)
                .collect(toList());
        model.addAttribute("sourceOverviews", sourcesOverviewDtos);
        model.addAttribute("appId", appProperties.getId());
        return "index";
    }

}
