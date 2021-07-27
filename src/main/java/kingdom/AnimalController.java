package kingdom;

import kingdom.animalTypes.Animal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;

@Controller
public class AnimalController {
    @Autowired
    AnimalService service;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public String loadMain() {
        //return "Hello World!";
        String returnValue = loadHeaders("Animal Kingdom", true);
        returnValue += loadListOfAnimals();
        return returnValue;
    }

    public String loadListOfAnimals() {
        Set<String> dataKeys = service.getDataMap().keySet();
        String returnValue = "<ul>";

        for (String key : dataKeys) {
            returnValue += "<li><a href=\"http://localhost:8080/" + key + "\">" + key +"</a></li>";
        }

        returnValue += "</ul>";

        return returnValue;
    }

    public String loadHeaders(String header, boolean isMainPage) {
        String returnValue = "<h1>" + header + "</h1>";

        if (!isMainPage) {
            returnValue += "<a href=\"http://localhost:8080/\">Home</a><br/><br/>";
        }

        return returnValue;
    }

    @RequestMapping(value = "/{type}", method = RequestMethod.GET)
    @ResponseBody
    public String loadProfile(@PathVariable("type") String type) {
        Animal animal = service.retrieveAnimal(type);

        String returnValue = loadHeaders(type, false);

        returnValue += "Attributes: <br/><ul>";
        returnValue += "<li><b>Says: </b>" + animal.getMessage() + "</li>";
        returnValue += "</ul>";
        return returnValue;
    }
}
