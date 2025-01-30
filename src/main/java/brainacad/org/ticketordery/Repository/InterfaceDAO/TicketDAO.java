package brainacad.org.ticketordery.Repository.InterfaceDAO;

import brainacad.org.ticketordery.Repository.InterfaceDAO.CustomRepository.CustomRepository;
import brainacad.org.ticketordery.Entity.Model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketDAO extends JpaRepository<Ticket, Long> , CustomRepository<Ticket>
{
    @Query("SELECT t FROM Ticket t WHERE t.event.id = :eventId AND t.status = :status")
    List<Ticket> findAllByEventIdAndStatus(@Param("eventId") Long eventId, @Param("status") boolean status);
}
