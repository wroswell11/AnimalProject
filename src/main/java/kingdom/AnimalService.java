package kingdom;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import kingdom.animalTypes.Animal;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
public class AnimalService {

    private Map<String, Animal> dataMap;

    public AnimalService() {
        dataMap = new HashMap<>();
        loadPreliminaryData();
    }

    public Map<String, Animal> getDataMap() { return dataMap; }

    void loadPreliminaryData() {
        try {
            Gson gson = new Gson();
            String jsonString = Files.readString(Path.of("src\\main\\resources\\data.json"));
            Type type = new TypeToken<HashMap<String, Animal>>(){}.getType();
            dataMap = gson.fromJson(jsonString, type);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public Animal retrieveAnimal(String type) {
        return dataMap.get(type);
    }

    public void addAnimalType(List<String> args) {
        dataMap.put(args.get(0), new Animal(args.get(1), Boolean.parseBoolean(args.get(2)), Boolean.parseBoolean(args.get(3)), Boolean.parseBoolean(args.get(4)), Boolean.parseBoolean(args.get(5))));
    }

    public void removeAnimalType(String type) {
        dataMap.remove(type);
    }

    public void updateAnimalType(List<String> args) {
        dataMap.replace(args.get(0), new Animal(args.get(1), Boolean.parseBoolean(args.get(2)), Boolean.parseBoolean(args.get(3)), Boolean.parseBoolean(args.get(4)), Boolean.parseBoolean(args.get(5))));
    }

    public void resetAnimalData() {
        try {
            Files.copy(Path.of("src\\main\\resources\\DefaultData.json"), Path.of("src\\main\\resources\\data.json"), REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void saveCurrentDataMap() {
        try {
            Gson gson = new Gson();
            String json = gson.toJson(dataMap);
            BufferedWriter bw = new BufferedWriter(new FileWriter("src\\main\\resources\\data.json", false));
            bw.write(json);
            bw.flush();
            bw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
