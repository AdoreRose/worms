package me.adorerose.worms.service.profile;

import me.adorerose.worms.map.selection.AreaSelection;
import org.bukkit.entity.Player;

public class WormsAdminProfile extends WormsPlayerProfile implements AdminProfile {
    private AreaSelection areaSelection;

    public WormsAdminProfile(Player player) {
        super(player);
    }

    @Override
    public AreaSelection getSelectedArea() {
        return areaSelection;
    }
}
