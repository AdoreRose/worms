package me.adorerose.worms.event;

import me.adorerose.worms.WormsPlugin;
import me.adorerose.worms.service.profile.*;
import me.adorerose.worms.storage.file.Configuration;
import me.adorerose.worms.storage.file.Language;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class GeneralListener implements Listener {
    private final WormsPlugin plugin;
    private final Configuration config;
    private final Language language;

    public GeneralListener(WormsPlugin plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfiguration();
        this.language = plugin.getLanguage();
    }

    @EventHandler(ignoreCancelled = true)
    public void on(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if (item == null || event.getClickedBlock() == null) return;
        if (player.hasPermission("worms.admin") && item.getType().getId() == config.WAND_ITEM_ID && player.isSneaking())
        {
            AdminProfile profile = PlayerProfileManager.getPlayers().get(player).asAdmin();
            Location loc = event.getClickedBlock().getLocation();
            event.setCancelled(true);
            switch (event.getAction()) {
                case LEFT_CLICK_BLOCK:
                    profile.getSelectedArea().setFirstPoint(loc);
                    player.sendMessage(String.format(language.POS1, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
                break;
                case RIGHT_CLICK_BLOCK:
                    profile.getSelectedArea().setSecondPoint(loc);
                    player.sendMessage(String.format(language.POS2, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
                break;
            }
        }
    }

    @EventHandler
    public void on(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerProfile profile = player.hasPermission("worms.admin")
                ? new WormsAdminProfile(player)
                : new WormsPlayerProfile(player);
        PlayerProfileManager.getPlayers().put(player, profile);
    }

    @EventHandler
    public void on(PlayerQuitEvent event) {
        PlayerProfileManager.getPlayers().remove(event.getPlayer());
    }
}
