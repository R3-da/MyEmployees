package hahn.myemployees;

import hahn.myemployees.view.EmployeeForm;
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

		// Run the code to load the form
		EventQueue.invokeLater(() -> {

			// Get the form object through Spring
			EmployeeForm employeeForm = context.getBean(EmployeeForm.class);
			employeeForm.setVisible(true);
		});
	}

}
