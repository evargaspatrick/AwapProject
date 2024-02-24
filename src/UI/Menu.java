package UI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Menu extends JPanel {
    private Image backgroundImage;
    private ViewSwitcher viewSwitcher;

    public Menu(ViewSwitcher viewSwitcher) {
        this.viewSwitcher = viewSwitcher;
        setLayout(new GridBagLayout());
        try {
            backgroundImage = ImageIO.read(new File("src/Resources/background.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        JButton newGameBtn = createStyledButton("New Game");
        JButton resumeBtn = createStyledButton("Resume");
        JButton exitBtn = createStyledButton("Exit");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(10, 10, 10, 10);

        add(newGameBtn, gbc);
        add(resumeBtn, gbc);
        add(exitBtn, gbc);

        // Example of how to handle button action in the context of CardLayout
        newGameBtn.addActionListener(e -> this.viewSwitcher.switchView("INTRODUCTION"));
        exitBtn.addActionListener(e -> System.exit(0));
    }

    // Override paintComponent to draw the background image
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setForeground(new Color(220, 220, 220)); // Set the text color with some transparency
        button.setFont(new Font("Times New Roman", Font.BOLD, 20)); // Set the font
        button.setContentAreaFilled(false); // Transparent button background
        button.setBorderPainted(false); // No border
        button.setFocusPainted(false); // No focus border
        button.setOpaque(false); // Make the button transparent

        // Change cursor to hand cursor when hovering over the button
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add mouse listener for hover effects
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setForeground(Color.WHITE); // Change color on hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setForeground(new Color(220, 220, 220)); // Change color back on exit
            }
        });

        return button;
    }
}
