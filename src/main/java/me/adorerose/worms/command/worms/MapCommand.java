package me.adorerose.worms.command.worms;

import me.adorerose.worms.command.CommandPermission;
import me.adorerose.worms.command.PluginCommand;
import me.adorerose.worms.map.BuildingZoneMapper;
import me.adorerose.worms.map.selection.AreaSelection;
import me.adorerose.worms.service.profile.PlayerProfile;

@CommandPermission(permission = "worms.admin")
public class MapCommand extends PluginCommand {
    public MapCommand() {
        super("map");
    }

    @Override
    public void execute(PlayerProfile profile, String label, String[] args) {
        if (profile.isAdmin()) {
            AreaSelection area = profile.asAdmin().getSelectedArea();
            if (!area.entireSelected()) {
                profile.sendMessage(plugin.getLanguage().NOT_ENTIRE_SELECTED);
                return;
            }

            int bSize = plugin.getConfiguration().MAP__BUILDING_SIZE;
            if (bSize > area.lengthX() || bSize > area.lengthY() || bSize > area.lengthZ()) {
                profile.sendMessage(String.format(plugin.getLanguage().NOT_ENOUGH_SEL_VOLUME, bSize, bSize, bSize));
                return;
            }

            if (area.firstPoint().getWorld() != area.secondPoint().getWorld()) {
                profile.sendMessage(plugin.getLanguage().POINTS_IN_DIFF_WORLDS);
                return;
            }

            BuildingZoneMapper mapper = new BuildingZoneMapper();
            mapper.accept(area);
            profile.sendMessage(plugin.getLanguage().AREA_MAPPING_STARTED);
        }
    }
}
