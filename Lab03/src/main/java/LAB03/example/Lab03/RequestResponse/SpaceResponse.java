package LAB03.example.Lab03.RequestResponse;


import LAB03.example.Lab03.entity.Facility;
import LAB03.example.Lab03.entity.Space;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SpaceResponse {

    private Integer id;
    private String name;
    private String location;
    private int capacity;
    private String description;
    private List<String> facilitiesList;

    // Constructor-based mapping
    public SpaceResponse(Space space) {
        this.id = space.getId();
        this.name = space.getName();
        this.location = space.getLocation();
        this.capacity = space.getCapacity();
        this.description = space.getDescription();
        this.facilitiesList = space.getFacilities() != null ?
                space.getFacilities().stream()
                        .map(Facility::getName)
                        .collect(Collectors.toList()) :
                new ArrayList<>();
    }


}
