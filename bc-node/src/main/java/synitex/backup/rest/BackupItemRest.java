package synitex.backup.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import synitex.backup.model.BackupSource;
import synitex.backup.service.IBackupSourceService;

import java.util.List;

@RestController
@RequestMapping("/backupitem")
public class BackupItemRest {

    private final IBackupSourceService backupItemService;

    @Autowired
    public BackupItemRest(IBackupSourceService backupItemService) {
        this.backupItemService = backupItemService;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<BackupSource> list() {
        return backupItemService.list();
    }

    /*@RequestMapping(value = "/describe/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public BackupItemInfo describe(@PathVariable("name") String name) {
        BackupItemInfo info = backupItemService.describe(name);
        if(info == null) {
            throw new ItemNotFoundException(String.format("Destination with name '%s' not found.", name));
        }
        return info;
    }*/

}
