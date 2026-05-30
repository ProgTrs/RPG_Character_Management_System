package rpg.model;

public class Weapon extends Item{

    private final int attackBonus;

    public Weapon(String name, int attackBonus){
        super(name);
        this.attackBonus=attackBonus;
    }

    public int getAttackBonus(){return attackBonus;}
}
