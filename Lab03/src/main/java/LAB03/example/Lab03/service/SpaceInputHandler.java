package LAB03.example.Lab03.service;

import LAB03.example.Lab03.RequestResponse.SpaceRequest;
import LAB03.example.Lab03.entity.Space;
import LAB03.example.Lab03.entity.Facility;
import LAB03.example.Lab03.repository.FacilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SpaceInputHandler {

    private final FacilityRepository facilityRepository;

    public Space processSpaceRequest(SpaceRequest spaceRequest) {
        List<Facility> facilities = new ArrayList<>();
        for (String facilityName : spaceRequest.getFacilities()) {
            Facility facility = facilityRepository.findByName(facilityName)
                    .orElseGet(() -> {
                        Facility newFacility = new Facility();
                        newFacility.setName(facilityName);
                        return facilityRepository.save(newFacility);
                    });
            facilities.add(facility);
        }

        return Space.builder()
                .name(spaceRequest.getName())
                .location(spaceRequest.getLocation())
                .capacity(spaceRequest.getCapacity())
                .description(spaceRequest.getDescription())
                .facilities(facilities)
                .build();
    }
}
