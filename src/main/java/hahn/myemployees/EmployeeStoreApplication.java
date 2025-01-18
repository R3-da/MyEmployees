package hahn.myemployees;

import hahn.myemployees.view.HomeScreen;
import org.slf4j.LoggerFactory;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.EventListener;

import java.awt.*;
import java.util.logging.Logger;

@SpringBootApplication
public class EmployeeStoreApplication {

    private static final Logger logger = Logger.getLogger(EmployeeStoreApplication.class.getName());

    public static void main(String[] args) {
        ConfigurableApplicationContext context =
                new SpringApplicationBuilder(EmployeeStoreApplication.class)
                        .headless(false)
                        .web(WebApplicationType.SERVLET) // Changed to SERVLET to enable web server
                        .run(args);

        logger.info("🚀 API Server started on port 8080");
        logger.info("📚 API Documentation available at http://localhost:8080/api/employees");

        EventQueue.invokeLater(() -> {
            HomeScreen homeScreen = context.getBean(HomeScreen.class);
            homeScreen.setVisible(true);
            logger.info("💻 Swing GUI initialized and displayed");
        });
    }

    @EventListener(ApplicationReadyEvent.class)
    public void logStartup() {
        logger.info("✅ Application fully started and ready to accept requests");
    }
}
