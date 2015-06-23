package synitex.backup.service.impl;

import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rocks.xmpp.core.Jid;
import rocks.xmpp.core.XmppException;
import rocks.xmpp.core.session.XmppSession;
import rocks.xmpp.core.stanza.model.AbstractMessage.Type;
import rocks.xmpp.core.stanza.model.client.Message;
import rocks.xmpp.core.stanza.model.client.Presence;
import synitex.backup.event.BackupFinishedEvent;
import synitex.backup.model.BackupResult;
import synitex.backup.prop.XmppProperties;
import synitex.backup.service.INotificationService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Service
public class XmppNotificationService implements INotificationService, DisposableBean {

    private static final Logger log = LoggerFactory.getLogger(XmppNotificationService.class);

    private final XmppProperties xmppProperties;
    private XmppSession xmppSession;

    @Autowired
    public XmppNotificationService(XmppProperties xmppProperties) {
        this.xmppProperties = xmppProperties;
        initXmppSession();
    }

    @Override
    public void destroy() throws Exception {
        if(xmppSession != null) {
            xmppSession.close();
        }
    }

    @Override
    public void notify(BackupFinishedEvent event) {
        BackupResult result = event.getResult();
        if(!result.success()) {
            String sourceId = event.getSource().getId();
            String destinationId = event.getDestination().getId();

            Instant instant = Instant.ofEpochMilli(event.getStartedAt());
            LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
            String time = formatter.format(dateTime);

            String msg = String.format("[%s] Failed! %s -> %s", time, sourceId, destinationId);
            send(msg);
        }
    }

    @Subscribe
    public void onBackupFinished(BackupFinishedEvent event) {
        if(xmppProperties.isEnabled()) {
            notify(event);
        }
    }

    private void initXmppSession() {
        if(xmppProperties.isEnabled()) {
            xmppSession = new XmppSession(xmppProperties.getDomain());
        }
    }

    private void send(String message) {
        if(xmppSession == null) {
            return;
        }
        if(!xmppSession.isConnected()) {
            try {
                xmppSession.connect();
                xmppSession.login(xmppProperties.getSender(), xmppProperties.getSenderPass());
            } catch (XmppException e) {
                log.error("Failed to connect to XMPP server", e);
            }
        }
        if(xmppSession.isConnected()) {
            xmppSession.send(new Presence());
            xmppSession.send(new Message(Jid.valueOf(xmppProperties.getReceiver()), Type.CHAT, message));
        }
    }

}
