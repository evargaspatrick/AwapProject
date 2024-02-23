package UI;

import Engine.Engine;
import Inventory.Clue;
import Missions.MissionDetails;
import Missions.MissionsBackBone;
import People.Hero;
import People.Person;
import Utility.JsonUtil;

import Location.*;
import java.io.IOException;

import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;



import static Engine.Engine.currLocation;
import static Missions.MissionDetails.mission1;

public class GamePlay extends JPanel {
    private JTextArea textArea;
    private JPanel buttonPanel, textPanel, inventoryPanel;
    private JButton searchButton, forwardButton, backButton, interactButton, inventoryButton, previousButton;
    private int moveIndex = 0;
    private ViewSwitcher viewSwitcher;
    public GamePlay(ViewSwitcher viewSwitcher) {
        setLayout(new BorderLayout());
        this.viewSwitcher = viewSwitcher;
        //Create text area
        textArea = new JTextArea("You are in Detective's Office");
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        textPanel = new JPanel(new BorderLayout());
        textPanel.add(scrollPane, BorderLayout.CENTER);
        add(textPanel, BorderLayout.CENTER);

        // Add the mission one detail
        mission1(textArea);
        // Create buttons
        searchButton = new JButton("Search");
        forwardButton = new JButton("Forward");
        backButton = new JButton("Back");
        interactButton = new JButton("Interact");
        inventoryButton = new JButton("Inventory");
        previousButton = new JButton("Previous");

        // Add buttons to a panel
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        buttonPanel.add(previousButton);
        buttonPanel.add(forwardButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(backButton);
        buttonPanel.add(interactButton);

        inventoryPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        inventoryPanel.add(inventoryButton);
        // Add button panel to the main panel
        add(buttonPanel, BorderLayout.SOUTH);
        add(inventoryPanel, BorderLayout.NORTH);


        forwardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (moveIndex < 3) {
                        if (Engine.getLocation(moveIndex + 1).getAccessibility()) {
                            moveIndex++;
                            Engine.move(moveIndex, textArea);
                        } else {
                            JOptionPane.showMessageDialog(GamePlay.this, "You need a key for this room", "Janitor Closet", JOptionPane.OK_OPTION);
                        }

                    } else {
                        JOptionPane.showMessageDialog(GamePlay.this, "There is no room to go forward", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                    if (moveIndex == 1 && MissionsBackBone.missionOneCompleted()) {
                        MissionDetails.mission2(textArea);
                    } else if (moveIndex == 2 && MissionsBackBone.missionSecondCompleted()) {
                        MissionDetails.mission3(textArea);
                    }

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (moveIndex > 0) {
                        moveIndex--;
                        Engine.move(moveIndex, textArea);
                    } else {
                        JOptionPane.showMessageDialog(GamePlay.this, "There is no room to go back", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                    if (moveIndex == 2 && MissionsBackBone.missionSecondCompleted()) {
                        MissionDetails.mission3(textArea);
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<Location> locations;
                try {
                    locations = JsonUtil.getAllLocations();
                    List<Clue> items = new ArrayList<>();
                    List<Person> people = new ArrayList<>();

                    for (Location location : locations) {
                        if (location.getName().equals(currLocation())) {
                            items = location.getItems();
                            people = location.getPeople();
//                            hero = JsonUtil.getMainHero().get(0);
                            location.setIsExamined(true);
                            break;
                        }
                    }

                    int count = 1;
                    if (items.isEmpty()) {
                        textArea.append("\nThe location is empty. \n");
                        return;
                    }
                    if (!items.isEmpty()) {
                        textArea.append("\nYou discovered: \n");
                        textArea.append("  Items: \n");
                        for(Clue clue : items){
                            textArea.append("     " + count + ". " + clue.getName() + "\n");
                            count++;
                        }
                    }

                    count = 1;
                    if (people.isEmpty()) {
                        return;
                    }

                    textArea.append("\n   People: \n");
                    for (Person person : people) {
                        textArea.append("     " + count + ". " + person.getName() + "\n");
                        count++;
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    viewSwitcher.switchView("INTRODUCTION");

            }
        });


        inventoryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    InventoryFrame.initializeUI();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        interactButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    ArrayList<String> items = Engine.interact(GamePlay.this);
                    ArrayList<String> peopleNames = Engine.getPeopleNames();
                    if (items != null) {
                        new InteractFrame(items, peopleNames);
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }


            }
        });





    }
}




