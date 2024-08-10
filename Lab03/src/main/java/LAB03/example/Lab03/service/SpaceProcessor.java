package LAB03.example.Lab03.service;

import LAB03.example.Lab03.entity.Space;
import LAB03.example.Lab03.repository.SpaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SpaceProcessor {

    private final SpaceRepository spaceRepository;

    public Space saveSpace(Space space) {
        return spaceRepository.save(space);
    }

    public List<Space> getAllSpaces() {
        return spaceRepository.findAll();
    }

    public Optional<Space> getSpaceById(Integer spaceId) {
        return spaceRepository.findById(spaceId);
    }

    public void deleteSpace(Integer spaceId) {
        spaceRepository.deleteById(spaceId);
    }
}
