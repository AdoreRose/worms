package me.adorerose.worms.service.profile;

import me.adorerose.worms.util.TextUtils;
import org.bukkit.entity.Player;

public class WormsPlayerProfile implements PlayerProfile {
    protected Player player;

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

    @Override
    public void sendMessage(String message) {
        player.sendMessage(message);
    }

    @Override
    public void sendMessage(String message, Object... args) {
        player.sendMessage(String.format(message, args));
    }

    @Override
    public String toString() {
        return TextUtils.coloredFormat("WormsPlayerProfile[player=%s]", player.getName());
    }

}
