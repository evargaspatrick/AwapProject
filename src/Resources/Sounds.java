package Resources;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;
import java.io.File;

public class Sounds {
    public static Clip clip;

    public static void backgroundMusic() {
        String filepath = "src/Resources/Angel Eyes (Soft Jazz Version).wav";
        PlayMusic(filepath);
    }

    public static void stopMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    public static void PlayMusic(String background) {
        try {
            File musicPath = new File(background);
            if (musicPath.exists()) {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(musicPath);
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            } else {
                System.out.println("Can't find file");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void doorEffect() {
        String filepath = "src/Resources/Door open sound effect.wav";
        playDoor(filepath);
    }

    public static void playDoor(String door) {
        try {
            File filepath = new File(door);
            if (filepath.exists()) {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(filepath);
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void searchSound() {
        String filepath = "src/Resources/Searching sound.wav";
        playSearch(filepath);
    }
    public static void playSearch(String search) {
        try {
            File filepath = new File(search);
            if (filepath.exists()) {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(filepath);
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            }
        }
         catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void inventorySound() {
        String filepath = "src/Resources/Inventory sound.wav";
        playInventory(filepath);
    }

    public static void playInventory(String Inventory) {
        try {
            File filepath = new File(Inventory);
            if (filepath.exists()) {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(filepath);
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void interactSound() {
        String filepath = "src/Resources/Grab Sound Effect.wav"; // Corrected file extension to .wav
        playInteract(filepath);
    }

    public static void playInteract(String interact) {
        try {
            File file = new File(interact);
            if (file.exists()) {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            } else {
                System.out.println("File not found: " + interact);
            }
        } catch (Exception e) {
            System.out.println("Error playing interact sound effect: " + e.getMessage());
        }
    }
}