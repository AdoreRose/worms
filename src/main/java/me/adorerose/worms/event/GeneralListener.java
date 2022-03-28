package me.adorerose.worms.event;

import me.adorerose.worms.WormsPlugin;
import me.adorerose.worms.storage.file.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class GeneralListener implements Listener {
    private WormsPlugin plugin;
    private Configuration config;

    public GeneralListener(WormsPlugin plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfiguration();
    }

    @EventHandler
    public void on(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if (item == null) return;
        if (player.hasPermission("worms.admin") && item.getType().getId() == config.WAND_ITEM_ID && player.isSneaking())
        {

        }

    }
}
