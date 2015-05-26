package synitex.backup.gui;

import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import synitex.backup.event.BackupFinishedEvent;
import synitex.backup.event.BackupStartedEvent;

import javax.imageio.ImageIO;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.io.IOException;
import java.io.InputStream;

public class BcNodeSystemTray implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(BcNodeSystemTray.class);

    private final BcNodeSystemTrayListener listener;
    private SystemTray tray;
    private TrayIcon trayIcon;
    private SystemTrayState state = SystemTrayState.NORMAL;
    private boolean initialized = false;

    public BcNodeSystemTray(BcNodeSystemTrayListener listener) {
        this.listener = listener;
    }

    @Override
    public void run() {
        tray = SystemTray.getSystemTray();

        PopupMenu popup = new PopupMenu();

        Image image = loadImage(state);
        trayIcon = new TrayIcon(image, createTooltipText(state), popup);
        trayIcon.setImageAutoSize(true);

        MenuItem itemWebUI = new MenuItem("Overview");
        itemWebUI.addActionListener(e -> listener.onShowWebUIClick());
        popup.add(itemWebUI);

        popup.addSeparator();

        MenuItem itemExit = new MenuItem("Exit");
        itemExit.addActionListener(e -> exit());
        popup.add(itemExit);

        try {
            tray.add(trayIcon);
            initialized = true;
        } catch (AWTException e) {
            log.error("Failed to start a system tray", e);
        }
    }

    @Subscribe
    public void onBackupStarted(BackupStartedEvent event) {
        changeSystemTrayState(SystemTrayState.BACKUP_IN_PROGRESS);
    }

    @Subscribe
    public void onBackupFinished(BackupFinishedEvent event) {
        changeSystemTrayState(SystemTrayState.NORMAL);
    }

    public void cleanUp() {
        if(initialized) {
            tray.remove(trayIcon);
        }
    }

    private void exit() {
        cleanUp();
        listener.onExitClick();
    }

    private synchronized void changeSystemTrayState(SystemTrayState newState) {
        if(initialized && newState != state) {
            state = newState;
            Image image = loadImage(state);
            trayIcon.setImage(image);
            trayIcon.setToolTip(createTooltipText(state));
        }
    }

    private Image loadImage(SystemTrayState state) {
        ClassPathResource imgRes = new ClassPathResource(state.getImageUrl());
        try (InputStream is = imgRes.getInputStream()) {
            return ImageIO.read(is);
        } catch (IOException e) {
            log.error("Failed to open icon image", e);
            throw new IllegalStateException(String.format("Failed to open icon image: '%s'", state.getImageUrl()), e);
        }
    }

    private String createTooltipText(SystemTrayState state) {
        switch (state) {
            case BACKUP_IN_PROGRESS: return "Backup in progress...";
            default: return "All done!";
        }
    }

}
