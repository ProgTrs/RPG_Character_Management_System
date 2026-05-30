package rpg;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import rpg.manager.*;
import rpg.exceptions.GameException;
import rpg.model.*;

import java.nio.file.Path;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RpgApplicationTests {

    private PlayerCharacter warrior;
    private PlayerCharacter ranger;
    private Guild guild;
    private PlayerCharacter mage;

    @BeforeEach
    void setUp() {
        guild = new Guild();
        warrior = new Warrior("Conan");
        ranger = new Ranger("Sylvanas");
        mage = new Mage("Twister");

        guild.addPlayer(warrior);
        guild.addPlayer(ranger);
        guild.addPlayer(mage);
    }
    @Test
    void testArmorMitigatesDamage(){
        warrior.equipArmor(new Armor("Shield", 10));

        int damageTaken = warrior.takeDamage(25);

        assertEquals(15, damageTaken);
        assertEquals(135, warrior.getHp());
    }

    @Test
    void testDamageNeverDropsBelowOne(){
        mage.equipArmor(new Armor("Heavy Cloak", 50));

        int damageTaken = mage.takeDamage(10);

        assertEquals(1, damageTaken);
        assertEquals(89, mage.getHp());
    }

    @Test
    void testWeaponIncreasesAttack() {
        int baseAttack = warrior.calculateAttack();
        warrior.equipWeapon(new Weapon("Sword", 15));

        assertEquals(baseAttack + 15, warrior.calculateAttack());
    }

    @Test
    void testBattleEngineResultsInOneWinner() {
        BattleEngine engine = new BattleEngine();
        warrior.equipWeapon(new Weapon("God Blade", 100));

        engine.duel(warrior, mage);

        assertTrue(warrior.isAlive());
        assertFalse(mage.isAlive());
        assertEquals(0, mage.getHp());
    }

    @Test
    void testGuildRetrieval() {
        Optional<PlayerCharacter> found = guild.getPlayerByName("Sylvanas");
        Optional<PlayerCharacter> notFound = guild.getPlayerByName("Arthas");

        assertTrue(found.isPresent());
        assertFalse(notFound.isPresent());
        assertEquals("Sylvanas", found.get().getName());
    }

    @Test
    void testDataManagerSerialization(@TempDir Path tempDir) {
        Path tempFile = tempDir.resolve("test_state.dat");

        warrior.takeDamage(50);
        warrior.equipWeapon(new Weapon("Axe", 12));

        DataManager.saveGuildState(guild, tempFile.toString());
        Guild loadedGuild = DataManager.loadGuildState(tempFile.toString());

        PlayerCharacter loadedWarrior = loadedGuild.getPlayerByName("Conan").orElseThrow();

        assertEquals(100, loadedWarrior.getHp());
        assertNotNull(loadedWarrior.getWeapon());
        assertEquals("Axe", loadedWarrior.getWeapon().getName());
        assertEquals(12, loadedWarrior.getWeapon().getAttackBonus());
    }
}