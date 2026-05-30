package rpg.model;

import rpg.enums.CharacterType;

public class Mage extends PlayerCharacter {

    public Mage(String name) {
        super(name, CharacterType.MAGE, 90);
    }

    @Override
    public int calculateAttack() {
        int baseAttack = 25;
        return getWeapon() != null ? baseAttack + (getWeapon().getAttackBonus() * 2) : baseAttack;
    }
    @Override
    public void performAction() {
        System.out.println(getName() + " casts a devastating arcane spell! WOAAAH ZSUM ZSUM");
    }
}