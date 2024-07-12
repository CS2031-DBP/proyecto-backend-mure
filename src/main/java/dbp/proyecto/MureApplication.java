package dbp.proyecto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync
public class MureApplication {
    public static void main(String[] args) {
        SpringApplication.run(MureApplication.class, args);
    }
}
