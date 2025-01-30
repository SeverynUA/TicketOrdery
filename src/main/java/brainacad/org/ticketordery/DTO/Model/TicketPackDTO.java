package brainacad.org.ticketordery.DTO.Model;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketPackDTO
{
    private BigDecimal cost;
    private int count;
}