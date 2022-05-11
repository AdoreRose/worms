package me.adorerose.worms.service.profile;

import me.adorerose.worms.map.selection.AreaSelection;
import me.adorerose.worms.map.wecui.CUIEvent;
import org.bukkit.entity.Player;

public interface AdminProfile extends PlayerProfile {
    AreaSelection getSelectedArea();

    Player getPlayer();

    void sendCUIEvent(CUIEvent event);
}
