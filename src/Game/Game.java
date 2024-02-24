package Game;

import Inventory.Clue;
import Inventory.Notebook;
import Location.Location;
import People.Hero;
import UI.GameFrame;
import Utility.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.Headers;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Game {
    public static void run() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GameFrame();
            }
        });
    }
}

