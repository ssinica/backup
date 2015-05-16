package synitex.backup.service.impl;

import com.google.common.base.Splitter;
import org.springframework.stereotype.Service;
import synitex.backup.model.BackupResult;
import synitex.backup.service.IRsyncOutputParser;

import java.util.List;

@Service
public class RsyncOutputParser implements IRsyncOutputParser {

    private static final String LBL_FILE_SIZE = "Total file size:";
    private static final String LBL_TRANSFERED_FILE_SIZE = "Total transferred file size:";
    private static final String LBL_TOTAL_TRANSFERED = "Total bytes sent:";

    // --------- Example output -----------------
    //Number of files: 154 (reg: 153, dir: 1)
    //Number of created files: 0
    //Number of regular files transferred: 0
    //Total file size: 3,213,350,252 bytes
    //Total transferred file size: 0 bytes
    //Literal data: 0 bytes
    //Matched data: 0 bytes
    //File list size: 0
    //File list generation time: 0.001 seconds
    //File list transfer time: 0.000 seconds
    //Total bytes sent: 8,690
    //Total bytes received: 16

    //sent 8,690 bytes  received 16 bytes  5,804.00 bytes/sec
    //total size is 3,213,350,252  speedup is 369,096.05
    // -------------------------------------------

    @Override
    public BackupResult parse(int exitCode, String out) {
        BackupResult result = new BackupResult(exitCode);
        if(exitCode == 0) {
            List<String> parts = Splitter.on("\n").omitEmptyStrings().trimResults().splitToList(out);
            for(String part : parts) {
                if(part.startsWith(LBL_FILE_SIZE)) {
                    result.setFilesSize(parseLong(part, LBL_FILE_SIZE));
                } else if(part.startsWith(LBL_TRANSFERED_FILE_SIZE)) {
                    result.setTransferedFilesSize(parseLong(part, LBL_TRANSFERED_FILE_SIZE));
                } else if(part.startsWith(LBL_TOTAL_TRANSFERED)) {
                    result.setTotalTransferedSize(parseLong(part, LBL_TOTAL_TRANSFERED));
                }
            }
        }
        return result;
    }

    private long parseLong(String part, String pre) {
        String s = part.substring(pre.length()).trim();
        int idx = s.indexOf(" ");
        String n = idx > 0 ? s.substring(0, idx) : s;
        n = n.replaceAll(",", "");
        return Long.valueOf(n);
    }

}
