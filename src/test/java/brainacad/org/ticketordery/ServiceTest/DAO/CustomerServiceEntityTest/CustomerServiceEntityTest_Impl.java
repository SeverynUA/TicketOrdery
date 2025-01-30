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

    public CustomerServiceEntityTest_Impl() {
        super(null);
    }

    @PostConstruct
    private void initRepository() {
        super.repository = customerServiceEntity.getRepository();
    }

    @BeforeEach
    @Override
    public void setUpMock() {
        customerServiceEntity.create(CustomerDTO.builder().name("Test User 1").email("user1@test.com").phone("1234567890").build());
        customerServiceEntity.create(CustomerDTO.builder().name("Test User 2").email("user2@test.com").phone("0987654321").build());
    }

    @Test
    @Override
    public void create_ReturnsClass_WhenValidInputProvided() {
        // Arrange
        CustomerDTO newCustomerDTO = CustomerDTO.builder()
                .name("New User")
                .email("newuser@test.com")
                .phone("1122334455")
                .build();

        // Act
        Customer createdCustomer = customerMapping.toEntity(customerServiceEntity.create(newCustomerDTO));

        // Assert
        assertNotNull(createdCustomer, "Saved customer should not be null.");
        assertNotNull(createdCustomer.getId(), "Customer ID should not be null.");
        assertEquals("New User", createdCustomer.getName(), "Customer name mismatch.");
        assertEquals("newuser@test.com", createdCustomer.getEmail(), "Customer email mismatch.");
    }

    @Test
    @Override
    public void getById_ReturnsClass_WhenRequestExists() {
        // Arrange
        Customer existingCustomer = customerMapping.toEntity(customerServiceEntity.getAll().get(0));

        // Act
        Customer foundCustomer = customerMapping.toEntity(customerServiceEntity.getById(existingCustomer.getId()));

        // Assert
        assertNotNull(foundCustomer, "Found customer should not be null.");
        assertEquals(existingCustomer.getId(), foundCustomer.getId(), "Customer ID mismatch.");
        assertEquals(existingCustomer.getName(), foundCustomer.getName(), "Customer name mismatch.");
    }

    @Test
    @Override
    public void delete_DoesNotThrow_WhenRequestExists()
    {
        // Arrange
        Customer existingCustomer = customerMapping.toEntity(customerServiceEntity.getAll().get(0));

        // Act
        assertDoesNotThrow(() -> customerServiceEntity.delete(existingCustomer.getId()), "Deleting an existing customer should not throw.");

        // Assert
        assertFalse(customerServiceEntity.getAll().stream()
                .anyMatch(c -> c.getName().equals(existingCustomer.getName())), "Deleted customer should not exist.");
    }

    @Test
    public void update_UpdatesEntity_WhenValidInputProvided() {
        // Arrange
        Customer existingCustomer = customerMapping.toEntity(customerServiceEntity.getAll().get(0));
        CustomerDTO updatedCustomerDTO = CustomerDTO.builder()
                .name("Updated Name")
                .email("updated@test.com")
                .phone(existingCustomer.getPhone())
                .build();

        // Act
        Customer updatedCustomer = customerMapping.toEntity(customerServiceEntity.update(existingCustomer.getId(), updatedCustomerDTO));

        // Assert
        assertNotNull(updatedCustomer, "Updated customer should not be null.");
        assertEquals("Updated Name", updatedCustomer.getName(), "Customer name was not updated.");
        assertEquals("updated@test.com", updatedCustomer.getEmail(), "Customer email was not updated.");
    }

    @Test
    public void getAll_ReturnsList_WhenCustomersExist() {
        // Act
        List<CustomerDTO> customers = customerServiceEntity.getAll();

        // Assert
        assertNotNull(customers, "Customer list should not be null.");
        assertFalse(customers.isEmpty(), "Customer list should not be empty.");
        assertEquals(2, customers.size(), "Customer list size mismatch.");
    }
}