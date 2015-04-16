package synitex.backup.gui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

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

    public BcNodeSystemTray(BcNodeSystemTrayListener listener) {
        this.listener = listener;
    }

    @Override
    public void run() {

        tray = SystemTray.getSystemTray();

        ClassPathResource imgRes = new ClassPathResource("res/bc-node.png");
        Image image = null;
        try (InputStream is = imgRes.getInputStream()) {
            image = ImageIO.read(is);
        } catch (IOException e) {
            log.error("Failed to open icon image", e);
            return;
        }

        PopupMenu popup = new PopupMenu();
        trayIcon = new TrayIcon(image, "bc-node", popup);
        trayIcon.setImageAutoSize(true);

        MenuItem item = new MenuItem("Exit");
        item.addActionListener(e -> exit());
        popup.add(item);

        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            log.error("Failed to start a tray", e);
        }

    }

    private void exit() {
        cleanUp();
        listener.onExitClick();
    }

    private void cleanUp() {
        tray.remove(trayIcon);
    }

}
