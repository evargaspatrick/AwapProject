package People;
import Inventory.Clue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Interactions.Dialogues;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Person {
    private String name;  // Holds the name of the person
    private String role;  // Role or occupation of the person
    private String description;  // A brief description of the person
    private String suspectReason;
    private int relationshipWithPlayer;  // Numerical value representing the relationship with the player
    private String relationshipWithVictim; // Wife, children, colleagues;
    private String usefulness;
    private String currentLocation;
    private List<String> traits;
    private List<Clue> clues;
    private double age;
    public Person() {}

    public Person(String name) {
        this.name = name;
    }

    public Person(String name,
                  String role,
                  String description,
                  String suspectReason,
                  String relationshipWithVictim,
                  String usefulness,
                  String location,
                  List<String> traits,
                  List<Clue> clues,
                  double age) {
        this.name = name;
        this.role = role;
        this.age = age;
        this.description = description;
        this.suspectReason = suspectReason;
        this.relationshipWithPlayer = 0;
        this.relationshipWithVictim = relationshipWithVictim;
        this.usefulness = usefulness;
        this.currentLocation = location;
        this.traits = traits;
        this.clues = clues;
    }

    // if there is no suspect reason and usefullness
    public Person(String name,
                  String role,
                  String description,
                  String relationshipWithVictim,
                  String location,
                  double age) {
        this.name = name;
        this.role = role;
        this.age = age;
        this.description = description;
        this.relationshipWithPlayer = 0;
        this.relationshipWithVictim = relationshipWithVictim;
        this.currentLocation = location;
    }

    public double getAge() {
        return this.age;
    }

    public List<Clue> getClues() {
        return this.clues;
    }

    // Change the traits of a person
    public void setTraits(List<String> traits) {
        this.traits = traits;
    }

    public List<String> getTraits() {
        return traits;
    }

    public String getUsefulness() {
        return this.usefulness;
    }

    public boolean isUseful() {
        return this.getUsefulness().isEmpty();
    }

    public String getSuspectReason() {
        return this.suspectReason;
    }

    public boolean isSuspect() {
        return this.getSuspectReason() != null;
    }

    public int getRelationshipWithPlayer() {
        return relationshipWithPlayer;
    }

    public boolean isTrustworthy() {
        return this.relationshipWithPlayer > 0;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() { return this.description; }

    public String getCurrentLocation() { return this.currentLocation; }

    public void setCurrentLocation(String newLocation) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        File heroFile = new File("src/Resources/MainHero.json");
        ObjectNode heroNode = (ObjectNode) objectMapper.readTree(heroFile);

        heroNode.put("currentLocation", newLocation);
        this.currentLocation = newLocation;
        // Write the updated JSON back to the file
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(heroFile, heroNode);
    }
    public String getRole() {
        return this.role;
    }

    public String getRelationshipWithVictim() {
        return relationshipWithVictim;
    }

    @Override
    public String toString() {
        return "Person Name: " + this.name + ", Role: " + this.role;
    }

}

