package UI;

import Interactions.Dialogues;
import Inventory.Clue;
import Inventory.Notebook;
import Missions.MissionsBackBone;
import People.Hero;
import People.Person;
import UI.DialogueInterface;
import Utility.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static Engine.Engine.addItems;
import static Engine.Engine.currLocation;
import static Missions.MissionsBackBone.*;
import static Resources.EndMessage.displayEndMessage1;

public class InteractFrame {
    private JButton notebookButton, talkButton, accessComputerButton;
    private JFrame frame;
    private JTabbedPane tabbedPane;
    private JPanel cluePanel, peoplePanel, computerButtonPanel, computerPanel;
    public InteractFrame(ArrayList<String> items, ArrayList<String> peopleNames) throws IOException {
        frame = new JFrame();
        frame.setTitle("Items available");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();
        cluePanel = new JPanel();
        cluePanel.setLayout(new BorderLayout()); // Use BorderLayout for the clue panel
        peoplePanel = new JPanel();

        JPanel clueCheckBoxPanel = new JPanel(); // Create a panel to hold checkboxes
        clueCheckBoxPanel.setLayout(new BoxLayout(clueCheckBoxPanel, BoxLayout.Y_AXIS)); // Align checkboxes vertically

        JPanel peopleCheckBoxPanel = new JPanel(); // Create a panel to hold checkboxes
        peopleCheckBoxPanel.setLayout(new BoxLayout(peopleCheckBoxPanel, BoxLayout.Y_AXIS)); // Align checkboxes vertically
        List<Clue> notebookClues = JsonUtil.getNotebook().getClues();

        // Add checkboxes to the checkBoxPanel, not directly to cluePanel
        for (String i : items) {

            boolean itemExists = false;
            for (Clue clue : notebookClues) {
                if (clue.getName().equalsIgnoreCase(i)) {
                    itemExists = true;
                }
            }
            if (!itemExists) {
                JCheckBox checkBox = new JCheckBox(i, false);
                clueCheckBoxPanel.add(checkBox);
            }

        }

        JScrollPane clueScrollPane = new JScrollPane(clueCheckBoxPanel); // Add checkBoxPanel to a scrollPane
        cluePanel.add(clueScrollPane, BorderLayout.CENTER); // Add scrollPane to the center of cluePanel


        JScrollPane peopleScrollPane = new JScrollPane(peopleCheckBoxPanel); // Add checkBoxPanel to a scrollPane
        peoplePanel.add(peopleScrollPane, BorderLayout.CENTER); // Add scrollPane to the center of cluePanel

        for (String person : peopleNames) {
            JCheckBox checkBox = new JCheckBox(person, false);
            peopleCheckBoxPanel.add(checkBox);
        }

        // Create a new button
        notebookButton = new JButton("Add to Inventory");
        talkButton = new JButton("Talk");
        ArrayList<String> itemsToRemove = new ArrayList<>();

        // Add an ActionListener to the button
        notebookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Iterate over the checkboxes
                for (Component component : clueCheckBoxPanel.getComponents()) {
                    if (component instanceof JCheckBox) {
                        JCheckBox checkBox = (JCheckBox) component;
                        if (checkBox.isSelected()) {
                            // If the checkbox is selected, get its label and adds it to the arrayList
                            String item = checkBox.getText();
                            itemsToRemove.add(item);
                            setTrue(item);
                        }
                    }
                }

                for(String i : itemsToRemove){
                    addItems(i);
                }
                frame.dispose();
            }
        });
        talkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //closes this frame and opens the new one for having a dialog
                frame.dispose();

                    DialogueInterface dialogue = new DialogueInterface(frame);
                    dialogue.setVisible(true);

            }
        });

        JPanel clueButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        clueButtonPanel.add(notebookButton); // Add the button to the panel
        cluePanel.add(clueButtonPanel, BorderLayout.SOUTH); // Add the button panel to the bottom
        JPanel peopleButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        peopleButtonPanel.add(talkButton);
        peoplePanel.add(peopleButtonPanel, BorderLayout.SOUTH);

        tabbedPane.addTab("Clues", cluePanel);
        tabbedPane.addTab("People", peoplePanel);
        frame.getContentPane().add(tabbedPane);
        frame.setVisible(true);
        if (currLocation().equalsIgnoreCase("Coach Marlowe’s Office")) {
            computerPanel = new JPanel();
            computerButtonPanel = new JPanel();
            accessComputerButton = new JButton("Access Computer");
            computerButtonPanel.add(accessComputerButton);
            computerPanel.add(computerButtonPanel, BorderLayout.SOUTH);
            tabbedPane.addTab("Computer", computerPanel);
            // Add ActionListener logic for the accessComputerButton
            // Updated logic with handling incorrect password
            accessComputerButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String pass = "Matt Lockwood";
                    String answer = "";
                    boolean passwordCorrect = false;
                    frame.dispose();
                    while (!passwordCorrect) {
                        answer = JOptionPane.showInputDialog("Enter password:");
                        if (answer == null) {
                            break; // User canceled input
                        } else if (answer.equals(pass)) {
                            JOptionPane.showMessageDialog(null, "    Hello Coach Marlowe!");
                            MissionsBackBone.setPuzzleTrue();
                            passwordCorrect = true;

                            // Call the displayEndMessage1 method here
                            displayEndMessage1();
                        } else {
                            JOptionPane.showMessageDialog(null, "Incorrect password. Must be a bench warmer! Please try again.");
                        }
                    }
                }
            });
        }
    }

    // sets true to item booleans if added to inventory
    public void setTrue(String item) {
        setMagGlassTrue(item);
        setNoteBookTrue(item);
        setTrenchCoatTrue(item);
        setYearBookTrue(item);
        setStickyNoteTrue(item);
        setHallwayFootPrint(item);
        setOfficeFootPrint(item);
        setInkBox(item);
    }
}
