package rpg.interfaces;

public interface Combatant {
    int takeDamage(int amount);
    int calculateAttack();
    boolean isAlive();
}
