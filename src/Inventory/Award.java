
package Inventory;

public class Award{
    private String name;
    private String description;
    public Award(String name,String desc){
        this.name = name;
        this.description = desc;
    }
    @Override
    public String toString() {
        return "Award Name: " + this.name + ", Description: " + this.description;
    }
}
