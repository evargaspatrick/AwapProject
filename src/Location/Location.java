package Location;
import Inventory.Clue;
import People.Person;
import Utility.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



// the class for location object
public class Location {
	private String name;
	private String description;
	private List<Clue> items;
	private List<Person> people;
	private boolean accessibility;
	private boolean isExamined;

	public Location() {}

	public Location(String name) {
		this.name = name;
	}

	public Location(String name,
					String description,
					List<Clue> items,
					List<Person> people,
					boolean accessibility,
					boolean isExamined) {
		this.name = name;
		this.description  = description;
		this.accessibility = accessibility;
		this.items = items;
		this.people = people;
		this.isExamined = isExamined;
	}

	public boolean isExamined() {
		return this.isExamined;
	}

	public void setIsExamined(boolean bool) {
		try {
			ObjectMapper mapper = new ObjectMapper();

			File file = new File("src/Resources/Locations.json");

			ArrayNode locations = (ArrayNode) mapper.readTree(file);

			// Iterate over the locations to find the correct one
			locations.forEach(location -> {
				if (location.get("name").asText().equals(this.name)) {
					// Update the isExamined property
					((ObjectNode) location).put("isExamined", bool);
				}
			});
			this.isExamined = bool;
			// Write the updated JSON back to the file
			mapper.writerWithDefaultPrettyPrinter().writeValue(file, locations);
		} catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

	public String describeLocation() {
		return this.description;
	}

	public List<Clue> getItems() {
		return this.items;
	}
	public boolean hasItem(Clue clue) {
		for (Clue existingClue : this.items) {
			if (existingClue.getName().toLowerCase().equals(clue.getName().toLowerCase())) {
				return true; // Clue is already present
			}
		}
		return false; // Clue is not present
	}
	public void setItems(List<Clue> items) {
		this.items = items;
	}
	public void setItem(Clue newItem) {
		// Ensure the items list is initialized
		if (this.items == null) {
			this.items = new ArrayList<>();
		}

		this.items.add(new Clue(newItem.getName())); // add a new item to the list of items

		try {
			ObjectMapper mapper = new ObjectMapper();
			List<Location> locations = JsonUtil.getAllLocations();

			// Find this location in the list and update its items
			for (Location location : locations) {
				if (location.getName().equals(this.name)) {
					location.setItems(this.items); // Assign the updated list
					break;
				}
			}
			File file = new File("src/Resources/Locations.json");
			// Serialize the updated list back to JSON and write it to the file
			mapper.writerWithDefaultPrettyPrinter().writeValue(file, locations);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getDescription() {
		return this.description;
	}

	public List<Person> getPeople() {
		return this.people;
	}

	public boolean getAccessibility() {
		return this.accessibility;
	}

	public void setAccessibility(boolean choice){
		try {
			ObjectMapper mapper = new ObjectMapper();

			File file = new File("src/Resources/Locations.json");

			ArrayNode locations = (ArrayNode) mapper.readTree(file);

			// Iterate over the locations to find the correct one
			locations.forEach(location -> {
				if (location.get("name").asText().equals(this.name)) {
					((ObjectNode) location).put("accessibility", choice);
				}
			});
			this.accessibility = choice;
			// Write the updated JSON back to the file
			mapper.writerWithDefaultPrettyPrinter().writeValue(file, locations);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	public String getName(){
		return this.name;
	}
	

}

