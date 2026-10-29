# RPG Character Management System

<p align="center">
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white" alt="Java" />
  <img src="https://img.shields.io/badge/Project-CLI%20RPG-5B21B6?style=for-the-badge" alt="CLI RPG" />
  <img src="https://img.shields.io/badge/Status-Active-22C55E?style=for-the-badge" alt="Status" />
  <img src="https://img.shields.io/badge/Tested-JUnit-FF6B6B?style=for-the-badge" alt="JUnit" />
</p>

<p align="center">
  <b>A lightweight Java-based RPG battle simulator built around guild management, character progression, equipment, and automated combat.</b>
</p>

<div align="center">
  <br>
  <img src="https://images.unsplash.com/photo-1542751371-adc38448a05e?auto=format&fit=crop&w=1200&q=80" alt="Fantasy battlefield" width="900" />
  <br><br>
</div>

## Table of Contents

- [Overview](#overview)
- [Project Goals](#project-goals)
- [Architecture](#architecture)
- [Core Gameplay Systems](#core-gameplay-systems)
- [Character Classes](#character-classes)
- [Battle Mechanics](#battle-mechanics)
- [Project Structure](#project-structure)
- [How to Run](#how-to-run)
- [Usage Example](#usage-example)
- [Testing](#testing)
- [Future Enhancements](#future-enhancements)

---

## Overview

The RPG Character Management System is a Java console application that simulates a small tactical RPG ecosystem. It combines:

- guild-based character management
- unique player classes with different combat profiles
- equipment and stat-based combat
- serialization to persist guild state
- automated battle simulation between party members

The project is intentionally structured as a clean object-oriented example, making it suitable for learning Java fundamentals, inheritance, interfaces, serialization, and game-loop design.

---

## Project Goals

This project demonstrates how to model a small game system using disciplined OOP patterns:

- Represent distinct character archetypes through inheritance
- Define shared behavior through interfaces
- Encapsulate combat logic in a central battle engine
- Store and reload game progress using Java serialization
- Keep the application easy to run from a terminal or IDE

---

## Architecture

The codebase is organized around a layered game architecture that separates domain logic, mechanics, and persistence.

### Main layers

- `rpg.model` — character, item, and equipment logic
- `rpg.manager` — guild and battle orchestration
- `rpg.interfaces` — shared contracts for combat and actions
- `rpg.enums` — character role definitions
- `rpg.exceptions` — custom application exceptions

This separation keeps the project readable and makes it easy to extend with new classes, enemies, items, or features.

---

## Core Gameplay Systems

### 1. Player character model

The abstract `PlayerCharacter` class is the foundation of the entire system. It contains:

- name and character type
- health (`hp`) and max health (`maxHp`)
- equipped weapon and armor
- methods for damage, healing, and survival checks

This class implements both of the game’s shared interfaces:

- `Combatant`: defines how a character participates in battle
- `Actionable`: defines the action performed by the character

### 2. Equipment system

The game uses a lightweight item hierarchy:

- `Item` as the base type
- `Weapon` adds `attackBonus`
- `Armor` adds `defenseBonus`
- `Potion` adds `healAmount`

This allows characters to equip equipment and make battle calculations more dynamic.

### 3. Guild management

The `Guild` class acts as a party container. It supports:

- adding players
- retrieving players by name
- retrieving players by index
- checking guild size

This is the central management layer for the simulated party.

### 4. Battle engine

`BattleEngine` orchestrates combat between two characters.

It:

- announces the duel
- executes alternating turns
- computes actual damage based on attack and armor mitigation
- determines a winner when one fighter falls

### 5. Save and load persistence

`DataManager` saves and loads the guild state using Java object serialization.

This allows the application to reload previous progress from a file such as `server_state.dat`.

---

## Character Classes

Each class extends `PlayerCharacter` and defines a unique combat profile.

| Class | Role | Max HP | Attack Style | Notes |
|---|---|---:|---|---|
| `Warrior` | Frontline tank | 150 | Melee | Strong defense and direct combat style |
| `Mage` | Arcane caster | 90 | Spell damage | High burst damage, lower HP |
| `Ranger` | Ranged attacker | 110 | Ranged attack | Balanced ranged damage profile |

### Class behaviors

#### Warrior
- Strength-focused class
- Base attack is moderate
- Weapon bonus adds directly to attack damage
- Action message emphasizes a melee-heavy strike

#### Mage
- High magical damage
- Attack bonus is multiplied by 2
- Lower survival pool than warrior
- Action message emphasizes arcane spell casting

#### Ranger
- Mid-range damage specialist
- Weapon bonus is boosted by 1.5x in attack calculation
- Action message emphasizes ranged attacks

---

## Battle Mechanics

The combat model is intentionally simple but effective.

### Damage formula

Damage is reduced by armor defense:

```java
int defense = (armor != null) ? armor.getDefenseBonus() : 0;
int actualDamage = Math.max(1, amount - defense);
this.hp = Math.max(0, this.hp - actualDamage);
```

### Attack calculation examples

- `Warrior`: `15 + weaponBonus`
- `Mage`: `25 + (weaponBonus * 2)`
- `Ranger`: `20 + (weaponBonus * 1.5)`

### Health recovery

Characters can consume potions:

```java
this.hp = Math.min(this.maxHp, this.hp + potion.getHealAmount());
```

This allows fighters to recover health before or during battle, which is especially relevant in the `Main` flow.

---

## Project Structure

```text
RPG_Character_Management_System/
├── .gitignore
├── RPG_Character_Management_System.iml
├── README.md
├── src/
│   └── rpg/
│       ├── Main.java
│       ├── enums/
│       │   └── CharacterType.java
│       ├── exceptions/
│       │   └── GameException.java
│       ├── interfaces/
│       │   ├── Actionable.java
│       │   └── Combatant.java
│       ├── manager/
│       │   ├── BattleEngine.java
│       │   ├── DataManager.java
│       │   └── Guild.java
│       └── model/
│           ├── Armor.java
│           ├── Item.java
│           ├── Mage.java
│           ├── PlayerCharacter.java
│           ├── Potion.java
│           ├── Ranger.java
│           ├── Warrior.java
│           ├── Weapon.java
│           └── ...
└── test/
    └── rpg/
        └── RpgApplicationTests.java
```

---

## How to Run

### Prerequisites

- Java JDK 17 or newer
- Terminal or IDE (IntelliJ IDEA, VS Code, Eclipse, etc.)

### Compile and run from the project root

```bash
javac -d out $(find src -name "*.java")
java -cp out rpg.Main
```

If you are using an IDE, run `rpg.Main` directly from the source tree.

### Generated data

On first launch, the application creates a new guild and saves state to:

```text
server_state.dat
```

This file persists the guild state using Java serialization.

---

## Usage Example

When the application runs, it:

1. loads any existing guild state
2. creates default characters if none exist
3. equips them with items
4. selects two opponents
5. restores them to full health
6. launches a duel via `BattleEngine`
7. saves the updated state back to disk

Example of the application flow:

```text
Setting up new players...
Conan equipped DeathBlade
Conan equipped Smoldering ashes
Sylvanas equipped Banshee's veil
Sylvanas equipped Banshee's Cry Thruster
Twister equipped Gambler's smoking
Twister equipped Arcane-sharpened Battle Deck
--- BATTLE START: Conan VS Twister ---
Conan attacks! Twister's armor mitigates the hit, taking X damage. (Y HP remaining)
...
--- BATTLE OVER! Conan wins! ---
Server state save succesfully to server_state.dat
```

---

## Testing

The project includes unit tests in `test/rpg/RpgApplicationTests.java`.

These tests verify:

- armor mitigates damage correctly
- damage never drops below 1
- weapon bonuses affect attack calculations
- battle engine produces a winner
- guild retrieval works as expected
- state serialization persists character and equipment information

### Run tests

```bash
javac -d out $(find src -name "*.java")
javac -cp out -d out/test $(find test -name "*.java")
java -jar ???
```

For most Java setups, tests are run directly in the IDE or via a build tool such as Maven or Gradle. This repository currently contains source files and JUnit tests without a build configuration file, so IDE-based execution is the most straightforward option.

---

## Future Enhancements

This project has a strong foundation for expansion. Potential next steps include:

- enemy NPCs and monster classes
- inventory and shop system
- leveling and experience points
- multiple guilds or party management screens
- richer battle actions and special abilities
- GUI or web frontend for easier interaction
- database-backed persistence instead of file serialization

---

## Summary

The RPG Character Management System is a clean and approachable Java project that demonstrates practical game-design principles in code. It combines:

- inheritance and polymorphism
- object-oriented modeling
- serialization
- battle logic
- custom exceptions
- test coverage

It is ideal for learning Java game architecture, object composition, and implementing a compact RPG simulation from scratch.

---

<p align="center">
  <b>Built with Java • Designed for learning and extension</b>
</p>
