package brainacad.org.ticketordery.Mapping;

import brainacad.org.ticketordery.DTO.Model.EventDTO;
import brainacad.org.ticketordery.DTO.Model.PlaceDTO;
import brainacad.org.ticketordery.DTO.Model.TicketPackDTO;
import brainacad.org.ticketordery.Entity.Model.Event;
import brainacad.org.ticketordery.Entity.Model.Place;
import brainacad.org.ticketordery.Entity.Model.Ticket;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface EventMapping
{
    Event toEntity(EventDTO dto);

    default List<TicketPackDTO> mapTickets(List<Ticket> tickets)
    {
        return tickets.stream()
                .collect(Collectors.groupingBy(
                        Ticket::getCost,
                        Collectors.summingInt(ticket -> 1)
                ))
                .entrySet()
                .stream()
                .map(entry -> TicketPackDTO.builder()
                        .cost(entry.getKey())
                        .count(entry.getValue())
                        .build())
                .collect(Collectors.toList());
    }

    EventDTO toDTO(Event entity);
}
