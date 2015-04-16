package synitex.backup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    private static ConfigurableApplicationContext ctx;

    public static void main(String[] args) {
        ctx = SpringApplication.run(Application.class, args);
    }

    public static void shutdown() {
        log.info("Shutdown is requested! See you!");
        ctx.close();
    }

}
