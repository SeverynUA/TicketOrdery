package brainacad.org.ticketordery.ServiceTest.DAO.PlaceServiceEntityTest;

import brainacad.org.ticketordery.Entity.Model.Place;
import brainacad.org.ticketordery.Repository.InterfaceDAO.PlaceDAO;
import brainacad.org.ticketordery.ServiceTest.DAO.CRUDService_Abstract;
import brainacad.org.ticketordery.TicketOrderyApplication;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TicketOrderyApplication.class)
@ActiveProfiles("test")
public class PlaceServiceEntityTest_Impl extends CRUDService_Abstract<Place, PlaceDAO> implements PlaceServiceEntityTest {

    @Autowired
    private PlaceDAO placeDAO;

    public PlaceServiceEntityTest_Impl()
    {
        super(null);
    }

    @PostConstruct
    private void initRepository()
    {
        super.repository = placeDAO;
    }

    @BeforeEach
    @Override
    public void setUpMock()
    {
        placeDAO.save(Place.builder().name("Test Place 1").address("123 Test Street").build());
        placeDAO.save(Place.builder().name("Test Place 2").address("456 Another Street").build());
    }

    @Test
    @Override
    public void create_ReturnsClass_WhenValidInputProvided()
    {
        Place newPlace = Place.builder().name("New Place").address("789 New Avenue").build();
        Place savedPlace = placeDAO.save(newPlace);

        assertNotNull(savedPlace);
        assertNotNull(savedPlace.getId());
        assertEquals("New Place", savedPlace.getName());
    }

    @Test
    @Override
    public void getById_ReturnsClass_WhenRequestExists()
    {
        Place savedPlace = placeDAO.findAll().get(0);
        Place foundPlace = placeDAO.findById(savedPlace.getId()).orElseThrow(() -> new RuntimeException("Place not found"));

        assertNotNull(foundPlace);
        assertEquals(savedPlace.getId(), foundPlace.getId());
    }

    @Test
    @Override
    public void delete_DoesNotThrow_WhenRequestExists()
    {
        Place savedPlace = placeDAO.findAll().get(0);

        assertDoesNotThrow(() -> placeDAO.delete(savedPlace));

        assertFalse(placeDAO.findById(savedPlace.getId()).isPresent());
    }
}
