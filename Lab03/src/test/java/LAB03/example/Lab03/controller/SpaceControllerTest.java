package LAB03.example.Lab03.controller;

import LAB03.example.Lab03.RequestResponse.SpaceRequest;
import LAB03.example.Lab03.RequestResponse.SpaceResponse;
import LAB03.example.Lab03.entity.Facility;
import LAB03.example.Lab03.entity.Space;
import LAB03.example.Lab03.service.SpaceInputHandler;
import LAB03.example.Lab03.service.SpaceProcessor;
import LAB03.example.Lab03.service.SpaceRenderer;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SpaceControllerTest {

    @InjectMocks
    private SpaceController spaceController;

    @Mock
    private SpaceInputHandler spaceInputHandler;

    @Mock
    private SpaceProcessor spaceProcessor;

    @Mock
    private SpaceRenderer spaceRenderer;

    @BeforeAll
    public static void beforeAll() {
        System.out.println("Executing @BeforeAll - Setup before all tests");
    }

    @BeforeEach
    public void setUp() {
        System.out.println("Executing @BeforeEach - Setup before each test");
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateSpace() {
        System.out.println("Executing testCreateSpace");


        SpaceRequest spaceRequest1 = new SpaceRequest(
                "Conference Room",
                "Floor 3",
                50,
                "A spacious conference room",
                Arrays.asList("Projector", "Whiteboard")
        );

        Facility facility1 = new Facility(1, "Projector", null);
        Facility facility2 = new Facility(2, "Whiteboard", null);

        Space space1 = Space.builder()
                .id(1)
                .name("Conference Room")
                .location("Floor 3")
                .capacity(50)
                .description("A spacious conference room")
                .facilities(Arrays.asList(facility1, facility2))
                .build();

        SpaceResponse spaceResponse1 = new SpaceResponse(space1);


        when(spaceInputHandler.processSpaceRequest(any(SpaceRequest.class))).thenReturn(space1);
        when(spaceProcessor.saveSpace(any(Space.class))).thenReturn(space1);
        when(spaceRenderer.renderSpace(any(Space.class))).thenReturn(spaceResponse1);


        ResponseEntity<SpaceResponse> response1 = spaceController.createSpace(spaceRequest1);


        assertEquals(HttpStatus.CREATED, response1.getStatusCode());
        assertEquals("Conference Room", response1.getBody().getName());
        verify(spaceInputHandler, times(1)).processSpaceRequest(spaceRequest1);
        verify(spaceProcessor, times(1)).saveSpace(space1);
        verify(spaceRenderer, times(1)).renderSpace(space1);


        SpaceRequest spaceRequest2 = new SpaceRequest(
                "Auditorium",
                "Floor 1",
                200,
                "A large auditorium",
                Arrays.asList("Sound System", "Microphone")
        );

        Facility facility3 = new Facility(3, "Sound System", null);
        Facility facility4 = new Facility(4, "Microphone", null);

        Space space2 = Space.builder()
                .id(2)
                .name("Auditorium")
                .location("Floor 1")
                .capacity(200)
                .description("A large auditorium")
                .facilities(Arrays.asList(facility3, facility4))
                .build();

        SpaceResponse spaceResponse2 = new SpaceResponse(space2);


        when(spaceInputHandler.processSpaceRequest(any(SpaceRequest.class))).thenReturn(space2);
        when(spaceProcessor.saveSpace(any(Space.class))).thenReturn(space2);
        when(spaceRenderer.renderSpace(any(Space.class))).thenReturn(spaceResponse2);


        ResponseEntity<SpaceResponse> response2 = spaceController.createSpace(spaceRequest2);


        assertEquals(HttpStatus.CREATED, response2.getStatusCode());
        assertEquals("Auditorium", response2.getBody().getName());
        verify(spaceInputHandler, times(1)).processSpaceRequest(spaceRequest2);
        verify(spaceProcessor, times(1)).saveSpace(space2);
        verify(spaceRenderer, times(1)).renderSpace(space2);
    }

    @Test
    public void testGetSpaceById() {
        System.out.println("Executing testGetSpaceById");

        // First Space
        Facility facility1 = new Facility(1, "Projector", null);
        Facility facility2 = new Facility(2, "Whiteboard", null);

        Space space1 = Space.builder()
                .id(1)
                .name("Conference Room")
                .location("Floor 3")
                .capacity(50)
                .description("A spacious conference room")
                .facilities(Arrays.asList(facility1, facility2))
                .build();

        SpaceResponse spaceResponse1 = new SpaceResponse(space1);

        // Mock the behavior of the spaceProcessor to return space1 for ID 1
        when(spaceProcessor.getSpaceById(1)).thenReturn(Optional.of(space1));
        when(spaceRenderer.renderSpace(space1)).thenReturn(spaceResponse1);

        // Test the controller's getSpaceById method
        ResponseEntity<SpaceResponse> response1 = spaceController.getSpaceById(1);


        assertEquals(HttpStatus.OK, response1.getStatusCode());
        assertEquals("Conference Room", response1.getBody().getName());
        verify(spaceProcessor, times(1)).getSpaceById(1);
        verify(spaceRenderer, times(1)).renderSpace(space1);


        Facility facility3 = new Facility(3, "Sound System", null);
        Facility facility4 = new Facility(4, "Microphone", null);

        Space space2 = Space.builder()
                .id(2)
                .name("Auditorium")
                .location("Floor 1")
                .capacity(200)
                .description("A large auditorium")
                .facilities(Arrays.asList(facility3, facility4))
                .build();

        SpaceResponse spaceResponse2 = new SpaceResponse(space2);


        when(spaceProcessor.getSpaceById(2)).thenReturn(Optional.of(space2));
        when(spaceRenderer.renderSpace(space2)).thenReturn(spaceResponse2);


        ResponseEntity<SpaceResponse> response2 = spaceController.getSpaceById(2);


        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertEquals("Auditorium", response2.getBody().getName());
        verify(spaceProcessor, times(1)).getSpaceById(2);
        verify(spaceRenderer, times(1)).renderSpace(space2);
    }

    @AfterEach
    public void tearDown() {
        System.out.println("Executing @AfterEach - Cleanup after each test");
    }

    @AfterAll
    public static void afterAll() {
        System.out.println("Executing @AfterAll - Cleanup after all tests");
    }
}
