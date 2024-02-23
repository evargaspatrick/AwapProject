package Weapons;

public class Revolver extends Weapon{
    private int bullets;
    public Revolver(){
        super("Revolver",25, true);
        this.bullets = 7;
    }
    public void unOccupy(){
        this.setIsOccupied(false);
    }
    public void addBullets(int bullets){
        this.setDamage(25);
        this.bullets += bullets;
        if(this.bullets >=7){
            this.bullets = 7;
        }
    }
    public void attack(){
        //I still need to figure out the health bar stuff for this method to work
        if(this.bullets>0) {
            this.bullets -= 1;
        }
        else{
            this.setDamage(0);
        }
    }
}
