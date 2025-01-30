package brainacad.org.ticketordery.ServiceTest.DAO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public interface CRUDService_Interface<T>
{
    @BeforeEach
    public void setUpMock();

    @Test
    public void create_ReturnsClass_WhenValidInputProvided();

    @Test
    public void getById_ReturnsClass_WhenRequestExists();

    @Test
    public void delete_DoesNotThrow_WhenRequestExists();
}
