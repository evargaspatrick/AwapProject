
package Inventory;

import java.util.List;

public class Clue {
    private String name;
    private String description;
    private String location;
    private String relevance; // An assessment of how the clue relates to the case or investigation.
    private String clueType; // The type of clue (e.g., physical, testimonial, circumstantial).
    private List<Clue> connectedClues; // Array of Clue objects that are connected to this clue
    private int evidenceStrength; // from 0 to 10

    public Clue() {}
    public Clue(String name) {
        this.name = name;
    }

    public Clue(String name, String description, String location, String relevance,
                String clueType, int evidenceStrength) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.relevance = relevance;
        this.clueType = clueType;
        this.evidenceStrength = evidenceStrength;
    }

    public String getName() {
        return this.name;
    }
    public String getDescription() {
        return this.description;
    }

    public String getLocation() {
        return this.location;
    }

    public String getRelevance() {
        return this.relevance;
    }

    public String getClueType() {
        return this.clueType;
    }

    public List<Clue> getConnectedClues() {
        return this.connectedClues;
    }

    public int getEvidenceStrength() {
        return evidenceStrength;
    }

    // Setters
    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setRelevance(String relevance) {
        this.relevance = relevance;
    }

    public void setClueType(String clueType) {
        this.clueType = clueType;
    }

    // Method to connect this clue with another clue
    public void connectClue(Clue anotherClue) {
        // Logic to connect this clue with another clue
        this.connectedClues.add(anotherClue);
    }
    @Override
    public String toString() {
        return this.name; // Assuming name and description fields
    }

}


