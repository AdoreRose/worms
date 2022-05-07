package me.adorerose.worms.service.profile;

import me.adorerose.worms.util.TextUtils;
import net.md_5.bungee.api.chat.TextComponent;
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
    public void sendActionBar(String message) {
        message = message.replace('&', '§');
        player.spigot().sendMessage(TextComponent.fromLegacyText(message));
    }

    @Override
    public void sendActionBar(String[] message) {
        StringBuilder acc = new StringBuilder();
        for (String s : message) acc.append(s);
        sendActionBar(acc.toString());
    }

    @Override
    public String toString() {
        return TextUtils.coloredFormat("WormsPlayerProfile[player=%s]", player.getName());
    }

}
