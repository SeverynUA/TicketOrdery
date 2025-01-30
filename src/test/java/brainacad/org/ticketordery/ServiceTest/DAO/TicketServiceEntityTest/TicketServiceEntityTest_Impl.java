package brainacad.org.ticketordery.ServiceTest.DAO.TicketServiceEntityTest;

import brainacad.org.ticketordery.Entity.Model.Customer;
import brainacad.org.ticketordery.Entity.Model.Event;
import brainacad.org.ticketordery.Entity.Model.Place;
import brainacad.org.ticketordery.Entity.Model.Ticket;
import brainacad.org.ticketordery.Repository.InterfaceDAO.CustomerDAO;
import brainacad.org.ticketordery.Repository.InterfaceDAO.EventDAO;
import brainacad.org.ticketordery.Repository.InterfaceDAO.PlaceDAO;
import brainacad.org.ticketordery.Repository.InterfaceDAO.TicketDAO;
import brainacad.org.ticketordery.ServiceTest.DAO.CRUDService_Abstract;
import brainacad.org.ticketordery.TicketOrderyApplication;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TicketOrderyApplication.class)
@ActiveProfiles("test")
public class TicketServiceEntityTest_Impl extends CRUDService_Abstract<Ticket, TicketDAO> implements TicketServiceEntityTest {

    @Autowired
    private TicketDAO ticketDAO;

    @Autowired
    private EventDAO eventDAO;

    @Autowired
    private CustomerDAO customerDAO;

    @Autowired
    PlaceDAO placeDAO;

    public TicketServiceEntityTest_Impl()
    {
        super(null);
    }

    @PostConstruct
    private void initRepository()
    {
        super.repository = ticketDAO;
    }

    @BeforeEach
    @Override
    public void setUpMock()
    {
        customerDAO.deleteAll();

        Place testPlace = Place.builder().name("Test Place").address("123 Test Street").build();
        Place savedPlace = placeDAO.save(testPlace);

        Event event = eventDAO.save(Event.builder().name("Test Event").eventDate(LocalDate.now().plusDays(5)).place(testPlace).build());
        Customer customer = customerDAO.save(Customer.builder().name("John Doe").email("john.doe@test.com").phone("1234567890").build());
        ticketDAO.save(Ticket.builder().cost(BigDecimal.valueOf(50.0)).number(1).status(false).event(event).customer(customer).build());
    }

    @Test
    @Override
    public void create_ReturnsClass_WhenValidInputProvided()
    {

        Event event = eventDAO.findAll().get(0);
        Customer customer = customerDAO.findAll().get(0);

        Ticket newTicket = Ticket.builder().cost(BigDecimal.valueOf(100.0)).number(2).status(false).event(event).customer(customer).build();

        Ticket savedTicket = ticketDAO.save(newTicket);

        assertNotNull(savedTicket);
        assertNotNull(savedTicket.getId());
        assertEquals(BigDecimal.valueOf(100.0), savedTicket.getCost());
    }

    @Test
    @Override
    public void getById_ReturnsClass_WhenRequestExists()
    {
        Ticket savedTicket = ticketDAO.findAll().get(0);

        Ticket foundTicket = ticketDAO.findById(savedTicket.getId()).orElseThrow(() -> new RuntimeException("Ticket not found"));

        assertNotNull(foundTicket);
        assertEquals(savedTicket.getId(), foundTicket.getId());
    }

    @Test
    @Override
    public void delete_DoesNotThrow_WhenRequestExists()
    {
        Ticket savedTicket = ticketDAO.findAll().get(0);

        assertDoesNotThrow(() -> ticketDAO.delete(savedTicket));

        assertFalse(ticketDAO.findById(savedTicket.getId()).isPresent());
    }

    @Test
    @Override
    public void findTicketsByEventAndStatus_ReturnsCorrectTickets()
    {
        Customer customer = customerDAO.save(Customer.builder().name("Test Customer").email("test@customer.com").phone("12345678900").build());

        Place place = placeDAO.save(Place.builder().name("Test Place").address("123 Test St").build());
        Event event = eventDAO.save(Event.builder().name("Test Event").eventDate(LocalDate.now()).place(place).build());

        Ticket ticket1 = Ticket.builder().cost(BigDecimal.valueOf(10.0)).number(1).status(false).event(event).customer(customer).build();
        Ticket ticket2 = Ticket.builder().cost(BigDecimal.valueOf(15.0)).number(2).status(false).event(event).customer(customer).build();
        ticketDAO.saveAll(List.of(ticket1, ticket2));

        List<Ticket> tickets = ticketDAO.findAllByEventIdAndStatus(event.getId(), false);

        assertEquals(2, tickets.size(), "Expected two tickets with status false.");
        assertTrue(tickets.stream().allMatch(t -> !t.getStatus()), "All tickets should have status false.");
    }

}
