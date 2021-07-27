package kingdom;

import kingdom.animalTypes.Animal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AnimalControllerTest {
    private AnimalController spyController;
    private AnimalService mockService;

    @BeforeEach
    public void setUp() {
        spyController = spy(new AnimalController());
        mockService = mock(AnimalService.class);

        spyController.service = mockService;
    }

    @Test
    public void loadMain_displayHeaderAndAnimalList() {
        when(spyController.loadHeaders(anyString(), anyBoolean())).thenReturn("Test1");
        doReturn("Test2").when(spyController).loadListOfAnimals();

        String result = spyController.loadMain();

        assertTrue(result.contains("Test1"));
        assertTrue(result.contains("Test2"));

        verify(spyController, times(1)).loadHeaders(anyString(), anyBoolean());
        verify(spyController, times(1)).loadListOfAnimals();
    }

    @Test
    public void loadHeaders_addsHeader_ifMainPageNoNavigation() {
        String result = spyController.loadHeaders("Test Header", true);

        assertTrue(result.contains("Test Header"));
        assertFalse(result.contains("Home"));
    }

    @Test
    public void loadHeaders_addsHeader_ifNotMainPageAddNavigation() {
        String result = spyController.loadHeaders("Test Header", true);

        assertTrue(result.contains("Test Header"));
        assertFalse(result.contains("Home"));
    }

    @Test
    public void loadListOfAnimals_showListOfAllAnimals_asLinks() {
        Map<String, Animal> testMap = new HashMap<>();
        testMap.put("cat", new Animal());
        testMap.put("dog", new Animal());

        when(mockService.getDataMap()).thenReturn(testMap);

        String result = spyController.loadListOfAnimals();

        assertTrue(result.contains("<a href=\"http://localhost:8080/cat\""));
        assertTrue(result.contains("<a href=\"http://localhost:8080/dog\""));
    }

    @Test
    public void loadProfile_displaysAnimalTypeAndCharacteristics() {
        Animal testAnimal = new Animal("meow");

        when(mockService.retrieveAnimal(anyString())).thenReturn(testAnimal);

        String result = spyController.loadProfile("cat");

        assertTrue(result.contains("cat"));
        assertTrue(result.contains("Home</a>"));
        assertTrue(result.contains("meow"));

        verify(spyController, times(1)).loadHeaders(anyString(), anyBoolean());
    }
}