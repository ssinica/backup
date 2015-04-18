package synitex.backup.gui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import synitex.backup.Application;

import javax.annotation.PreDestroy;
import java.awt.EventQueue;

@Component
public class BcNodeGuiSupport implements BcNodeSystemTrayListener {

    private static final Logger log = LoggerFactory.getLogger(BcNodeGuiSupport.class);

    private static final String HEADLESS_PROP = "java.awt.headless";

    private BcNodeSystemTray systemTray;

    public void startGui() {
        if("true".equalsIgnoreCase(System.getProperty(HEADLESS_PROP))) {
            log.info("Application is running in headless mode. Will skip GUI setup. If you need GUI, consider setting system property {}=false.", HEADLESS_PROP);
        } else {
            systemTray = new BcNodeSystemTray(this);
            EventQueue.invokeLater(systemTray);
        }
    }

    @PreDestroy
    public void onDestroy() {
        if(systemTray != null) {
            try {
                systemTray.cleanUp();
            } catch (Exception ex) {
                log.error("Failed to clean up GUI", ex);
            }
        }
    }

    @Override
    public void onExitClick() {
        Application.shutdown();
    }

}
