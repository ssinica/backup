package synitex.backup.service.impl;

import org.springframework.stereotype.Service;
import synitex.backup.model.BackupResult;
import synitex.backup.service.IRsyncOutputParser;

@Service
public class RsyncOutputParser implements IRsyncOutputParser {

    @Override
    public BackupResult parse(int exitCode, String out) {
        return new BackupResult(0, 100, 1000, 132123123);
    }

}
