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
    @Mapping(target = "place", source = "placeId", qualifiedByName = "placeFromId")
    Event toEntity(EventDTO dto);

    @Mapping(target = "name", source = "entity.name")
    @Mapping(target = "eventDate", source = "entity.eventDate") // Виправлено назву
    @Mapping(target = "placeId", source = "entity.place.id") // Додаємо мапінг для placeId
    @Mapping(target = "ticketPacks", source = "entity.tickets", qualifiedByName = "mapTickets")
    EventDTO toDTO(Event entity);

    @Named("mapTickets")
    default List<TicketPackDTO> mapTickets(List<Ticket> tickets) {
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

    @Named("placeFromId")
    default Place placeFromId(Long placeId) {
        if (placeId == null) return null;
        Place place = new Place();
        place.setId(placeId);
        return place;
    }
}
