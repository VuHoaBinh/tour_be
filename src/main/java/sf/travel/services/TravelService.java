package sf.travel.services;

import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import sf.travel.entities.Travel;
import sf.travel.errors.ConflictError;
import sf.travel.errors.ErrorCode;
import sf.travel.helper.specifications.TravelSpec;
import sf.travel.repositories.TravelRepository;
import sf.travel.rests.types.CreateTravelReq;
import sf.travel.rests.types.TravelFilter;
import sf.travel.rests.types.UpdateTravelReq;

@Service
@AllArgsConstructor
public class TravelService {
    @Autowired
    private static final Logger LOGGER = Logger.getLogger(TravelService.class.getName());
    private final TravelRepository travelRepo;
    private final TravelSpec travelSpecifications;

    public Travel create(CreateTravelReq input) {
        Travel travel = new Travel();
        travel.setName(input.getName());
        travel.setDescription(input.getDescription());
        travel.setPrice(input.getPrice());
        travel.setType(input.getType());
        travel.setDomain(input.getDomain());
        travel.setImage(input.getImage());
        return travelRepo.save(travel);
    }

    public Page<Travel> findAll(TravelFilter filter) {
        Specification<Travel> spec = null;
        if (filter.getName() != null) {
            spec = travelSpecifications.createSpecification("name", filter.getName());
        }
        if (filter.getType() != null) {
            spec = travelSpecifications.createSpecification("type", filter.getType());
        }
        if (filter.getSearchText() != null) {
            spec = travelSpecifications.createLikeSpecification("description", filter.getSearchText())
                                       .or(travelSpecifications.createLikeSpecification("name",
                                                                                        filter.getSearchText()));

        }
        System.out.println("Conditions: " + spec);

        Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize(), Sort.by("id").ascending());

        Page<Travel> res = travelRepo.findAll(spec, pageable);
        LOGGER.info("condition :: " + res);
        return res;
    }

    public Optional<Travel> findById(Long id) {
        return travelRepo.findById(id);
    }

    public Travel partialUpdate(Long id, UpdateTravelReq input) {
        Optional<Travel> travel = travelRepo.findById(id);

        if (travel.isPresent()) {
            Travel newTravel = travel.get();
            if (input.getName() != null) {
                newTravel.setName(input.getName());
            }
            if (input.getDescription() != null) {
                newTravel.setDescription(input.getDescription());
            }
            if (input.getPrice() != null) {
                newTravel.setPrice(input.getPrice());
            }
            if (input.getType() != null) {
                newTravel.setType(input.getType());
            }
            if (input.getImage() != null) {
                newTravel.setImage(input.getImage());
            }

            if (input.getDomain() != null) {
                newTravel.setDomain(input.getDomain());
            }

            return travelRepo.save(newTravel);
        } else {
            throw new ConflictError(ErrorCode.TRAVEL_NOT_FOUND);
        }
    }

    public void delete(Long id) {
        travelRepo.deleteById(id);
    }
}
