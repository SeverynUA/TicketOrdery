package brainacad.org.ticketordery.ServiceTest.DAO.EventServiceEntityTest;

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
public class EventServiceEntityTest_Impl extends CRUDService_Abstract<Event, EventDAO> implements EventServiceEntityTest {

    @Autowired
    private EventDAO eventDAO;

    @Autowired
    private PlaceDAO placeDAO;

    @Autowired
    private TicketDAO ticketDAO;

    @Autowired
    private CustomerDAO customerDAO;

    public EventServiceEntityTest_Impl()
    {
        super(null);
    }

    @PostConstruct
    private void initRepository()
    {
        super.repository = eventDAO;
    }


    @BeforeEach
    @Override
    public void setUpMock()
    {
        customerDAO.deleteAll();

        Place testPlace = Place.builder().name("Test Place").address("123 Test Street").build();
        Place savedPlace = placeDAO.save(testPlace);
        Event testEvent = Event.builder().name("Test Event").eventDate(LocalDate.now().plusDays(10)).place(testPlace).build();

        savedPlace = placeDAO.save(savedPlace);
        eventDAO.save(testEvent);

    }

    @Test
    @Override
    public void create_ReturnsClass_WhenValidInputProvided()
    {
        Customer customer = Customer.builder()
                .name("Test Customer")
                .email("test@customer.com")
                .phone("1234567890")
                .build();
        customer = customerDAO.save(customer);

        Place place = Place.builder().name("New Place").address("456 New Street").build();

        place = placeDAO.save(place);

        Event newEvent = Event.builder().name("New Event").eventDate(LocalDate.now().plusDays(5)).place(place).build();

        Event savedEvent = eventDAO.save(newEvent);

        assertNotNull(savedEvent);
        assertNotNull(savedEvent.getId());
        assertEquals("New Event", savedEvent.getName());
    }

    @Test
    @Override
    public void getById_ReturnsClass_WhenRequestExists()
    {
        Event savedEvent = eventDAO.findAll().get(0);

        Event foundEvent = eventDAO.findById(savedEvent.getId()).orElseThrow(() -> new RuntimeException("Event not found"));

        assertNotNull(foundEvent);
        assertEquals(savedEvent.getId(), foundEvent.getId());
    }

    @Test
    @Override
    public void delete_DoesNotThrow_WhenRequestExists()
    {
        Event savedEvent = eventDAO.findAll().get(0);

        assertDoesNotThrow(() -> eventDAO.delete(savedEvent));

        assertFalse(eventDAO.findById(savedEvent.getId()).isPresent());
    }

    @Test
    @Override
    public void findUpcomingEvents_ReturnsListOfEvents()
    {
        List<Event> upcomingEvents = eventDAO.findByEventDateAfter(LocalDate.now());

        assertNotNull(upcomingEvents);
        assertFalse(upcomingEvents.isEmpty());
        assertTrue(upcomingEvents.stream().allMatch(event -> event.getEventDate().isAfter(LocalDate.now())));
    }

    @Test
    @Override
    public void assignTicketToUser_UpdatesTicketWithCustomer()
    {
        Event event = eventDAO.findAll().get(0);

        Customer customer = Customer.builder().name("Test Customer").email("test@customer.com").phone("1234567890").build();
        customer = customerDAO.save(customer);

        Ticket ticket = Ticket.builder().cost(BigDecimal.valueOf(10.0)).number(1).status(false).customer(customer).event(event).build();
        ticket = ticketDAO.save(ticket);

        Ticket updatedTicket = ticketDAO.findById(ticket.getId()).orElseThrow(() -> new RuntimeException("Ticket not found"));

        assertNotNull(updatedTicket.getCustomer());
        assertEquals(customer.getId(), updatedTicket.getCustomer().getId());
    }

}
