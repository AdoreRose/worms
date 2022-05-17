package me.adorerose.worms.map;

import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitPlayer;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.extension.platform.permission.ActorSelectorLimits;
import com.sk89q.worldedit.internal.cui.SelectionShapeEvent;
import com.sk89q.worldedit.regions.selector.ConvexPolyhedralRegionSelector;
import me.adorerose.worms.WormsPlugin;
import me.adorerose.worms.map.selection.AreaSelection;
import me.adorerose.worms.service.profile.AdminProfile;
import me.adorerose.worms.util.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static com.sk89q.worldedit.extension.platform.permission.ActorSelectorLimits.forActor;
import static org.bukkit.plugin.java.JavaPlugin.getPlugin;

public class BuildingZoneMapper extends BukkitRunnable {
    private static final WormsPlugin plugin = WormsPlugin.getInstance();
    private Location pos1;
    private Location pos2;
    private World world;
    private static final int ZONE_SIGN = -1;
    private final AdminProfile holder;

    private ConvexPolyhedralRegionSelector selector;
    private ActorSelectorLimits selectorLimits;
    private BukkitPlayer player;
    private LocalSession session;

    /** Размер карты поверхностей */
    int sfMapSize;
    /** Карта поверхностей */
    byte[] surfaceMap;
    /** Длины сторон выделенной области на каждой координатной оси */
    int lengthX, lengthY, lengthZ;
    /** Размер зоны постройки */
    byte bSize;
    /** Размер отступа вокруг зоны постройки */
    int bPadding;
    /**Площадь слоя области*/
    int laySquare;
    /** Нижняя и верхняя граница сглаживания поверхности по аппликате */
    int w_zmn, w_zmx;
    /** Граница исследования потенциальных зон на области */
    int z_e;

    public BuildingZoneMapper(AdminProfile holder) {
        this.holder = holder;
    }

    public BukkitTask accept(AreaSelection area) {
        this.pos1 = Objects.requireNonNull(area.firstPoint(), "pos1");
        this.pos2 = Objects.requireNonNull(area.secondPoint(), "pos2");
        this.world = pos1.getWorld();
        this.bSize = (byte) plugin.getConfiguration().MAP__BUILDING_SIZE;
        this.bPadding = plugin.getConfiguration().MAP__BUILDING_PADDING;

        fitToSquare(area);
        this.lengthX = area.lengthX();
        this.lengthY = area.lengthY();
        if (lengthY == 0) lengthY = 1;
        this.lengthZ = area.lengthZ();
        this.laySquare = lengthX * lengthZ;
        this.sfMapSize = laySquare * lengthY;
        this.surfaceMap = new byte[sfMapSize];
        this.z_e = bSize + (bPadding << 1);

        WorldEdit we = WorldEdit.getInstance();
        session = we.getSessionManager().findByName(holder.getPlayer().getName());
        if (session != null) {
            com.sk89q.worldedit.world.World world = WorldEdit.getInstance().getServer().getWorlds().get(0);
            player = new BukkitPlayer(getPlugin(WorldEditPlugin.class), we.getServer(), holder.getPlayer());
            selector = new ConvexPolyhedralRegionSelector(world);
            selectorLimits = forActor(player);

            session.setRegionSelector(world, selector);
            player.dispatchCUIEvent(new SelectionShapeEvent("polyhedron"));
            session.describeCUI(player);
        }
        return runTaskAsynchronously(plugin);
    }

    @Override
    public void run() {
        int idx = 0;
        byte bSizeHalf = (byte) (bSize >> 1);
        session.dispatchCUIEvent(player, new SelectionShapeEvent("polyhedron"));

        for (int y = pos1.getBlockY(); y <= pos2.getBlockY(); y++) {
            evalLayZBorder(y - pos1.getBlockY());
            for (int z = pos1.getBlockZ(); z <= pos2.getBlockZ(); z++) {
                for (int x = pos1.getBlockX(); x <= pos2.getBlockX(); x++) {
                    if (world.getBlockAt(x, y, z).getType() != Material.AIR) {
                        surfaceMap[idx] = bSizeHalf;
                        smoothSurface(idx);
                    }
                    idx++;
                }
            }
        }

        retrieveZones();
        int[] offsets = selectZones();

    }

    private void fitToSquare(AreaSelection area) {
        int lenX = area.lengthX();
        int lenZ = area.lengthZ();
        int deltaLen = lenX - lenZ;
        if (deltaLen > 0) area.secondPoint().add(0, 0, deltaLen);
        else area.secondPoint().subtract(deltaLen, 0, 0);
    }

    private Location toWorldLocation(int pos) {
        int y = pos / laySquare;
        int z = pos1.getBlockZ() + (pos - laySquare * y) / lengthX;
        int x = pos1.getBlockX() + pos % lengthX;
        return new Location(world, x, pos1.getBlockY() + y, z);
    }

