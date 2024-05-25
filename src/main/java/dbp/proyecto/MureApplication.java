package dbp.proyecto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableAsync
public class MureApplication {

	public static void main(String[] args) {
		SpringApplication.run(MureApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsMappingConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(org.springframework.web.servlet.config.annotation.CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("*")
						.allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "PATCH")
						.maxAge(3600)
						.allowedHeaders("*")
						.allowCredentials(false);
			}
		};
	}
}
