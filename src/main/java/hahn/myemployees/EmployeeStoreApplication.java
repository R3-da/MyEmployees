package hahn.myemployees;

import hahn.myemployees.view.HomeScreen;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.awt.*;

@SpringBootApplication
public class EmployeeStoreApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context =
                new SpringApplicationBuilder(EmployeeStoreApplication.class)
                        .headless(false)
                        .web(WebApplicationType.NONE)
                        .run(args);

        // Run the code to load the home screen
        EventQueue.invokeLater(() -> {
            HomeScreen homeScreen = context.getBean(HomeScreen.class);
            homeScreen.setVisible(true);
        });
    }
}