package brainacad.org.ticketordery.DTO.Model;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventDTO
{
    private String name;
    private LocalDate eventDate;
    private Long placeId;
    private List<TicketPackDTO> ticketPacks;
}
