package rpg.manager;

import rpg.model.PlayerCharacter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Guild implements Serializable {
    // Add a unique ID for serialization versions
    private static final long serialVersionUID = 1L;

    private final List<PlayerCharacter> members = new ArrayList<>();

    public void addPlayer(PlayerCharacter player) {
        if (player != null) members.add(player);
    }

    public Optional<PlayerCharacter> getPlayerByName(String name) {
        return members.stream()
                .filter(p -> p.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    public int getGuildSize() { return members.size(); }
}