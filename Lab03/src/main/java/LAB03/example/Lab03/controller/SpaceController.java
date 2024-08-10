package LAB03.example.Lab03.controller;

import LAB03.example.Lab03.RequestResponse.SpaceRequest;
import LAB03.example.Lab03.RequestResponse.SpaceResponse;
import LAB03.example.Lab03.entity.Space;
import LAB03.example.Lab03.service.SpaceInputHandler;
import LAB03.example.Lab03.service.SpaceProcessor;
import LAB03.example.Lab03.service.SpaceRenderer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/spaces")
@RequiredArgsConstructor
@CrossOrigin("*")
public class SpaceController {

    private final SpaceInputHandler spaceInputHandler;
    private final SpaceProcessor spaceProcessor;
    private final SpaceRenderer spaceRenderer;

    @PostMapping("/createspaces")
    public ResponseEntity<SpaceResponse> createSpace(@RequestBody SpaceRequest spaceRequest) {
        System.out.println("Calling API");
        Space space = spaceInputHandler.processSpaceRequest(spaceRequest);
        Space savedSpace = spaceProcessor.saveSpace(space);
        SpaceResponse spaceResponse = spaceRenderer.renderSpace(savedSpace);
        return ResponseEntity.status(HttpStatus.CREATED).body(spaceResponse);
    }

    @GetMapping("/getallspaces")
    public ResponseEntity<List<SpaceResponse>> getAllSpaces() {
        List<Space> spaces = spaceProcessor.getAllSpaces();
        List<SpaceResponse> spaceResponses = spaces.stream()
                .map(spaceRenderer::renderSpace)
                .collect(Collectors.toList());
        return ResponseEntity.ok(spaceResponses);
    }

    @GetMapping("/spaces/{id}")
    public ResponseEntity<SpaceResponse> getSpaceById(@PathVariable Integer id) {
        Optional<Space> space = spaceProcessor.getSpaceById(id);
        return space.map(spaceRenderer::renderSpace)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/spaces/{id}")
    public ResponseEntity<SpaceResponse> updateSpace(@PathVariable Integer id, @RequestBody SpaceRequest spaceRequest) {
        Optional<Space> existingSpace = spaceProcessor.getSpaceById(id);

        if (existingSpace.isPresent()) {
            Space updatedSpace = spaceInputHandler.processSpaceRequest(spaceRequest);
            updatedSpace.setId(id); // Ensure the ID is set for the update
            Space savedSpace = spaceProcessor.saveSpace(updatedSpace);
            SpaceResponse spaceResponse = spaceRenderer.renderSpace(savedSpace);
            return ResponseEntity.ok(spaceResponse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/spaces/{id}")
    public ResponseEntity<Void> deleteSpace(@PathVariable Integer id) {
        Optional<Space> existingSpace = spaceProcessor.getSpaceById(id);

        if (existingSpace.isPresent()) {
            spaceProcessor.deleteSpace(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
