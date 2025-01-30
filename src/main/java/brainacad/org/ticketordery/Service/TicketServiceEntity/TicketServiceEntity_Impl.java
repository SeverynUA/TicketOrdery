package brainacad.org.ticketordery.Service.TicketServiceEntity;

import brainacad.org.ticketordery.DTO.Model.TicketDTO;
import brainacad.org.ticketordery.Entity.Model.Customer;
import brainacad.org.ticketordery.Entity.Model.Event;
import brainacad.org.ticketordery.Entity.Model.Ticket;
import brainacad.org.ticketordery.Mapping.TicketMapping;
import brainacad.org.ticketordery.Repository.InterfaceDAO.CustomerDAO;
import brainacad.org.ticketordery.Repository.InterfaceDAO.EventDAO;
import brainacad.org.ticketordery.Repository.InterfaceDAO.TicketDAO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TicketServiceEntity_Impl implements TicketServiceEntity {

    private final TicketDAO ticketDAO;
    private final EventDAO eventDAO;
    private final CustomerDAO customerDAO;
    private final TicketMapping ticketMapping; // Mapper для Ticket ↔ TicketDTO

    @Override
    public TicketDTO create(@Valid TicketDTO model)
    {
        Event event = eventDAO.findById(model.getEventId()).orElseThrow(() -> new IllegalArgumentException("Event not found with ID: " + model.getEventId()));

        Customer customer = null;
        if (model.getCustomerId() != null)
        {
            customer = customerDAO.findById(model.getCustomerId()).orElseThrow(() -> new IllegalArgumentException("Customer not found with ID: " + model.getCustomerId()));
        }

        Ticket ticket = ticketMapping.toEntity(model);
        ticket.setEvent(event);
        ticket.setCustomer(customer);

        return ticketMapping.toDTO(ticketDAO.save(ticket));
    }

    @Override
    public TicketDTO getById(@NotNull Long id)
    {
        Ticket ticket = ticketDAO.findById(id).orElseThrow(() -> new IllegalArgumentException("Ticket not found with ID: " + id));

        return ticketMapping.toDTO(ticket);
    }

    @Override
    public List<TicketDTO> getAll()
    {
        return ticketDAO.findAll().stream().map(ticketMapping::toDTO).toList();
    }

    @Override
    public TicketDTO update(@NotNull Long id, @Valid TicketDTO model)
    {
        Ticket existingTicket = ticketDAO.findById(id).orElseThrow(() -> new IllegalArgumentException("Ticket not found with ID: " + id));

        if (model.getCost() != null) existingTicket.setCost(model.getCost());
        if (model.getNumber() != null) existingTicket.setNumber(model.getNumber());
        if (model.getStatus() != null) existingTicket.setStatus(model.getStatus());

        if (model.getEventId() != null)
        {
            Event event = eventDAO.findById(model.getEventId()).orElseThrow(() -> new IllegalArgumentException("Event not found with ID: " + model.getEventId()));
            existingTicket.setEvent(event);
        }

        if (model.getCustomerId() != null)
        {
            Customer customer = customerDAO.findById(model.getCustomerId()).orElseThrow(() -> new IllegalArgumentException("Customer not found with ID: " + model.getCustomerId()));
            existingTicket.setCustomer(customer);
        }

        return ticketMapping.toDTO(ticketDAO.save(existingTicket));
    }

    @Override
    public void delete(@NotNull Long id)
    {
        if (!ticketDAO.existsById(id))
        {
            throw new IllegalArgumentException("Ticket not found with ID: " + id);
        }

        ticketDAO.deleteById(id);
    }

    @Override
    public JpaRepository<?, ?> getRepository() {return ticketDAO;}
}
