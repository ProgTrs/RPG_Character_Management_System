package rpg.manager;

import rpg.model.PlayerCharacter;

public class BattleEngine {

    public void duel(PlayerCharacter p1, PlayerCharacter p2){
        System.out.println("\n--- BATTLE START: " + p1.getName() + " VS " + p2.getName() + " ---");

        while (p1.isAlive() && p2.isAlive()){
            executeTurn(p1, p2);
            if(!p2.isAlive()) break;
            executeTurn(p2, p1);
        }

        PlayerCharacter winner = p1.isAlive() ? p1 : p2;
        System.out.println("--- BATTLE OVER! " + winner.getName() + " wins! ---\n");
    }

    private void executeTurn(PlayerCharacter attacker, PlayerCharacter defender){
        int rawDamage = attacker.calculateAttack();
        int actualDamage = defender.takeDamage(rawDamage);

        System.out.printf("%s attacks! %s's armor mitigates the hit, taking %d damage. (%d HP remaining)%n",
                attacker.getName(), defender.getName(), actualDamage, defender.getHp());
    }


}
