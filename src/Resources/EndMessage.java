package Resources;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class EndMessage {

    public static void displayEndMessage1() {
        try {
            Path filePath = Paths.get("C:\\Users\\Eshmael\\Desktop\\team-cyber-ware-main\\src\\Resources\\EndMessage");
            String endMessage = new String(Files.readAllBytes(filePath));

            JOptionPane.showMessageDialog(null, endMessage, "End Message", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
