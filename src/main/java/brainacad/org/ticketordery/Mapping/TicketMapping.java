package brainacad.org.ticketordery.Mapping;

import brainacad.org.ticketordery.DTO.Model.CustomerDTO;
import brainacad.org.ticketordery.DTO.Model.PlaceDTO;
import brainacad.org.ticketordery.DTO.Model.TicketDTO;
import brainacad.org.ticketordery.Entity.Model.Customer;
import brainacad.org.ticketordery.Entity.Model.Place;
import brainacad.org.ticketordery.Entity.Model.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TicketMapping
{
    TicketDTO toDTO(Ticket ticket);
    Ticket toEntity(TicketDTO dto);
}
