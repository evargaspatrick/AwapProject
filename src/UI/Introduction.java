package UI;

import Inventory.Notebook;
import Utility.FileIO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Introduction extends JPanel {
    private CardLayout cardLayout;
    private JPanel cards;
    private JTextArea openingText;
    private JPanel openingPanel, buttonPanel;
    private JTextArea keyWordsText;
    private JButton continueButton, previousButton;
    private JPanel keyWordsPanel;
    private ViewSwitcher viewSwitcher;

    public Introduction(ViewSwitcher viewSwitcher) {
        // Set up the CardLayout
        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);

        // Create the opening panel
        openingPanel = new JPanel(new BorderLayout());
        openingText = new JTextArea(getOpeningScene());
        openingText.setWrapStyleWord(true);
        openingText.setLineWrap(true);
        openingText.setEditable(false);
        openingPanel.add(new JScrollPane(openingText), BorderLayout.CENTER);

        continueButton = new JButton("Continue");

        // continue button's action listener
        // when button is clicked change the card to panel with key words
        continueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cards, "KeyWordsPanel");
            }
        });
        openingPanel.add(continueButton, BorderLayout.SOUTH);

        // Create the keywords panel
        keyWordsPanel = new JPanel(new BorderLayout());
        keyWordsText = new JTextArea(getKeyWordsHelp());
        keyWordsText.setWrapStyleWord(true);
        keyWordsText.setLineWrap(true);
        keyWordsText.setEditable(false);
        keyWordsPanel.add(new JScrollPane(keyWordsText), BorderLayout.CENTER);


        //create buttons
        JButton keyContinueButton = new JButton("Start Game");
        JButton previousButton = new JButton("Previous");

        // buttons to panel
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(keyContinueButton);
        buttonPanel.add(previousButton);


        add(buttonPanel, BorderLayout.SOUTH);
        keyWordsPanel.add(buttonPanel, BorderLayout.SOUTH);

        keyContinueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewSwitcher.switchView("GAMEPLAY");
            }
        });


        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewSwitcher.switchView("MENU");

            }
        });


        // Add the panels to the CardLayout
        cards.add(openingPanel, "OpeningPanel");
        cards.add(keyWordsPanel, "KeyWordsPanel");

        // Initially show the opening panel
        cardLayout.show(cards, "OpeningPanel");

        // Add the cards to the Introduction panel
        this.setLayout(new BorderLayout());
        this.add(cards, BorderLayout.CENTER);
    }

    private static String getOpeningScene() {
        // read the opening scene from the txt email
        String filePath = "src/Resources/OpeningScene";
        String content = FileIO.extractContent(filePath);
        return content;
    }

    private static String getKeyWordsHelp() {
        // read the key words tutorial from the txt email
        String filePath = "src/Resources/KeyWordsHelp";
        String content = FileIO.extractContent(filePath);
        return content;
    }
}

