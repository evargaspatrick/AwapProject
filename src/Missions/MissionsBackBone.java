package Missions;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.JOptionPane;

public class MissionsBackBone {

    public static boolean stageOne = false; // represent that mission one is still false
    static boolean magGlass = false; // represents that the Magnifying glass has not been found
    static boolean noteBook = false; // represents that the Notebook has not been found
    static boolean trenchCoat = false; // represents that the Trench Coat has not been found
    static boolean yearBook = false; // represents that the football year book has not been found
    static boolean jcKey = false; // represents that the janitor closet key has not been found
    static boolean stickyNote = false; // represents that coach Marlowe's password hint has not been found
    static boolean inkBox = false;
    static boolean officeFootPrint = false;
    static boolean hallwayFootPrint = false;
    static boolean email1 = false; // represents that coach Marlowe's email has not been discovered.

    static boolean puzzle1 = false; // represents that computer password has not been entered.
    public static void missionOne() {
        // this mission requires players to just pick up Malone's personal items to handle the case he embarks on
        boolean[] conditions = {magGlass, noteBook, trenchCoat};
        boolean allTrue = true;

        for (boolean condition : conditions) {
            if (!condition) {
                allTrue = false;
                break;
            }
        }

        if (allTrue) {
            System.out.println("All conditions are true1");
//            missionTwo(); // Proceeds to the next stage.
        } else {
            // Implement game logic here to allow conditions to change based on player actions.
        }
    }

    public static void setMagGlassTrue(String item) {
        if (item.equalsIgnoreCase("Magnifying Glass")) {
            magGlass = true; // represents that the magnifying glass has been found
        }
    }

    public static void setNoteBookTrue(String item) {
        if (item.equalsIgnoreCase("Notebook")) {
            noteBook = true; // represents that the Notebook has been found
        }
    }

    public static void setTrenchCoatTrue(String item){
        if (item.equalsIgnoreCase("Trench Coat")) {
            trenchCoat = true; // represents that the Trench Coat has been found

        }
    }

    public static boolean missionOneCompleted() {
        return magGlass && noteBook && trenchCoat;
    }

    public static boolean missionSecondCompleted() {
        return yearBook && stickyNote && jcKey && officeFootPrint && inkBox && hallwayFootPrint;
    }

    public static void missionTwo() {
        // Players are looking for the coach at this point
        boolean[] conditions = {yearBook, jcKey, stickyNote};
        boolean allTrue = true;
        for (boolean condition : conditions) {
            if (!condition) {
                allTrue = false;
                break;
            }
        }

        if (allTrue) {
            System.out.println("All conditions are true2");
            missionThree(); // Call the next stage here if needed.
        } else {
            System.out.println("All conditions are false2");
        }
    }

    public static void setOfficeFootPrint(String item) {
        if (item.equalsIgnoreCase("Ink Footprints in the Office")) {
            officeFootPrint = true;
        }
    }

    public static void setHallwayFootPrint(String item) {
        if (item.equalsIgnoreCase("Ink Footprints in the Hallway")) {
            hallwayFootPrint = true;
        }
    }

    public static void setInkBox(String item) {
        if (item.equalsIgnoreCase("Ink Box")) {
            inkBox = true;
        }
    }

    public static void setYearBookTrue(String item) {
        if (item.equalsIgnoreCase("Football Yearbook")) {
            yearBook = true; // represents that the football year book has been found
        }
    }

    public static void setJcKeyTrue(String item){
        if (item.equalsIgnoreCase("Key")) {
            jcKey = true; // represents that the janitor closet key has been found
        }
    }

    public static void setStickyNoteTrue(String item){
        if (item.equalsIgnoreCase("Sticky Note")) {
            stickyNote = true; // represents that coach Marlowe's password hint has been found
        }
    }

    public static void missionThree() {
        boolean[] conditions = {puzzle1};
        boolean allTrue = true;

        for (boolean condition : conditions) {
            if (!condition) {
                allTrue = false;
                break;
            }
        }

        if (allTrue) {
            System.out.println("All conditions are true"); // This is for testing purposes, remove later.
            // game ending goes here.
        } else {
            System.out.println("All conditions are false3"); // This is for testing purposes, will not be in the game, remove later.
        }
    }

    public static void setPuzzleTrue() {
        puzzle1 = true; // represents that computer password has been entered.
        email1 = true; // // represents that coach Marlowe's email has been discovered.
    }
}