package rpg.model;

import rpg.enums.CharacterType;
import rpg.exceptions.GameException;
import rpg.interfaces.Actionable;
import rpg.interfaces.Combatant;

import java.io.Serializable;

public abstract class PlayerCharacter implements Actionable, Combatant, Serializable{

    private final String name;
    private final CharacterType type;
    private int hp;
    private int maxHp;

    private Weapon weapon;
    private Armor armor;

    // Protected constructor so only subclasses can call it
    protected PlayerCharacter(String name, CharacterType type, int maxHp) {
        this.name = name;
        this.type = type;

        this.maxHp = maxHp;
        this.hp = maxHp;
    }

    public void equipWeapon(Weapon weapon){
        if (weapon == null){
            throw  new GameException("Cannot equip a null item.");
        }
        this.weapon = weapon;
        System.out.println(name + " equipped " + weapon.getName());
    }

    public void equipArmor(Armor armor){
        if (armor == null){
            throw  new GameException("Cannot equip a null item.");
        }
        this.armor = armor;
        System.out.println(name + " equipped " + armor.getName());
    }

    public void drinkPotion(Potion potion){
        this.hp = Math.min(this.maxHp, this.hp+potion.getHealAmount());
        System.out.println(name + " drank "+ potion.getName() + " and healed to "+ hp+ " HP.");
    }

    @Override
    public int takeDamage(int amount){
        int defense = (armor != null) ? armor.getDefenseBonus() : 0;
        int actualDamage = Math.max(1, amount-defense);
        this.hp = Math.max(0, this.hp-actualDamage);
        return actualDamage;
    }

    @Override
    public boolean isAlive(){
        return this.hp>0;
    }

    // Getters
    public String getName() { return name; }
    public CharacterType getType() { return type; }
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public Weapon getWeapon() { return weapon;}
    public Armor getArmor() {return armor;}

}
