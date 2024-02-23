package People;
import Inventory.*;
import Weapons.Knife;
import Weapons.Weapon;

// class that represents main hero
public class Hero extends Person {
    Weapon currWeapon;
    Notebook notebook;

    public Hero() {}

    public Hero(String name,
                String role,
                String description,
                String relationshipWithVictim,
                String currentLocation,
                double age) {
        super(name, role, description, relationshipWithVictim, currentLocation, age);
        Knife knife= new Knife();
        this.currWeapon = knife;
        notebook = new Notebook();
        //- line later when we figure out the GUI stuff
    }

    public void setCurrWeapon(Weapon currWeapon) {
        this.currWeapon = currWeapon;
    }

    public Weapon getCurrWeapon() {
        return this.currWeapon;
    }

    public void addClue(Clue clue) {
        notebook.addClue(clue);
    }

    public void addAward(Award award) {
        notebook.addAward(award);
    }

    public void addPerson(Person person) {
        notebook.addPerson(person);
    }
}
