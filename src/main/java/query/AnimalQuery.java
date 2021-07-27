package query;

import kingdom.animalTypes.Animal;
import kingdom.AnimalService;
import kingdom.animalTypes.Pet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnimalQuery {
    protected AnimalService service;

    public AnimalQuery() {
        service = new AnimalService();
    }

    public static void main(String[] args) {
        AnimalQuery query = new AnimalQuery();

        List<String[]> validEntries = query.processEntries(args);

        for (String[] entry : validEntries) {
                String output = (entry.length > 1) ? query.sayHello(entry) : null;
                if (output != null) {
                    System.out.println(output);
                }
        }
    }

    public List<String[]> processEntries(String[] args) {
        List<String> tempList = new ArrayList<>();
        List<String[]> listOfEntries = new ArrayList<>();
        boolean performOperation = false;

        for (int i = 0; i < args.length; i++) {
            tempList.add(args[i].replace(",", ""));

            if (args[i].contains("-")) {
                performOperation = true;
            }

            if (args[i].contains(",") ) {
                if (performOperation) {
                    checkForCLIOptions(tempList.toArray(new String[tempList.size()]));
                    performOperation = false;
                } else {
                    listOfEntries.add(tempList.toArray(new String[tempList.size()]));
                }
                tempList.clear();
            }
        }

        if (performOperation) {
            checkForCLIOptions(tempList.toArray(new String[tempList.size()]));
        } else {
            listOfEntries.add(tempList.toArray(new String[tempList.size()]));
        }
        tempList.clear();

        return listOfEntries;
    }

    protected void checkForCLIOptions(String[] args) {
        int counter = 0;
        int optionIndex = -1;
        
        for (int i = 0; i < args.length; i++) {
            if (args[i].contains("-")) {
                counter++;
                optionIndex = i;
            }
        }
        
        if (counter == 1) {
            performOperation(optionIndex, Arrays.asList(args));
        } else if (counter > 1) {
            System.out.println("Cannot run multiple commands at once");
        }
    }

    void performOperation(int option, List<String> args) {
        String operation = args.get(option);
        args.remove(option);

        switch(operation.replace("-", "").toLowerCase()) {
            case "add":
                service.addAnimalType(args);
                break;
            case "remove":
                service.removeAnimalType(args.get(0));
                break;
            case "update":
                service.updateAnimalType(args);
                break;
            case "reset":
                service.resetAnimalData();
                break;
            default:
                break;
        }
    }

    protected String sayHello(String[] entry) {
        Pet pet = new Pet(entry);
        pet.setMessage(service.retrieveAnimal(pet.getType()).getMessage());

        /*if (pet == null) {
            return null;
        }*/

        if (pet.getMessage().toLowerCase().contains("real")) {
            return pet.getMessage();
        }

        return pet.getName() + " says " + pet.getMessage();
    }
}
