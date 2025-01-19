package com.hahn.myemployees;

import com.hahn.myemployees.view.LoginScreen;
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
        logger.info("📚 API Testing: The GET /api/employees endpoint is accessible at http://localhost:8080/api/employees");

        EventQueue.invokeLater(() -> {
            LoginScreen homeScreen = context.getBean(LoginScreen.class);
            homeScreen.setVisible(true);
            logger.info("💻 Swing GUI initialized and displayed");
        });
    }

    @EventListener(ApplicationReadyEvent.class)
    public void logStartup() {
        logger.info("✅ Application fully started and ready to accept requests");
    }
}
