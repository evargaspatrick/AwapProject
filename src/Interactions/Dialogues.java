
package Interactions;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import People.*;
import Location.*;
import ChatGPT.ChatGPT;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

// the dialogue class for interacting with npc
public class Dialogues {
    private String location;
    private Hero mainHero;
    private Person character;
    private ArrayList<DialogueEntry> dialogueHistory;

    public Dialogues(Hero mainHero, Person character, String location) throws IOException {
        this.mainHero = mainHero;
        this.character = character;
        this.location = location;

        this.dialogueHistory = new ArrayList<>();
    }

    public String processPlayerInput(String input) {
        // this method just process the conversation
        DialogueEntry userEntry = new DialogueEntry(mainHero.getName(), input);
        String prompt = generateChatGPTPrompt(input);
        System.out.println(prompt);
        updateDialogueHistory(userEntry);
        String response = String.valueOf(ChatGPT.chatGPT(prompt));
        DialogueEntry botEntry = new DialogueEntry(character.getName(), response);
        updateDialogueHistory(botEntry);
        return response;
    }

    // the method for adding dialogue entry to the chat history
    private void updateDialogueHistory(DialogueEntry entry) {
        this.dialogueHistory.add(entry);
    }

    public Person getCharacter() {
        return this.character;
    }

    private static void writeHistory(DialogueEntry history) {
        //This method will write the chats to the json file
        //we will use this json file to print chats to the console
        try (FileWriter fw = new FileWriter("src/Resources/ChatHistory.json", true)) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writerWithDefaultPrettyPrinter().writeValue(fw, history);
            fw.write(System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String generateChatGPTPrompt(String userInput) {
        StringBuilder promptBuilder = new StringBuilder();

        // Story and character introduction
        String storyDescription = getStoryDescription(); // Assuming this fetches a concise background story
        promptBuilder.append("Story: ").append(storyDescription).append("\n\n");

        // Character introduction
        promptBuilder.append(character.getName()).append(", the janitor at Mega Stadium, known for his dedication, offers assistance to Detective Collins in the investigation of Coach Harrington's disappearance. ")
                .append(character.getName())
                .append(" emphasizes the importance of vigilance and offers to provide access to less-visible areas of the stadium. He addresses any suspicions about his involvement, asserting his role is to maintain the stadium's cleanliness and security, not to meddle in dark affairs. ")
                .append(character.getName())
                .append(" then inquires how he can assist further, specifically asking if access to any locked areas is needed.").append("\n\n");

        // Recent conversation history
        promptBuilder.append("Recent conversation history:\n");
        for (int i = Math.max(0, dialogueHistory.size() - 2); i < dialogueHistory.size(); i++) {
            DialogueEntry entry = dialogueHistory.get(i);
            promptBuilder.append(entry.getSpeaker()).append(": ").append(entry.getLine()).append("\n");
        }

        // Player's current input
        promptBuilder.append("Detective Jack: ").append(userInput);

        return promptBuilder.toString();
    }

    private String interpretRelationshipWithPlayer() {
        // Implement logic to interpret the relationshipWithPlayer numerical value
        // For example:
        if (character.getRelationshipWithPlayer() > 0) {
            return "positive";
        } else if (character.getRelationshipWithPlayer() < 0) {
            return "negative";
        } else {
            return "neutral";
        }
    }

    private String getStoryDescription() {
        // Reads from a file and return string of a description
        ArrayList<String> story = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Hp\\IdeaProjects\\team-cyber-ware\\src\\resources\\storyDescription"));
            String line;
            while((line = reader.readLine()) != null) {
                story.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.join(" ", story);
    }

}
