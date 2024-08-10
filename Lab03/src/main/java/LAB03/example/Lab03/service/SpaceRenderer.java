package LAB03.example.Lab03.service;

import LAB03.example.Lab03.RequestResponse.SpaceResponse;
import LAB03.example.Lab03.entity.Space;
import org.springframework.stereotype.Component;

@Component
public class SpaceRenderer {

    public SpaceResponse renderSpace(Space space) {
        return new SpaceResponse(space);
    }
}
