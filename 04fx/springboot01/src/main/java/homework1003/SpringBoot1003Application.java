package homework1003;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(scanBasePackages = {"homework1003"})
public class SpringBoot1003Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext context =SpringApplication.run(SpringBoot1003Application.class, args);
    }
}
