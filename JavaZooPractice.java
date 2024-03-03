import java.util.*;
import java.io.*;
class Animal 
{
    // vars within the animal class so no interference 
    // w/ the other classes
    // these are for name, age, & species
    private String name;
    private int age;
    private String species;
    // my constructors for animal class
    public Animal(String name, int age, String species) 
    {
        // my instance vars being assigned value
        this.name = name;
        this.age = age;
        this.species = species;
    }
    // my getters and setters
    // to get acsess to my private code
    public String getName() 
    {
        return name;
    }
    public void setName(String name) 
    {
        this.name = name;
    }
    public int getAge() 
    {
        return age;
    }
    public void setAge(int age) 
    {
        this.age = age;
    }
    public String getSpecies() 
    {
        return species;
    }

    public void setSpecies(String species) 
    {
        this.species = species;
    }
}
// extends means can acsess all the code from the private animal class
class Hyena extends Animal 
{
    // my constructors for specific Hyena species
    public Hyena(String name, int age) 
    {
        super(name, age, "Hyena");
    }
}
class Lion extends Animal 
{
    // my constructor for Lion subclass
    public Lion(String name, int age) 
    {
        super(name, age, "Lion");
    }
}
class Tiger extends Animal 
{
    // my constructor for Tiger subclass
    public Tiger(String name, int age) 
    {
        super(name, age, "Tiger");
    }
}
class Bear extends Animal 
{
    // my constructor for Bear subclass
    public Bear(String name, int age) 
    {
        super(name, age, "Bear");
    }
}
public class JavaZooPractice 
{
    public static void main(String[] args) 
    {
        // arrayList are better b/c you can add to it
        ArrayList<Animal> animals = new ArrayList<>();
        // hashMaps for keeping track of species count
        HashMap<String, Integer> speciesCount = new HashMap<>();
        // for the animal names now
        HashMap<String, ArrayList<String>> animalNames = new HashMap<>();
        // now we reading the names from the file provided
        try 
        {
            BufferedReader reader = new BufferedReader(new FileReader("animalNames.txt"));
            // to hold what the buffer reads
            String line;
            String currentSpecies = "";
            while ((line = reader.readLine()) != null) 
            {
                if (line.contains("Names:")) 
                {
                    // i had it reading comas before but changed it to spaces for no out of bounds errors
                    currentSpecies = line.split(" ")[0];
                    animalNames.put(currentSpecies, new ArrayList<>());
                } else 
                {
                    String[] names = line.split(", ");
                    for (String name : names) 
                    {
                        animalNames.get(currentSpecies).add(name);
                    }
                }
            }
            reader.close();
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
        // reading animal details from arrivingAnimals file provided
        try 
        {
            BufferedReader reader = new BufferedReader(new FileReader("arrivingAnimals.txt"));
            // to hold the buffer info 
            String line;
            while ((line = reader.readLine()) != null) 
            {
                String[] parts = line.split(" ");
                String[] ageParts = parts[0].split(" ");
                int age = Integer.parseInt(ageParts[0]);
                String species = parts[5];
                Animal animal;
                // used switch for each possible outcome when it comes to species
                switch (species) 
                {
                    case "hyena":
                        animal = new Hyena(parts[6], age);
                        break;
                    case "lion":
                        animal = new Lion(parts[6], age);
                        break;
                    case "tiger":
                        animal = new Tiger(parts[6], age);
                        break;
                    case "bear":
                        animal = new Bear(parts[6], age);
                        break;
                    default:
                        animal = new Animal(parts[6], age, species);
                }
                animals.add(animal);
                speciesCount.put(species, speciesCount.getOrDefault(species, 0) + 1);
            }
            reader.close();
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
        // writing report to newAnimals file provided
        try 
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter("newAnimals.txt"));
            // to keep track of species count
            for (String species : speciesCount.keySet()) 
            {
                // write out the species count then put in the file
                writer.write("Species: " + species + ", Count: " + speciesCount.get(species) + "\n");
                for (Animal animal : animals) 
                {
                    // only if species match do we write to the file
                    if (animal.getSpecies().equalsIgnoreCase(species)) 
                    {
                        writer.write("Name: " + animal.getName() + ", Age: " + animal.getAge() + "\n");
                    }
                }
                writer.write("\n");
            }
            writer.close();
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
}
