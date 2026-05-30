package rpg.model;

import rpg.enums.CharacterType;

public class Ranger extends PlayerCharacter{
    public Ranger(String name) {
        super(name, CharacterType.RANGER, 110);
    }

    @Override
    public int calculateAttack() {
        int baseAttack = 20;
        return getWeapon() != null ? baseAttack + (int)(getWeapon().getAttackBonus() * 1.5) : baseAttack;
    }
    @Override
    public void performAction() {
        System.out.println(getName() + " SHOOOTS OMEGA MISSLES or arrows");
    }
}
