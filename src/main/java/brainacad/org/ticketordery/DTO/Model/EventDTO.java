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

    public String getName() {return name;}
    public LocalDate getEventDate() {return eventDate;}
    public Long getPlaceId() {return placeId;}
    public List<TicketPackDTO> getTicketPacks() {return ticketPacks;}

    public void setName(String name) {this.name = name;}
    public void setEventDate(LocalDate eventDate) {this.eventDate = eventDate;}
    public void setPlaceId(Long placeId) {this.placeId = placeId;}
    public void setTicketPacks(List<TicketPackDTO> ticketPacks) {this.ticketPacks = ticketPacks;}
}
