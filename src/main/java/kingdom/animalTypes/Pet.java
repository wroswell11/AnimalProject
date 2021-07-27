package kingdom.animalTypes;

public class Pet extends Animal{
    private String name;
    private String type;
    private String favFood;

    private int height;
    private int weight;
    private int age;

    public Pet(String[] args) {
        name = args[0];
        type = args[1];

        if (args.length > 3) {
            favFood = args[2];
        }

        if (args.length > 4) {
            height = Integer.parseInt(args[3]);
        }

        if (args.length > 5) {
            height = Integer.parseInt(args[4]);
        }

        if (args.length > 6) {
            height = Integer.parseInt(args[5]);
        }
    }

    public String getName() {
        return name;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFavFood() {
        return favFood;
    }

    public void setFavFood(String favFood) {
        this.favFood = favFood;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
