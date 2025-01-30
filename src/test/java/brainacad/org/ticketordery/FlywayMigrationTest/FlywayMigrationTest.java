package brainacad.org.ticketordery.FlywayMigrationTest;

import jakarta.persistence.EntityManager;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
public class FlywayMigrationTest
{
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private Flyway flyway;

    @BeforeEach
    @Transactional
    public void cleanDatabase()
    {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    public void testFlywayMigration()
    {
        MigrationInfo[] appliedMigrations = flyway.info().applied();

        System.out.println("Number of applied migrations: " + appliedMigrations.length);

        assertTrue(appliedMigrations.length > 0, "No migrations were applied!");
    }
}
