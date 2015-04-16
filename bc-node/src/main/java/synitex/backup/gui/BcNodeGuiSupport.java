package synitex.backup.gui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import synitex.backup.Application;

import javax.annotation.PostConstruct;
import java.awt.EventQueue;

@Component
public class BcNodeGuiSupport implements BcNodeSystemTrayListener {

    private static final Logger log = LoggerFactory.getLogger(BcNodeGuiSupport.class);

    private static final String HEADLESS_PROP = "java.awt.headless";

    @PostConstruct
    public void init() {
        startGui();
    }

    private void startGui() {
        if("true".equalsIgnoreCase(System.getProperty(HEADLESS_PROP))) {
            log.info("Application is running in headless mode. Will skip GUI setup. If you need GUI, consider setting system property {}=false.", HEADLESS_PROP);
        } else {
            EventQueue.invokeLater(new BcNodeSystemTray(this));
        }
    }

    @Override
    public void onExitClick() {
        Application.shutdown();
    }

}
