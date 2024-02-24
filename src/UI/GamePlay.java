package UI;

import Engine.Engine;
import Inventory.Clue;
import Missions.MissionDetails;
import Missions.MissionsBackBone;
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

import javax.swing.JOptionPane;


import static Engine.Engine.currLocation;
import static Missions.MissionDetails.mission1;
import static Resources.Sounds.PlayMusic;
import static Resources.Sounds.clip;

public class GamePlay extends JPanel {
    private JTextArea textArea;
    private JPanel buttonPanel, textPanel, inventoryPanel, musicControlPanel, northPanel;
    private JButton searchButton, forwardButton, backButton, interactButton, inventoryButton, previousButton,
            playmusicButton, stopmusicButton;
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
        playmusicButton = new JButton("Play");
        stopmusicButton = new JButton("Stop");

        // Add buttons to a panel
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        buttonPanel.add(previousButton);
        buttonPanel.add(forwardButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(backButton);
        buttonPanel.add(interactButton);

        musicControlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        musicControlPanel.add(playmusicButton);
        musicControlPanel.add(stopmusicButton);

        inventoryPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        inventoryPanel.add(inventoryButton);

        northPanel = new JPanel(new BorderLayout());
        northPanel.add(musicControlPanel, BorderLayout.WEST);
        northPanel.add(inventoryPanel, BorderLayout.EAST);

        // Add button panel to the main panel
        add(buttonPanel, BorderLayout.SOUTH);
        add(northPanel, BorderLayout.NORTH);

        playmusicButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlayMusic("D:\\Work\\Project - Sounds\\Angel Eyes (Soft Jazz Version).wav");
                System.out.println("I'm being pressed.");
            }
        });

        stopmusicButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (clip != null && clip.isRunning()) {
                    clip.stop();
                }
            }
        });

        forwardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Resources.Sounds.doorEffect();
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
                Resources.Sounds.doorEffect();
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
                Resources.Sounds.searchSound();
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
                Resources.Sounds.inventorySound();
                try {
                    InventoryFrame.initializeUI();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        interactButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Resources.Sounds.interactSound();
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




