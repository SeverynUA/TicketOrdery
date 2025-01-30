package brainacad.org.ticketordery.DTO.Model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDTO
{
    private String name;
    private String email;
    private String phone;
}
