package synitex.backup.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "notification")
public class NotificationProperties {

    private boolean backupFailed = false;
    private boolean backupSuccess = false;

    public boolean isBackupFailed() {
        return backupFailed;
    }

    public void setBackupFailed(boolean backupFailed) {
        this.backupFailed = backupFailed;
    }

    public boolean isBackupSuccess() {
        return backupSuccess;
    }

    public void setBackupSuccess(boolean backupSuccess) {
        this.backupSuccess = backupSuccess;
    }
}
