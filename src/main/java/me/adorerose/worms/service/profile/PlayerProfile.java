package me.adorerose.worms.service.profile;

import org.bukkit.entity.Player;

public interface PlayerProfile {
    Player getPlayer();

    boolean isAdmin();

    AdminProfile asAdmin();
}
