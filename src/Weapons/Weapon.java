package Weapons;

public class Weapon {
    private String name;
    private int damage;
    private boolean isOccupied;

    public Weapon(String name, int damage, boolean isOccupied){
        this.name = name;
        this.damage = damage;
        this.isOccupied = isOccupied;
    }

    public String getName() {
        return name;
    }

    public int getDamage() {
        return damage;
    }

    public boolean getIsOccupied() {
        return isOccupied;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setIsOccupied(boolean occupancy) {
        this.isOccupied = occupancy;
    }
}
