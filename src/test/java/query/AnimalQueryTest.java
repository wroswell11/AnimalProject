package query;

import kingdom.animalTypes.Animal;
import kingdom.AnimalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AnimalQueryTest {
    private AnimalQuery spyQuery;
    private AnimalService mockService;
    private Animal testCatAnimal;
    private Animal testUnrealAnimal;

    @BeforeEach
    public void setup() {
        spyQuery = spy(new AnimalQuery());
        mockService = mock(AnimalService.class);

        spyQuery.service = mockService;

        testCatAnimal = new Animal("meow");
        testUnrealAnimal = new Animal("Unicorns are not real");
    }

    @Test
    public void sayHello_returnsAnimalMessage_ifAnimalIsReal() {
        when(mockService.retrieveAnimal(anyString())).thenReturn(testCatAnimal);

        assertEquals("TestCat says meow", spyQuery.sayHello(new String[]{"TestCat", "cat"}));
    }

    @Test
    public void sayHello_returnsNotRealMessage_ifAnimalIsNotReal() {
        when(mockService.retrieveAnimal(anyString())).thenReturn(testUnrealAnimal);

        assertEquals("Unicorns are not real", spyQuery.sayHello(new String[]{"TestUnicorn", "unicorn"}));
    }

    /*@Test
    public void sayHello_returnsNothing_ifAnimalIsUnknown() {
        when(mockService.retrieveAnimal(anyString())).thenReturn(null);

        assertNull(spyQuery.sayHello("TestXenomorph", "xenomorph"));
    }*/

    @Test
    public void checkForCLIOptions_doesNothing_ifNoOptionsEntered() {
        String[] testArgs = {"hello", "world"};

        spyQuery.checkForCLIOptions(testArgs);

        verify(spyQuery, never()).performOperation(anyInt(), any(List.class));
    }

    @Test
    public void checkForCLIOptions_onlyAcceptsOneOptionAtATime_ifMultipleOptionsEntered() {
        String[] testArgs = {"-add", "-help"};

        spyQuery.checkForCLIOptions(testArgs);

        verify(spyQuery, never()).performOperation(anyInt(), any(List.class));
    }

    @Test
    public void checkForCLIOptions_performsOperation_ifReady() {
        String[] testArgs = {"-add"};

        doNothing().when(spyQuery).performOperation(anyInt(), any(List.class));

        spyQuery.checkForCLIOptions(testArgs);

        verify(spyQuery, times(1)).performOperation(anyInt(), any(List.class));
    }

    @Test
    public void performOperation_doesNothing_ifOperationNotSupported() {
        List<String> testArgs = new ArrayList<>();
        testArgs.add("-help");

        spyQuery.performOperation(0, testArgs);

        verify(mockService, never()).addAnimalType(any(List.class));
        verify(mockService, never()).removeAnimalType(anyString());
        verify(mockService, never()).updateAnimalType(any(List.class));
        verify(mockService, never()).resetAnimalData();
    }

    @Test
    public void performOperation_performsAddOperation_ifAddOptionIsEntered() {
        ArrayList<String> testArgs = new ArrayList<>();
        testArgs.add("-add");
        testArgs.add("hello");


        doNothing().when(mockService).addAnimalType(any(List.class));

        spyQuery.performOperation(0, testArgs);

        verify(mockService, times(1)).addAnimalType(any(List.class));
        verify(mockService, never()).removeAnimalType(anyString());
        verify(mockService, never()).updateAnimalType(any(List.class));
        verify(mockService, never()).resetAnimalData();
    }

    @Test
    public void performOperation_performsRemoveOperation_ifRemoveOptionIsEntered() {
        ArrayList<String> testArgs = new ArrayList<>();
        testArgs.add("-remove");
        testArgs.add("hello");

        doNothing().when(mockService).removeAnimalType(anyString());

        spyQuery.performOperation(0, testArgs);

        verify(mockService, never()).addAnimalType(any(List.class));
        verify(mockService, times(1)).removeAnimalType(anyString());
        verify(mockService, never()).updateAnimalType(any(List.class));
        verify(mockService, never()).resetAnimalData();
    }

    @Test
    public void performOperation_performsUpdateOperation_ifUpdateOptionIsEntered() {
        ArrayList<String> testArgs = new ArrayList<>();
        testArgs.add("-update");
        testArgs.add("hello");

        doNothing().when(mockService).updateAnimalType(testArgs);

        spyQuery.performOperation(0, testArgs);

        verify(mockService, never()).addAnimalType(any(List.class));
        verify(mockService, never()).removeAnimalType(anyString());
        verify(mockService, times(1)).updateAnimalType(any(List.class));
        verify(mockService, never()).resetAnimalData();
    }

    @Test
    public void performOperation_performsResetOperation_ifResetOptionIsEntered() {
        List<String> testArgs = new ArrayList<>();
        testArgs.add("-reset");

        doNothing().when(mockService).resetAnimalData();

        spyQuery.performOperation(0, testArgs);

        verify(mockService, never()).addAnimalType(any(List.class));
        verify(mockService, never()).removeAnimalType(anyString());
        verify(mockService, never()).updateAnimalType(any(List.class));
        verify(mockService, times(1)).resetAnimalData();
    }

    @Test
    public void processEntries_processesSingleNormalEntry() {
        String[] testArgs = {"Mr Pickles", "cat"};

        List<String[]> resultList = spyQuery.processEntries(testArgs);

        assertEquals("Mr Pickles", resultList.get(0)[0]);
    }

    @Test
    public void processEntries_processesMultipleNormalEntries() {
        String[] testArgs = {"Mr Pickles", "cat,", "Bowser", "dog,", "Moo", "cow"};

        List<String[]> resultList = spyQuery.processEntries(testArgs);

        assertEquals("Mr Pickles", resultList.get(0)[0]);
        assertEquals("cat", resultList.get(0)[1]);
        assertEquals("Bowser", resultList.get(1)[0]);
        assertEquals("dog", resultList.get(1)[1]);
        assertEquals("Moo", resultList.get(2)[0]);
        assertEquals("cow", resultList.get(2)[1]);
    }

    @Test
    public void processEntries_processesEntryWithOption() {
        String[] testArgs = {"-add", "cat"};

        doNothing().when(spyQuery).checkForCLIOptions(any(String[].class));

        List<String[]> resultList = spyQuery.processEntries(testArgs);

        verify(spyQuery, times(1)).checkForCLIOptions(any(String[].class));

        assertTrue(resultList.isEmpty());
    }

    @Test
    public void processEntries_processesOptionsWithinMultipleEntries() {
        String[] testArgs = {"-add", "Xenomorph", "hiss", "false", "false", "true", "false,", "Fluffy", "xenomorph", "Any Living Thing", "7", "250", "10,", "Mr Pickles", "cat"};

        doNothing().when(spyQuery).checkForCLIOptions(any(String[].class));

        List<String[]> resultList = spyQuery.processEntries(testArgs);

        verify(spyQuery, times(1)).checkForCLIOptions(any(String[].class));

        assertEquals(2, resultList.size());
    }

    @Test
    public void processEntries_processesMultipleOptionsWithinMultipleEntries() {
        String[] testArgs = {"-add", "Xenomorph", "hiss", "false", "false", "true", "false,", "Fluffy", "xenomorph", "Any Living Thing", "7", "250", "10,", "Mr Pickles", "cat,", "-remove", "dog"};

        doNothing().when(spyQuery).checkForCLIOptions(any(String[].class));

        List<String[]> resultList = spyQuery.processEntries(testArgs);

        verify(spyQuery, times(2)).checkForCLIOptions(any(String[].class));

        assertEquals(2, resultList.size());
    }
}