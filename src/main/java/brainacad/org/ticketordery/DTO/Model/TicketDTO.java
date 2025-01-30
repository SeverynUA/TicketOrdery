package brainacad.org.ticketordery.DTO.Model;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketDTO
{
    private BigDecimal cost;
    private Integer number;
    private Boolean status;
    private Long eventId;
    private Long customerId;
}