package kingdom;

import kingdom.animalTypes.Animal;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;

class AnimalServiceTest {
    private AnimalService spyService;
    private Path copied = Path.of("src\\main\\resources\\DefaultData.json");
    private Path original = Path.of("src\\main\\resources\\data.json");


    @BeforeEach
    public void setup() {
        spyService = spy(new AnimalService());
    }

    @AfterEach
    public void teardown() {
        spyService.resetAnimalData();
    }

    @Test
    public void loadPreliminaryData_populatesDataMap() {
        spyService.loadPreliminaryData();

        Map<String, Animal> dataMap = spyService.getDataMap();

        assertEquals("moo", dataMap.get("cow").getMessage());
    }

    @Test
    public void retrieveAnimal_returnsSpecifiedAnimal() {
        Animal testCat = spyService.retrieveAnimal("cat");

        assertEquals("meow", testCat.getMessage());
    }

    @Test
    public void addAnimalType_createsNewAnimalAndAddsToTheDataMap() {
        List<String> testArgs = Arrays.asList(new String[]{"atlantic salmon", "bloop", "true", "false", "false", "false"});

        spyService.addAnimalType(testArgs);

        assertEquals("bloop", spyService.retrieveAnimal("atlantic salmon").getMessage());
    }

    @Test
    public void removeAnimalType_removesAnimalTypeFromDataMap() {
        List<String> testArgs = Arrays.asList(new String[]{"atlantic salmon", "bloop", "true", "false", "false", "false"});

        spyService.addAnimalType(testArgs);

        assertEquals("bloop", spyService.retrieveAnimal("atlantic salmon").getMessage());

        spyService.removeAnimalType(testArgs.get(0));

        assertNull(spyService.retrieveAnimal("atlantic salmon"));
    }

    @Test
    public void resetAnimalData_overwritesCurrentAnimalDataFileWithDefaultDataFile() throws IOException {
        spyService.resetAnimalData();

        assertTrue(new File(String.valueOf(copied)).exists());
        assertEquals(Files.readAllLines(original), Files.readAllLines(copied));
    }

    @Test
    public void saveCurrentDataMap_savesAnyChangesToDataJson() throws IOException {
        List<String> testArgs = Arrays.asList(new String[]{"Xenomorph", "hiss", "false", "false", "true", "false"});
        assertNull(spyService.retrieveAnimal("Xenomorph"));

        spyService.addAnimalType(testArgs);

        assertNotNull(spyService.retrieveAnimal("Xenomorph"));

        spyService.saveCurrentDataMap();

        List<String> results = Files.readAllLines(original);

        System.out.println(results.toString());
        assertTrue(Files.readAllLines(original).get(0).contains("Xenomorph"));
    }
}