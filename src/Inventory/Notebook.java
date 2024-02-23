
package Inventory;
import People.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//keeps the record of awards,people and clues collected so far

public class Notebook {
    private List<Clue> clues;
    private List<Award> awards;
    private List<Person> people;

    public Notebook(){
        clues = new ArrayList<Clue>();
        awards = new ArrayList<Award>();
        people = new ArrayList<Person>();
    }


    public void addClue(Clue clue){
        clues.add(clue);
    }
    public void addAward(Award award){
        awards.add(award);
    }
    public void addPerson(Person person){
        people.add(person);
    }

    public List<Clue> getClues() {
        return this.clues;
    }

    public List<Award> getAwards() {
        return this.awards;
    }

    public List<Person> getPeople() {
        return this.people;
    }
}
