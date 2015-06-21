package synitex.backup.gui;

import com.google.common.base.Joiner;
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
import java.util.concurrent.ConcurrentHashMap;

public class BcNodeSystemTray implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(BcNodeSystemTray.class);
    private static final String ALL_DONE = "All done!";

    private final BcNodeSystemTrayListener listener;
    private SystemTray tray;
    private TrayIcon trayIcon;
    private boolean initialized = false;

    private ConcurrentHashMap<String, String> tooltips = new ConcurrentHashMap<>();

    public BcNodeSystemTray(BcNodeSystemTrayListener listener) {
        this.listener = listener;
    }

    @Override
    public void run() {
        tray = SystemTray.getSystemTray();

        PopupMenu popup = new PopupMenu();

        Image image = loadImage(SystemTrayState.NORMAL);
        trayIcon = new TrayIcon(image, ALL_DONE, popup);
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
        String s = String.format("Syncing %s to %s...", event.getSource().getId(), event.getDestination().getId());
        String id = event.getSource().getId() + "-" +  event.getDestination().getId();
        tooltips.put(id, s);
        updateSystemTrayState();
    }

    @Subscribe
    public void onBackupFinished(BackupFinishedEvent event) {
        String id = event.getSource().getId() + "-" +  event.getDestination().getId();
        tooltips.remove(id);
        updateSystemTrayState();
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

    private synchronized void updateSystemTrayState() {
        if(!initialized) {
            return;
        }

        SystemTrayState state = SystemTrayState.NORMAL;
        String text = ALL_DONE;
        if(tooltips.size() > 0) {
            state = SystemTrayState.BACKUP_IN_PROGRESS;
            text = Joiner.on("\n").join(tooltips.values());
        }

        Image image = loadImage(state);
        trayIcon.setImage(image);
        trayIcon.setToolTip(text);

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

}
