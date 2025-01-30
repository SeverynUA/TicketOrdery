package brainacad.org.ticketordery.Service.EventServiceEntity;

import brainacad.org.ticketordery.DTO.Model.EventDTO;
import brainacad.org.ticketordery.DTO.Model.TicketDTO;
import brainacad.org.ticketordery.Entity.Model.Customer;
import brainacad.org.ticketordery.Entity.Model.Event;
import brainacad.org.ticketordery.Entity.Model.Place;
import brainacad.org.ticketordery.Entity.Model.Ticket;
import brainacad.org.ticketordery.Mapping.EventMapping;
import brainacad.org.ticketordery.Mapping.TicketMapping;
import brainacad.org.ticketordery.Repository.InterfaceDAO.CustomerDAO;
import brainacad.org.ticketordery.Repository.InterfaceDAO.EventDAO;
import brainacad.org.ticketordery.Repository.InterfaceDAO.PlaceDAO;
import brainacad.org.ticketordery.Repository.InterfaceDAO.TicketDAO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class EventServiceEntity_Impl implements EventServiceEntity
{
    private final PlaceDAO placeDAO;
    private final EventDAO eventDAO;
    private final TicketDAO ticketDAO;
    private final CustomerDAO customerDAO;
    private final EventMapping eventMapping; // Для перетворення Event ↔ EventDTO
    private final TicketMapping ticketMapping; // Для перетворення Ticket ↔ TicketDTO

    @Override
    public JpaRepository<?, ?> getRepository() {
        return eventDAO;
    }

    @Override
    public List<TicketDTO> findAvailableTickets(Long eventId)
    {
        Event event = eventDAO.findById(eventId).orElseThrow(() -> new RuntimeException("Event with ID " + eventId + " not found"));

        return ticketDAO.findAllByEventIdAndStatus(event.getId(), false).stream().map(ticketMapping::toDTO).toList();
    }

    @Override
    public List<EventDTO> findUpcomingEvents()
    {
        LocalDate today = LocalDate.now();

        return eventDAO.findByEventDateAfter(today).stream().map(eventMapping::toDTO).toList();
    }

    @Override
    public void assignTicketToUser(Long ticketId, Long customerId)
    {
        Customer customer = customerDAO.findById(customerId).orElseThrow(() -> new RuntimeException("Customer with ID " + customerId + " not found"));

        Ticket ticket = ticketDAO.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket with ID " + ticketId + " not found"));

        if (ticket.getStatus())
        {
            throw new RuntimeException("Ticket with ID " + ticketId + " is already assigned to another customer");
        }

        ticket.setCustomer(customer);
        ticket.setStatus(true);
        ticketDAO.save(ticket);
    }

    @Override
    public EventDTO create(EventDTO dto)
    {
        Event event = eventMapping.toEntity(dto);

        List<Ticket> tickets = dto.getTicketPacks().stream()
                .flatMap(pack ->
                {
                    List<Ticket> packTickets = new ArrayList<>();
                    for (int i = 0; i < pack.getCount(); i++)
                    {
                        Ticket ticket = Ticket.builder()
                                .event(event)
                                .cost(pack.getCost())
                                .status(false)
                                .build();
                        packTickets.add(ticket);
                    }
                    return packTickets.stream();
                })
                .toList();

        event.setTickets(tickets);

        return eventMapping.toDTO(eventDAO.save(event));
    }

    @Override
    public EventDTO getById(@NotNull Long id)
    {
        Event event = eventDAO.findById(id).orElseThrow(() -> new RuntimeException("Event with ID " + id + " not found"));

        return eventMapping.toDTO(event);
    }

    @Override
    public List<EventDTO> getAll()
    {
        return eventDAO.findAll().stream().map(eventMapping::toDTO).toList();
    }

    @Override
    public EventDTO update(@NotNull Long id, @Valid EventDTO dto)
    {
        Event existingEvent = eventDAO.findById(id).orElseThrow(() -> new RuntimeException("Event with ID " + id + " not found"));
        Place place = placeDAO.findById(dto.getPlaceId()).orElseThrow(() -> new RuntimeException("Place with ID " + dto.getPlaceId() + " not found"));

        Event existingEvent_temp = eventMapping.toEntity(dto);
        existingEvent_temp.setId(existingEvent.getId());
        existingEvent_temp.setPlace(place);

        Event updatedEvent = eventDAO.save(existingEvent_temp);

        return  eventMapping.toDTO(updatedEvent);
    }


    @Override
    public void delete(@NotNull Long id)
    {
        Event event = eventDAO.findById(id).orElseThrow(() -> new RuntimeException("Event with ID " + id + " not found"));

        eventDAO.delete(event);
    }
}
