package brainacad.org.ticketordery.Service.CustomerServiceEntity;

import brainacad.org.ticketordery.DTO.Model.CustomerDTO;
import brainacad.org.ticketordery.Entity.Model.Customer;
import brainacad.org.ticketordery.Entity.Model.Place;
import brainacad.org.ticketordery.Mapping.CustomerMapping;
import brainacad.org.ticketordery.Repository.InterfaceDAO.CustomerDAO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerServiceEntity_Impl implements CustomerServiceEntity
{
    private final CustomerDAO customerDAO;
    private final CustomerMapping customerMapping;

    @Override
    public JpaRepository<?, ?> getRepository() {return customerDAO;}

    @Override
    public CustomerDTO create(@Valid CustomerDTO dto)
    {
        Customer customer = customerMapping.toEntity(dto);
        Customer savedCustomer = customerDAO.save(customer);

        return customerMapping.toDTO(savedCustomer);
    }

    @Override
    public CustomerDTO getById(@NotNull Long id)
    {
        return customerDAO.findById(id).map(customerMapping::toDTO).orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
    }

    @Override
    public List<CustomerDTO> getAll()
    {
        List<Customer> customers = customerDAO.findAll();

        if (customers.isEmpty())
        {
            throw new RuntimeException("No customers found");
        }

        return customers.stream().map(customerMapping::toDTO).toList();
    }

    @Override
    public CustomerDTO update(@NotNull Long id, @Valid CustomerDTO dto)
    {
        Customer existingCustomer = customerDAO.findById(id).orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));

        Customer existingCustomer_temp = customerMapping.toEntity(dto);
        existingCustomer_temp.setId(existingCustomer.getId());

        Customer updatedCustomer = customerDAO.save(existingCustomer_temp);

        return customerMapping.toDTO(updatedCustomer);
    }

    @Override
    public void delete(@NotNull Long id)
    {
        Customer existingCustomer = customerDAO.findById(id).orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));

        customerDAO.delete(existingCustomer);
    }
}


