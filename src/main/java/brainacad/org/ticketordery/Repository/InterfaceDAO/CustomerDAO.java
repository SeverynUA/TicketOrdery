package brainacad.org.ticketordery.Repository.InterfaceDAO;

import brainacad.org.ticketordery.Repository.InterfaceDAO.CustomRepository.CustomRepository;
import brainacad.org.ticketordery.Entity.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerDAO extends JpaRepository<Customer,Long> , CustomRepository<Customer>
{

}
