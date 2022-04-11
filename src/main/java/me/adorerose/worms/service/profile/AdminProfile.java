package me.adorerose.worms.service.profile;

import me.adorerose.worms.map.selection.AreaSelection;
import org.bukkit.entity.Player;

public interface AdminProfile extends PlayerProfile {
    AreaSelection getSelectedArea();

    Player getPlayer();
}
