package synitex.backup.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import synitex.backup.model.Destination;
import synitex.backup.model.SizeTimed;
import synitex.backup.prop.AppProperties;
import synitex.backup.rest.dto.DestinationDto;
import synitex.backup.rest.dto.OverviewDto;
import synitex.backup.rest.dto.SizeDto;
import synitex.backup.service.IDestinationService;
import synitex.backup.service.ISizeService;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
public class OverviewRest {

    private final ISizeService sizeService;
    private final AppProperties appProperties;
    private final IDestinationService destinationProvider;

    @Autowired
    public OverviewRest(ISizeService sizeService,
                        AppProperties appProperties,
                        IDestinationService destinationProvider) {
        this.sizeService = sizeService;
        this.appProperties = appProperties;
        this.destinationProvider = destinationProvider;
    }

    @RequestMapping(RestUrls.OVERVIEW)
    public OverviewDto overview() {
        OverviewDto dto = new OverviewDto();
        dto.setAppId(appProperties.getId());

        List<DestinationDto> destinations = destinationProvider.list().stream()
                .map(this::mapDestination)
                .collect(toList());
        dto.setDestinations(destinations);

        return dto;
    }

    private DestinationDto mapDestination(Destination d) {
        DestinationDto dto = new DestinationDto();
        dto.setId(d.getId());
        dto.setName(d.getName());
        dto.setType(d.getType().name());
        dto.setSize(mapSize(sizeService.size(d)));
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