    private void dumpMap() {
        int idx = 0;
        for (int y = pos1.getBlockY(); y <= pos2.getBlockY(); y++) {
            System.out.println(plugin.getLanguage().CUSTOM + TextUtils.coloredFormat(
                    "Слой %s (y = %s):", y - pos1.getBlockY(), y));
            ArrayList<String> rows = new ArrayList<>(lengthZ);
            for (int z = pos1.getBlockZ(); z <= pos2.getBlockZ(); z++) {
                StringBuilder rowVals = new StringBuilder();
                for (int x = pos1.getBlockX(); x <= pos2.getBlockX(); x++) {
                    rowVals.append(String.valueOf(surfaceMap[idx]));
                    idx++;
                }
                rows.add(rowVals.toString());
            }
            for (int i = rows.size() - 1; i >= 0; i--) System.out.println(rows.get(i));
        }
    }

    private void evalLayZBorder(int lay) {
        this.w_zmn = laySquare * lay + lengthX - 1;
        this.w_zmx = laySquare * lay + lengthX * (lengthZ - 1);
    }

    private void smoothSurface(final int idx) {
        for (int widx = idx; widx > 0 && surfaceMap[widx] > 1 && widx % lengthX != 0;) {
            widx--;
            surfaceMap[widx] = (byte) Math.max(surfaceMap[widx], surfaceMap[widx + 1] - 1);
        }
        for (int widx = idx; surfaceMap[widx] > 1 && widx % lengthX != lengthX - 1;) {
            widx++;
            surfaceMap[widx] = (byte) Math.max(surfaceMap[widx], surfaceMap[widx - 1] - 1);
        }
        for (int widx = idx; surfaceMap[widx] > 1 && widx < w_zmx;) {
            widx += lengthX;
            surfaceMap[widx] = (byte) Math.max(surfaceMap[widx], surfaceMap[widx - lengthX] - 1);
            if (surfaceMap[widx] > 1 && widx + lengthX < w_zmx && surfaceMap[widx + lengthX] < 1) smoothSurface(widx);
        }
        for (int widx = idx; surfaceMap[widx] > 1 && widx > w_zmn;) {
            widx -= lengthX;
            surfaceMap[widx] = (byte) Math.max(surfaceMap[widx], surfaceMap[widx + lengthX] - 1);
            if (surfaceMap[widx] > 1 && widx - lengthX > w_zmn && surfaceMap[widx - lengthX] < 1) smoothSurface(widx);
        }
    }

    private void retrieveZones() {
        for (int y = 0; y <= sfMapSize - laySquare * (bSize + 1); y += laySquare) {
            for (int z = y; z <= y + lengthX * (lengthZ - bSize); z += lengthX) {
                for (int x = z; x <= z + lengthX - bSize; x++) {
                    if (checkArea(x)) {
                        Bukkit.broadcast(TextUtils.coloredFormat("Signed %s as potential zone.", x), "*");
//                        for (Player player : Bukkit.getOnlinePlayers()) player.sendBlockChange(toWorldLocation(x), Material.BEACON, (byte) 0);
                    }
                }
            }
        }
    }

    private boolean checkArea(int idx) {
        for (int z = idx, idxmx = idx + lengthX * bSize; z < idxmx; z += lengthX) {
            for (int x = z; x < z + bSize; x++) {
                if (surfaceMap[x] <= 0) return false;
            }
        }

        idx += laySquare;
        for (int y = idx, ymax = idx + laySquare * bSize; y < ymax; y += laySquare) {
            for (int z = y, zmax = y + bSize * lengthZ; z < zmax; z += lengthX) {
                for (int  x = z; x < z + bSize; x++) {
                    if (surfaceMap[x] != 0) return false;
                }
            }
        }
        surfaceMap[idx] = ZONE_SIGN;
        return true;
    }

    private int[] selectZones() {
        int[] preferredOffsets = new int[lengthY - bSize + 1];
        for (int y = 0, offsetIdx = 0; y <= sfMapSize - laySquare * bSize; y += laySquare) {
            int[] zoneCount = new int[z_e * z_e];
            for (int z = y, cntIdx = 0; z < y + lengthX * z_e; z += lengthX) {
                for (int x = z; x < z + z_e; x++) {
                    zoneCount[cntIdx++] = countZones(y, z, x);
                }
            }
            StringBuilder sb = new StringBuilder(128);
            int maxIdx = -1;
            for (int max = 0, i = 0; i < zoneCount.length; i++) {
                if (zoneCount[i] > max) {
                    max = zoneCount[i];
                    maxIdx = i;
                }
                sb.append(i).append("=").append(zoneCount[i]).append("; ");
            }
            preferredOffsets[offsetIdx++] = maxIdx;
            System.out.println(TextUtils.coloredFormat("At lay %s => %s", y, sb.toString()));
        }
        Bukkit.broadcastMessage(Arrays.toString(preferredOffsets));
        return preferredOffsets;
    }

    private int countZones(int y0, int z0, int idx) {
        int count = 0;
        for (int z = idx, dZ = z_e  * lengthX; z < y0 + laySquare; z += dZ) {
            for (int x = z; x < z0 + lengthX; x += z_e) {
                if (surfaceMap[x] == -1) count++;
            }
        }
        return count;
    }
}
