package brainacad.org.ticketordery.ServiceTest.DAO.TicketServiceEntityTest;

import brainacad.org.ticketordery.Entity.Model.Ticket;
import brainacad.org.ticketordery.ServiceTest.DAO.CRUDService_Interface;

public interface TicketServiceEntityTest extends CRUDService_Interface<Ticket>
{
    void findTicketsByEventAndStatus_ReturnsCorrectTickets();
}
