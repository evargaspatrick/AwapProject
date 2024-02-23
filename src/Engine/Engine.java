package Engine;

import Inventory.Notebook;
import Location.Location;
import People.*;
import Utility.JsonUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Engine {
    public static void resetGameState() {
        try {
            JsonUtil.resetLocations();
            JsonUtil.resetMainHeroLocation();

            ObjectMapper mapper = new ObjectMapper();

            mapper.writerWithDefaultPrettyPrinter().writeValue(new File("src/Resources/Notebook.json"), new Notebook());

        } catch (IOException e) {
            new RuntimeException(e);
        }
    }
    // the method for moving
    public static void move(int index, JTextArea textArea) throws IOException {
        // changes the location of the main hero
        List<Location> locations = JsonUtil.getAllLocations();

        if (index < locations.size() && index >= 0) {
            Hero hero = JsonUtil.getMainHero().get(0);
            hero.setCurrentLocation((locations.get(index).getName()));
            textArea.setText("You are in " + locations.get(index).getName());
        }
    }

    // The method for getting the location
    public static Location getLocation(int index) throws IOException {
        List<Location> locations = JsonUtil.getAllLocations();

        return locations.get(index);
    }

    // The method reads the file from json of locations and puts it into interactions
    public static ArrayList<String> interact(JPanel gamePlayPanel) throws IOException {
        // Specify the path to your JSON file
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<String> items = new ArrayList<>();
        File file = new File("src/Resources/Locations.json");
        String Location = currLocation();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(file);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        JsonNode foundLocation = null;
        if (jsonNode.isArray()) {
            for (JsonNode location : jsonNode) {
                if (location.get("isExamined").asBoolean() && location.get("name").asText().equals(Location)) {
                    foundLocation = location;
                    break;
                } else if (!location.get("isExamined").asBoolean() && location.get("name").asText().equals(Location)) {
                    JOptionPane.showMessageDialog(gamePlayPanel, "You need to search the room first", "Warning", JOptionPane.WARNING_MESSAGE);
                    return null;
                }

            }
        }

        JsonNode itemsNode = foundLocation.get("items");
        for (JsonNode item : itemsNode) {
            items.add(item.get("name").asText());
        }

        return items;
    }

    public static ArrayList<String> getPeopleNames() throws IOException {
        ArrayList<String> peopleNames = new ArrayList<>();
        List<Location> locations = JsonUtil.getAllLocations();
        for (Location location : locations) {
            if (location.getName().equals(currLocation())) {
                for (Person person : location.getPeople()) {
                    peopleNames.add(person.getName());
                }
            }

        }
        return peopleNames;
    }

    public static String currLocation() throws IOException {
        // return the current location of a main hero
        Hero hero = JsonUtil.getMainHero().get(0);
        return (hero.getCurrentLocation());
    }

    public static void addItems(String item){
        ObjectMapper mapper = new ObjectMapper();

        try {
            File jsonFile = new File("src/Resources/Notebook.json");

            // Parse JSON file
            ObjectNode rootNode = (ObjectNode) mapper.readTree(jsonFile);

            // Get the "clues" array
            ArrayNode cluesNode = (ArrayNode) rootNode.get("clues");

            // Add items to the "clues" array
            cluesNode.add(item);

            // Write the updated rootNode back to the file
            mapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, rootNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
