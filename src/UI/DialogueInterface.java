package UI;

import Interactions.DialogueChoice;
import Interactions.Dialogues;

import People.Person;
import Utility.JsonUtil;
import Location.Location;
import Inventory.Clue;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;
import Engine.Engine;

import static Engine.Engine.addItems;
import static Missions.MissionsBackBone.setJcKeyTrue;

public class DialogueInterface extends JDialog {
    private JTextArea dialogueTextArea;
    private JTextField userInputField;

    private JPanel optionsPanel; // Panel for displaying dialogue options

    private Dialogues dialogues;

    public DialogueInterface(JFrame parentFrame) {
        super(parentFrame, "Dialogue", true);
        this.dialogues = dialogues;

        setSize(500, 300);
        setLayout(new BorderLayout());

        dialogueTextArea = new JTextArea();
        dialogueTextArea.setEditable(false);
        dialogueTextArea.setLineWrap(true);
        dialogueTextArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(dialogueTextArea);
        add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        userInputField = new JTextField();

        inputPanel.add(userInputField, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        optionsPanel = new JPanel();
        optionsPanel.setLayout(new FlowLayout());
        add(optionsPanel, BorderLayout.SOUTH);
        showChoices();
        setLocationRelativeTo(parentFrame);
    }
    private void processInput(String response) throws IOException {
        if (!response.trim().isEmpty()) {
            dialogueTextArea.append("Janitor Joe: " + response + "\n");
            userInputField.setText("");
            addToNotebook();
        }
    }

    // Adds the key to the inventory/notebook
    private void addToNotebook() throws IOException {
        List<Person> people = JsonUtil.getAllPeople();
        Location location = Engine.getLocation(3);


        location.setAccessibility(true);
        for (Person person : people) {
            if (!person.getClues().isEmpty()) {
                addItems(person.getClues().getFirst().getName());

            }
        }
    }

    private void appendToDialogue(String text) {
        dialogueTextArea.append(text + "\n");
        dialogueTextArea.setCaretPosition(dialogueTextArea.getDocument().getLength());
    }

    public void presentChoices(DialogueChoice... choices) {
        optionsPanel.removeAll();
        dialogueTextArea.append("Janitor Joe: How you doing, is there something you need?");
        for (DialogueChoice choice : choices) {
            JButton choiceButton = new JButton(choice.getPrompt());
            choiceButton.addActionListener((ActionEvent e) -> {
                setJcKeyTrue("Key");


                choice.select();
                optionsPanel.removeAll();
                this.revalidate();
                this.repaint();
            });
            optionsPanel.add(choiceButton);
        }

        this.revalidate();
        this.repaint();
    }

    private void showChoices() {
        DialogueChoice choice1 = new DialogueChoice("Ask for the keys politely", () -> {
            // Logic to handle this choice
            appendToDialogue("\n\nYou: Could you please give me the keys?\n");
            // Assume processInput simulates processing and displaying the janitor's response
            try {
                List<Clue> notebookItems = JsonUtil.getAllClues();


                for (Clue item : notebookItems) {
                    if (item.getName().equalsIgnoreCase("Key")) {
                        processInput("I already handed you the key. What, you went and dropped it?");
                        return;
                    }
                }
                processInput("Here are the keys. Please be careful.");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        DialogueChoice choice2 = new DialogueChoice("Demand the keys forcefully", () -> {
            // Logic to handle this choice
            appendToDialogue("\n\nYou: Hand over the keys now, it's important.\n");
            // Assume processInput simulates processing and displaying the janitor's response
            try {
                processInput("Alright, alright, no need to be pushy. Here you go.");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        presentChoices(choice1, choice2);
    }

}
