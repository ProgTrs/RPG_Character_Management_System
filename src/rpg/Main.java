package rpg;

import rpg.manager.BattleEngine;
import rpg.manager.DataManager;
import rpg.manager.Guild;
import rpg.model.*;

public class Main {

    private static final String SAVE_FILE = "server_state.dat";

    public static void main(String[] args) {
        //tries loading if unable then creates a new Guild
        Guild guild = DataManager.loadGuildState(SAVE_FILE);

        if (guild.getGuildSize() ==0){
            System.out.println("Setting up new players...");
            PlayerCharacter death_knight= new Warrior("Conan");
            PlayerCharacter ranger = new Ranger("Sylvanas");
            PlayerCharacter mage = new Mage("Twister");

            death_knight.equipWeapon(new Weapon("DeathBlade", 20));
            death_knight.equipArmor(new Armor("Smoldering ashes", 6));

            ranger.equipArmor(new Armor("Banshee's veil", 5));
            ranger.equipWeapon(new Weapon("Banshee's Cry Thruster", 19));

            mage.equipArmor(new Armor("Gambler's smoking", 3));
            mage.equipWeapon(new Weapon("Arcane-sharpened Battle Deck", 27));

            guild.addPlayer(death_knight);
            guild.addPlayer(mage);
            guild.addPlayer(ranger);
        }

        //BattleEngine arena = new BattleEngine();



        PlayerCharacter p2= guild.getPlayerByName("Twister").orElseThrow();
        PlayerCharacter p1= guild.getPlayerByName("Sylvanas").orElseThrow();

        while (p1.getHp()!= p1.getMaxHp()){
            p1.drinkPotion(new Potion("Minor Health Potion", 20));
        }

        while (p2.getHp()!= p2.getMaxHp()){
            p2.drinkPotion(new Potion("Minor Health Potion", 20));
        }

        BattleEngine arena = new BattleEngine();
        arena.duel(p1, p2);

        DataManager.saveGuildState(guild, SAVE_FILE);
    }
}