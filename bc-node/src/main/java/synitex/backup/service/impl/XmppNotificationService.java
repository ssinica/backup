package synitex.backup.service.impl;

import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rocks.xmpp.core.Jid;
import rocks.xmpp.core.session.XmppSession;
import rocks.xmpp.core.stanza.model.AbstractMessage.Type;
import rocks.xmpp.core.stanza.model.client.Message;
import rocks.xmpp.core.stanza.model.client.Presence;
import synitex.backup.event.BackupFinishedEvent;
import synitex.backup.model.BackupResult;
import synitex.backup.prop.AppProperties;
import synitex.backup.prop.NotificationProperties;
import synitex.backup.prop.XmppProperties;
import synitex.backup.service.IEventsService;
import synitex.backup.service.INotificationService;
import synitex.backup.util.TimeUtils;

@Service
public class XmppNotificationService implements INotificationService, DisposableBean {

    private static final Logger log = LoggerFactory.getLogger(XmppNotificationService.class);

    private final XmppProperties xmppProperties;
    private final NotificationProperties notificationProperties;
    private final AppProperties appProperties;

    private XmppSession xmppSession;

    @Autowired
    public XmppNotificationService(XmppProperties xmppProperties,
                                   NotificationProperties notificationProperties,
                                   IEventsService eventsService,
                                   AppProperties appProperties) {
        this.xmppProperties = xmppProperties;
        this.notificationProperties = notificationProperties;
        this.appProperties = appProperties;

        eventsService.register(this);
        initXmppSession();
    }

    @Override
    public void destroy() throws Exception {
        if(xmppSession != null) {
            log.debug("Closing XMPP session.");
            xmppSession.close();
        }
    }

    @Override
    public void notify(BackupFinishedEvent event) {
        BackupResult result = event.getResult();
        if(notificationProperties.isBackupSuccess() && result.success()
                || notificationProperties.isBackupFailed() && !result.success()) {
            String sourceId = event.getSource().getId();
            String destinationId = event.getDestination().getId();
            String time = TimeUtils.formatDateTime(event.getStartedAt());
            String status = event.getResult().success() ? "OK" : "Failed";
            String msg = String.format("[%s] %s! %s -> %s [%s]",
                    appProperties.getId(),
                    status,
                    sourceId,
                    destinationId,
                    time);
            log.info(String.format("Sending XMPP notification about backup (%s -> %s) finish.", sourceId, destinationId));
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
            log.info(String.format("Init XMPP session with domain '%s'", xmppProperties.getDomain()));
            xmppSession = new XmppSession(xmppProperties.getDomain());
        } else {
            log.info("XMPP notifications are disabled!");
        }
    }

    private void send(String message) {
        if(xmppSession == null) {
            return;
        }
        try {
            if(!xmppSession.isConnected()) {
                log.debug("XMPP session is not connected. Will connect.");
                xmppSession.connect();
                log.debug(String.format("Will login to XMPP server with '%s' username", xmppProperties.getSender()));
                xmppSession.login(xmppProperties.getSender(), xmppProperties.getSenderPass());
            }
            if(xmppSession.isConnected()) {
                xmppSession.send(new Presence());
                xmppSession.send(new Message(Jid.valueOf(xmppProperties.getReceiver()), Type.CHAT, message));
                if(log.isDebugEnabled()) {
                    log.debug(String.format("XMPP message '%s' is sent to '%s'", message, xmppProperties.getReceiver()));
                }
            } else {
                log.warn("XMPP message is not sent because XMPP session is not connected!");
            }
        } catch (Exception ex) {
            log.error("Failed to send XMPP message", ex);
        }
    }

}
