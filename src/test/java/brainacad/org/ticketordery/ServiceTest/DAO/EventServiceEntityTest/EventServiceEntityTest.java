package brainacad.org.ticketordery.ServiceTest.DAO.EventServiceEntityTest;

import brainacad.org.ticketordery.Entity.Model.Event;
import brainacad.org.ticketordery.ServiceTest.DAO.CRUDService_Interface;

public interface EventServiceEntityTest extends CRUDService_Interface<Event>
{
    void findUpcomingEvents_ReturnsListOfEvents();
    void assignTicketToUser_UpdatesTicketWithCustomer();
}
