package me.adorerose.worms.service.profile;

import com.sk89q.util.StringUtil;
import me.adorerose.worms.WormsPlugin;
import me.adorerose.worms.map.selection.AreaSelection;
import me.adorerose.worms.map.selection.CuboidAreaSelection;
import me.adorerose.worms.map.wecui.CUIEvent;
import me.adorerose.worms.util.TextUtils;
import org.bukkit.entity.Player;

import java.nio.charset.StandardCharsets;

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
    public void sendCUIEvent(CUIEvent event) {
        String[] params = event.getParameters();
        String send = event.getTypeId();
        if (params.length > 0) send += "|" + StringUtil.joinString(params, "|");
        this.player.sendPluginMessage(WormsPlugin.getInstance(), "WECUI", send.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String toString() {
        return TextUtils.coloredFormat("WormsAdminProfile[player=%s]", super.player.getName());
    }
}
