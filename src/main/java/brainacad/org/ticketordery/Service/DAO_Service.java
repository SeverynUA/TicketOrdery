package brainacad.org.ticketordery.Service;

import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DAO_Service<D>
{
    D create(@Valid D dto);
    D getById(@NotNull Long id);
    List<D> getAll();
    D update(@NotNull Long id, @Valid D dto);
    void delete(@NotNull Long id);
}
