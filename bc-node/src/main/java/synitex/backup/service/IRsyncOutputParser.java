package synitex.backup.service;

import synitex.backup.model.BackupResult;

public interface IRsyncOutputParser {

    BackupResult parse(int exitCode, String out);

}
