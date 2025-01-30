package brainacad.org.ticketordery.Service.PlaceServiceEntity;

import brainacad.org.ticketordery.DTO.Model.PlaceDTO;
import brainacad.org.ticketordery.Entity.Model.Place;
import brainacad.org.ticketordery.Mapping.PlaceMapping;
import brainacad.org.ticketordery.Repository.InterfaceDAO.PlaceDAO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PlaceServiceEntity_Impl implements PlaceServiceEntity {

    private final PlaceDAO placeDAO;
    private final PlaceMapping placeMapping;

    @Override
    public PlaceDTO create(@Valid PlaceDTO model)
    {
        Place place = placeMapping.toEntity(model);
        Place savedPlace = placeDAO.save(place);

        return placeMapping.toDTO(savedPlace);
    }

    @Override
    public PlaceDTO getById(@NotNull Long id)
    {
        return placeDAO.findById(id).map(placeMapping::toDTO).orElseThrow(() -> new RuntimeException("Place with ID " + id + " not found"));
    }

    @Override
    public List<PlaceDTO> getAll()
    {
        List<Place> places = placeDAO.findAll();

        return places.stream().map(placeMapping::toDTO).toList();
    }

    @Override
    public PlaceDTO update(@NotNull Long id, PlaceDTO model)
    {
        Place existingPlace = placeDAO.findById(id).orElseThrow(() -> new RuntimeException("Place with ID " + id + " not found"));
        Place existingPlace_temp = placeMapping.toEntity(model);
        existingPlace_temp.setId(existingPlace.getId());

        Place updatedPlace =  placeDAO.save(existingPlace);

        return placeMapping.toDTO(updatedPlace);
    }

    @Override
    public void delete(@NotNull Long id)
    {
        Place place = placeDAO.findById(id).orElseThrow(() -> new RuntimeException("Place with ID " + id + " not found"));
        placeDAO.delete(place);
    }

    @Override
    public JpaRepository<?, ?> getRepository() {return placeDAO;}

    @Override
    public PlaceDTO GetFindByName(@NotNull String placeName)
    {
        return placeDAO.findByName(placeName).map(placeMapping::toDTO).orElseThrow(() -> new RuntimeException("Place with name " + placeName + " not found"));
    }
}
