package brainacad.org.ticketordery.Service;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Entity_Service
{
    JpaRepository<?,?> getRepository();
}
