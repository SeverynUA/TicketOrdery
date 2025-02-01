package brainacad.org.ticketordery.ServiceTest.DAO.CustomerServiceEntityTest;

import brainacad.org.ticketordery.DTO.Model.CustomerDTO;
import brainacad.org.ticketordery.Entity.Model.Customer;
import brainacad.org.ticketordery.Mapping.CustomerMapping;
import brainacad.org.ticketordery.Repository.InterfaceDAO.CustomerDAO;
import brainacad.org.ticketordery.Service.CustomerServiceEntity.CustomerServiceEntity;
import brainacad.org.ticketordery.ServiceTest.DAO.CRUDService_Abstract;
import brainacad.org.ticketordery.TicketOrderyApplication;
import jakarta.annotation.PostConstruct;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TicketOrderyApplication.class)
@ActiveProfiles("test")
public class CustomerServiceEntityTest_Impl extends CRUDService_Abstract<Customer, CustomerDAO> implements CustomerServiceEntityTest {

    @Autowired
    private CustomerServiceEntity customerServiceEntity;
    @Autowired
    CustomerMapping customerMapping;
    @Autowired
    private CustomerDAO customerDAO;

    public CustomerServiceEntityTest_Impl()
    {
        super(null);
    }

    @PostConstruct
    private void initRepository()
    {
        super.repository = customerServiceEntity.getRepository();
    }

    @BeforeEach
    @Override
    public void setUpMock()
    {
        customerServiceEntity.create(CustomerDTO.builder().name("Test User 1").email("user1@test.com").phone("1234567890").build());
        customerServiceEntity.create(CustomerDTO.builder().name("Test User 2").email("user2@test.com").phone("0987654321").build());
    }

    @Test
    @Override
    public void create_ReturnsClass_WhenValidInputProvided()
    {
        CustomerDTO newCustomerDTO = CustomerDTO.builder()
                .name("New User")
                .email("newuser@test.com")
                .phone("1122334455")
                .build();

        Customer createdCustomer = customerMapping.toEntity(customerServiceEntity.create(newCustomerDTO));

        assertNotNull(createdCustomer, "Saved customer should not be null.");
        assertEquals("New User", createdCustomer.getName(), "Customer name mismatch.");
        assertEquals("newuser@test.com", createdCustomer.getEmail(), "Customer email mismatch.");
    }

    @Test
    @Override
    public void getById_ReturnsClass_WhenRequestExists()
    {
        Customer existingCustomer = customerDAO.findAll().get(0);

        Customer foundCustomer = customerMapping.toEntity(customerServiceEntity.getById(existingCustomer.getId()));

        assertNotNull(foundCustomer, "Found customer should not be null.");
        assertEquals(existingCustomer.getName(), foundCustomer.getName(), "Customer name mismatch.");
    }

    @Test
    @Override
    public void delete_DoesNotThrow_WhenRequestExists()
    {
        Customer existingCustomer = customerDAO.findAll().get(0);

        customerServiceEntity.delete(existingCustomer.getId());
        assertFalse(customerServiceEntity.getAll().stream().anyMatch(c -> c.getName().equals(existingCustomer.getName())), "Deleted customer should not exist.");
    }

    @Test
    public void update_UpdatesEntity_WhenValidInputProvided() {
        // Arrange
        Customer existingCustomer = customerDAO.findAll().get(0);
        CustomerDTO updatedCustomerDTO = CustomerDTO.builder()
                .name("Updated Name")
                .email("updated@test.com")
                .phone(existingCustomer.getPhone())
                .build();

        Customer updatedCustomer = customerMapping.toEntity(customerServiceEntity.update(existingCustomer.getId(), updatedCustomerDTO));

        assertNotNull(updatedCustomer, "Updated customer should not be null.");
        assertEquals("Updated Name", updatedCustomer.getName(), "Customer name was not updated.");
        assertEquals("updated@test.com", updatedCustomer.getEmail(), "Customer email was not updated.");
    }

    @Test
    public void getAll_ReturnsList_WhenCustomersExist()
    {
        List<CustomerDTO> customers = customerServiceEntity.getAll();

        assertNotNull(customers, "Customer list should not be null.");
        assertFalse(customers.isEmpty(), "Customer list should not be empty.");
        assertEquals(2, customers.size(), "Customer list size mismatch.");
    }
}