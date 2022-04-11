package me.adorerose.worms.service.profile;

import me.adorerose.worms.lang.collections.NoNullMap;
import org.bukkit.entity.Player;

import java.util.Map;

public class PlayerProfileManager {
    private static final Map<Player, PlayerProfile> players;

    static {
        players = NoNullMap.newInstance();
    }

    public static Map<Player, PlayerProfile> getPlayers() {
        return players;
    }

}
