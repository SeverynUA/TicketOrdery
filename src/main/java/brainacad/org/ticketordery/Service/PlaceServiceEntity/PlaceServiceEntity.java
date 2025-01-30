package brainacad.org.ticketordery.Service.PlaceServiceEntity;

import brainacad.org.ticketordery.DTO.Model.PlaceDTO;
import brainacad.org.ticketordery.Entity.Model.Place;
import brainacad.org.ticketordery.Service.DAO_Service;
import brainacad.org.ticketordery.Service.Entity_Service;

public interface PlaceServiceEntity extends DAO_Service<PlaceDTO>, Entity_Service
{
    PlaceDTO GetFindByName(String placeName);
}
