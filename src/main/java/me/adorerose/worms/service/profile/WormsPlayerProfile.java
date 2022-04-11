package me.adorerose.worms.service.profile;

import org.bukkit.entity.Player;

public class WormsPlayerProfile implements PlayerProfile {
    private Player player;

    public WormsPlayerProfile(Player player) {
        this.player = player;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public boolean isAdmin() {
        return this instanceof AdminProfile;
    }

    @Override
    public AdminProfile asAdmin() {
        return isAdmin() ? (AdminProfile) this : null;
    }

}
