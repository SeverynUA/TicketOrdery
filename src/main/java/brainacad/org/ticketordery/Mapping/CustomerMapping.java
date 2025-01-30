package brainacad.org.ticketordery.Mapping;

import brainacad.org.ticketordery.DTO.Model.CustomerDTO;
import brainacad.org.ticketordery.Entity.Model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapping
{
    @Mapping(target = "name", source = "customer.name")
    @Mapping(target = "email", source = "customer.email")
    @Mapping(target = "phone", source = "customer.phone")
    CustomerDTO toDTO(Customer customer);

    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "email", source = "dto.email")
    @Mapping(target = "phone", source = "dto.phone")
    Customer toEntity(CustomerDTO dto);
}
