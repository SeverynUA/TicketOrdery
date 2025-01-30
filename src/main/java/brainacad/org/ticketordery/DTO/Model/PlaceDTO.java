package brainacad.org.ticketordery.DTO.Model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlaceDTO
{
    private String name;
    private String address;
}