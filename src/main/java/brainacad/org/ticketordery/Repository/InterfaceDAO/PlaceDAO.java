package brainacad.org.ticketordery.Repository.InterfaceDAO;

import brainacad.org.ticketordery.Repository.InterfaceDAO.CustomRepository.CustomRepository;
import brainacad.org.ticketordery.Entity.Model.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlaceDAO extends JpaRepository<Place, Long> , CustomRepository<Place>
{
    Optional<Place> findByName(String name);
}
