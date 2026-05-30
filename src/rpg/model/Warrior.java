package rpg.model;
import rpg.enums.CharacterType;

public class Warrior extends PlayerCharacter {

    public Warrior(String name){
        super(name, CharacterType.WARRIOR, 150);
    }

    @Override
    public void performAction(){
        System.out.println(getName() + " unleashes a heavy melee strike! RAAAAAR!");
   }

    @Override
    public int calculateAttack(){
        int baseAttack = 15;
        return getWeapon() != null ? baseAttack + getWeapon().getAttackBonus() : baseAttack;
     }

}
