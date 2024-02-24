package UI;

import Inventory.Notebook;
import Inventory.Clue;
import Inventory.Award;
import People.Person;
import Utility.JsonUtil;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.core.type.TypeReference;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static Utility.JsonUtil.parseJsonFile;

public class InventoryFrame extends JFrame {

    public static void initializeUI() throws IOException {
        Notebook notebook = parseJsonFile("src/Resources/Notebook.json", new TypeReference<Notebook>() {
        });
        JFrame frame = new JFrame();
        // set window characteristics
        frame.setTitle("Inventory");
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // initialize JObjects
        JTabbedPane tabbedPane = new JTabbedPane();
        JTextArea cluesArea = new JTextArea();
        JTextArea awardsArea = new JTextArea();
        JTextArea peopleArea = new JTextArea();

        cluesArea.setEditable(false);
        awardsArea.setEditable(false);
        peopleArea.setEditable(false);

        // Write the items into teaxt areas
        populateTextArea(cluesArea, notebook.getClues());

        tabbedPane.addTab("Items", new JScrollPane(cluesArea));
        tabbedPane.addTab("Awards", new JScrollPane(awardsArea));
        tabbedPane.addTab("People", new JScrollPane(peopleArea));

        frame.add(tabbedPane);
    }
    private static void populateTextArea(JTextArea textArea, List<?> items) throws IOException {
        // Creates the structure of a text area
        StringBuilder sb = new StringBuilder();
        List<Clue> clues = JsonUtil.getAllClues();
        int count = 0;
        for (Object item : items) {
            count++;
            for (Clue clue : clues) {
                if (clue.getName().equals(item.toString())) {
                    sb.append(count + ". ").append("Clue Name: ").append(clue.getName() + "\n")
                            .append("    Description: ").append(clue.getDescription() + "\n");
                }

            }
        }
        textArea.setText(sb.toString());
    }
}

