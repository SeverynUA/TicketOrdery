package brainacad.org.ticketordery.Service.EventServiceEntity;

import brainacad.org.ticketordery.DTO.Model.EventDTO;
import brainacad.org.ticketordery.DTO.Model.TicketDTO;
import brainacad.org.ticketordery.Entity.Model.Event;
import brainacad.org.ticketordery.Entity.Model.Ticket;
import brainacad.org.ticketordery.Service.DAO_Service;
import brainacad.org.ticketordery.Service.Entity_Service;

import java.util.List;

public interface EventServiceEntity extends DAO_Service<EventDTO>, Entity_Service
{
    List<TicketDTO> findAvailableTickets(Long eventId);
    List<EventDTO> findUpcomingEvents();
    void assignTicketToUser(Long ticketId, Long customerId);
}
