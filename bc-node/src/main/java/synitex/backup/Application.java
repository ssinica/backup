package synitex.backup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.StringUtils;
import synitex.backup.gui.BcNodeGuiSupport;
import synitex.backup.prop.AppProperties;

@SpringBootApplication
@EnableScheduling
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    private static ConfigurableApplicationContext ctx;

    public static void main(String[] args) {

        SpringApplication app = new SpringApplicationBuilder()
                .showBanner(false)
                .sources(Application.class)
                .profiles("default")
                .build();

        ctx = app.run(args);

        AppProperties appProperties = ctx.getBean(AppProperties.class);
        String appId = appProperties.getId();
        if(StringUtils.isEmpty(appId)) {
            log.error("Application ID not set, will exit.");
            shutdown();
        } else {
            log.info("Application ID = {}", appId);
            BcNodeGuiSupport guiSupport = ctx.getBean(BcNodeGuiSupport.class);
            guiSupport.startGui();
        }

    }

    public static void shutdown() {
        log.info("Shutdown is requested! See you!");
        ctx.close();
    }

}
