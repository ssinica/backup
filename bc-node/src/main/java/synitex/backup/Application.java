package synitex.backup;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import org.flywaydb.core.Flyway;
import org.jooq.DSLContext;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;
import synitex.backup.gui.BcNodeGuiSupport;
import synitex.backup.prop.AppProperties;

import javax.sql.DataSource;

@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    private static ConfigurableApplicationContext ctx;

    public static void main(String[] args) {

        log.info("Starting bc-node...");
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        SpringApplication app = new SpringApplicationBuilder()
                .showBanner(false)
                .sources(Application.class)
                .build();

        ctx = app.run(args);

        // print some logging configuration info
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        StatusPrinter.print(lc);

        // validate if mandatory properties are set
        AppProperties appProperties = ctx.getBean(AppProperties.class);
        String appId = appProperties.getId();
        if(StringUtils.isEmpty(appId)) {
            log.error("Application ID not set, will exit.");
            shutdown();
        } else {
            log.info("Application ID = {}", appId);

            // try to start GUI if headless mode is off
            BcNodeGuiSupport guiSupport = ctx.getBean(BcNodeGuiSupport.class);
            guiSupport.startGui();
        }

        stopWatch.stop();
        log.info("Application started in {} s.", stopWatch.getTotalTimeSeconds());
    }

    public static void shutdown() {
        log.info("Shutdown is requested!");
        try {
            ctx.close();
        } finally {
            log.info("See you!");
            System.exit(0);
        }
    }

    @Bean
    @Autowired
    public Flyway flyway(DataSource dataSource) {
        log.info("Starting database migration");
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        int appliedMigrationsCount = flyway.migrate();
        log.info("Successfully applied {} DB migrations!", appliedMigrationsCount);
        return flyway;
    }

    @Bean
    @Autowired
    public DSLContext dsl(DataSource dataSource, PlatformTransactionManager transactionManager) {
        DataSourceConnectionProvider connectionProvider = new DataSourceConnectionProvider(new TransactionAwareDataSourceProxy(dataSource));
        DefaultConfiguration config = new DefaultConfiguration();
        config.setConnectionProvider(connectionProvider);
        return new DefaultDSLContext(config);
    }

}
