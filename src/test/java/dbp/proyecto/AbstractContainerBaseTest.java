package dbp.proyecto;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@DataJpaTest
@Testcontainers
public abstract class AbstractContainerBaseTest {
	private static final PostgreSQLContainer<?> postgresqlContainer;

	static {
		postgresqlContainer = new PostgreSQLContainer<>("postgres:latest")
				.withDatabaseName("e2eTestDb")
				.withUsername("e2e")
				.withPassword("e2e");

		postgresqlContainer.start();
	}

	@DynamicPropertySource
	static void overrideTestProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", postgresqlContainer::getJdbcUrl);
		registry.add("spring.datasource.username", postgresqlContainer::getUsername);
		registry.add("spring.datasource.password", postgresqlContainer::getPassword);
	}
}