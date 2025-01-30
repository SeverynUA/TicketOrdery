package brainacad.org.ticketordery.ServiceTest.DAO;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class CRUDService_Abstract<T, R extends JpaRepository<T, Long>> {
    protected static JpaRepository<?, ?> repository;

    public CRUDService_Abstract(R repository) {
        CRUDService_Abstract.repository = repository;
    }

    @BeforeEach
    public void setUp() {
        System.out.println("Setting up test environment and clearing repository...");
        repository.deleteAll();
    }


    @AfterAll
    public static void clearDB() {
        if (repository != null) {
            System.out.println("Clearing database after all tests...");
            repository.deleteAll();
        } else {
            System.out.println("Repository is not set; skipping clearDB.");
        }
    }
}
