package Utility;

import Inventory.Clue;
import Inventory.Notebook;
import Location.Location;
import People.Hero;
import People.Person;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String MAIN_HERO_PATH = "src/Resources/MainHero.json";
    private static final String LOCATIONS_PATH = "src/Resources/Locations.json";
    public static <T> T parseJsonFile(String filePath, TypeReference<T> typeReference) throws IOException {
        objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        return objectMapper.readValue(new File(filePath), typeReference);
    }

    public static List<Person> getAllPeople() throws IOException {
        return parseJsonFile("src\\Resources\\Characters.json", new TypeReference<List<Person>>() {
        });
    }

    public static List<Location> getAllLocations() throws IOException {
        return parseJsonFile(LOCATIONS_PATH, new TypeReference<List<Location>>() {
        });
    }

    public static List<Hero> getMainHero() throws IOException {
        return parseJsonFile(MAIN_HERO_PATH, new TypeReference<List<Hero>>() {
        });
    }

    public static List<Clue> getAllClues() throws IOException {
        return parseJsonFile("src/Resources/Clues.json", new TypeReference<List<Clue>>() {
        });
    }

    public static Notebook getNotebook() throws IOException {
        return parseJsonFile("src/Resources/Notebook.json", new TypeReference<Notebook>() {
        });
    }

    public static void resetLocations() {
        try {
            List<Location> locations = getAllLocations();
            for (Location location : locations) {
                location.setIsExamined(false);
                if (location.getName().equalsIgnoreCase("Janitor's Closet")) {
                    location.setAccessibility(false);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Resets the main hero's current location to the default starting location
    public static void resetMainHeroLocation() {
        try {
            Hero mainHero = getMainHero().getFirst();
            // Assuming the default starting location is known and set here
            mainHero.setCurrentLocation("Detective's Office");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

