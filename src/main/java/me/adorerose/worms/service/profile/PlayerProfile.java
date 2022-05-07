package me.adorerose.worms.service.profile;

import org.bukkit.entity.Player;

public interface PlayerProfile {
    Player getPlayer();

    boolean isAdmin();

    AdminProfile asAdmin();

    void sendMessage(String message);

    void sendMessage(String message, Object... args);

    void sendActionBar(String message);

    void sendActionBar(String[] message);
}
