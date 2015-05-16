package synitex.backup.gui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.stereotype.Component;
import synitex.backup.Application;
import synitex.backup.rest.RestUrls;
import synitex.backup.service.IEventsService;

import javax.annotation.PreDestroy;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.io.IOException;
import java.net.URI;

@Component
public class BcNodeGuiSupport implements BcNodeSystemTrayListener {

    private static final Logger log = LoggerFactory.getLogger(BcNodeGuiSupport.class);

    private static final String HEADLESS_PROP = "java.awt.headless";

    private final IEventsService eventsService;
    private final ServerProperties serverProperties;

    @Autowired
    public BcNodeGuiSupport(IEventsService eventsService, ServerProperties serverProperties) {
        this.eventsService = eventsService;
        this.serverProperties = serverProperties;
    }

    private BcNodeSystemTray systemTray;

    public void startGui() {
        if("true".equalsIgnoreCase(System.getProperty(HEADLESS_PROP))) {
            log.info("Application is running in headless mode. Will skip GUI setup. If you need GUI, consider setting system property {}=false.", HEADLESS_PROP);
        } else {
            systemTray = new BcNodeSystemTray(this);
            EventQueue.invokeLater(systemTray);
            eventsService.register(systemTray);
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

    @Override
    public void onShowWebUIClick() {
        String url = String.format("http://localhost:%s%s", serverProperties.getPort(), RestUrls.OVERVIEW);
        if(Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(URI.create(url));
            } catch (IOException e) {
                log.error(String.format("Failed to open WEB UI: %s", url), e);
            }
        } else {
            log.error("Failed to open WEB UI, because 'desktop' object not supported!");
        }
    }

}
