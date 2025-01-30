package brainacad.org.ticketordery.Repository.InterfaceDAO;

import brainacad.org.ticketordery.Repository.InterfaceDAO.CustomRepository.CustomRepository;
import brainacad.org.ticketordery.Entity.Model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventDAO extends JpaRepository<Event, Long> , CustomRepository<Event>
{
    List<Event> findByEventDateAfter(LocalDate date);
}
