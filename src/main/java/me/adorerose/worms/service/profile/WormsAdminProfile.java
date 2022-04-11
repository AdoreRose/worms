package me.adorerose.worms.service.profile;

import me.adorerose.worms.map.selection.AreaSelection;
import me.adorerose.worms.map.selection.CuboidAreaSelection;
import me.adorerose.worms.util.TextUtils;
import org.bukkit.entity.Player;

public class WormsAdminProfile extends WormsPlayerProfile implements AdminProfile {
    private final AreaSelection areaSelection;

    public WormsAdminProfile(Player player) {
        super(player);
        this.areaSelection = new CuboidAreaSelection();
    }

    @Override
    public AreaSelection getSelectedArea() {
        return areaSelection;
    }

    @Override
    public String toString() {
        return TextUtils.coloredFormat("WormsAdminProfile[player=%s]", super.player.getName());
    }
}
