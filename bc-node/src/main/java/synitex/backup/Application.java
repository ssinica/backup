package synitex.backup;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;
import synitex.backup.gui.BcNodeGuiSupport;
import synitex.backup.prop.AppProperties;

@SpringBootApplication
@EnableScheduling
public class Application {

    private static final Logger BC_LOGGER = LoggerFactory.getLogger("bcnode");
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    private static ConfigurableApplicationContext ctx;

    public static void main(String[] args) {

        appLog().info("Starting bc-node...");
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        SpringApplication app = new SpringApplicationBuilder()
                .showBanner(false)
                .sources(Application.class)
                .profiles("default")
                .build();

        ctx = app.run(args);

        // print some logging configuration info
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        StatusPrinter.print(lc);

        // validate if mandatory properties are set
        AppProperties appProperties = ctx.getBean(AppProperties.class);
        String appId = appProperties.getId();
        if(StringUtils.isEmpty(appId)) {
            appLog().error("Application ID not set, will exit.");
            shutdown();
        } else {
            appLog().info("Application ID = {}", appId);

            // try to start GUI if headless mode is off
            BcNodeGuiSupport guiSupport = ctx.getBean(BcNodeGuiSupport.class);
            guiSupport.startGui();
        }

        stopWatch.stop();
        appLog().info("Application started in {} s.", stopWatch.getTotalTimeSeconds());
    }

    public static void shutdown() {
        log.info("Shutdown is requested! See you!");
        ctx.close();
    }

    public static Logger appLog() {
        return BC_LOGGER;
    }

}
