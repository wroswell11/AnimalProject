package kingdom.animalTypes;

public class Animal {
    String message;
    /*private int avgAge;
    private int avgHeight;
    private int avgWeight;
    private String favFood;
    private String color;*/
    private boolean isAquatic;
    private boolean isAerial;
    private boolean isExtraterrestrial;
    private boolean isMythological;

    public Animal() {}

    public Animal(String message) {
        this.message = message;
    }

    public Animal(String message, boolean isAquatic, boolean isAerial, boolean isExtraterrestrial, boolean isMythological) {
        this.message = message;
        this.isAquatic = isAquatic;
        this.isAerial = isAerial;
        this.isExtraterrestrial = isExtraterrestrial;
        this.isMythological = isMythological;
    }

    public String getMessage() { return message; }
}
