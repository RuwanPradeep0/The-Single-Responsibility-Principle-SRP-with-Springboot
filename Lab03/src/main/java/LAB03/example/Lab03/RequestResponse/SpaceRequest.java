package LAB03.example.Lab03.RequestResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SpaceRequest {
    private String name;
    private String location;
    private int capacity;
    private String description;
    private List<String> facilities;
}
