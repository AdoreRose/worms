package me.adorerose.worms.map;

import me.adorerose.worms.WormsPlugin;
import me.adorerose.worms.map.selection.AreaSelection;
import me.adorerose.worms.util.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Objects;

public class BuildingZoneMapper extends BukkitRunnable {
    private static final WormsPlugin plugin = WormsPlugin.getInstance();
    private Location pos1;
    private Location pos2;
    private World world;

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

    public BukkitTask accept(AreaSelection area) {
        this.pos1 = Objects.requireNonNull(area.firstPoint(), "pos1");
        this.pos2 = Objects.requireNonNull(area.secondPoint(), "pos2");
        this.world = pos1.getWorld();
        this.bSize = (byte) (plugin.getConfiguration().MAP__BUILDING_SIZE >> 1);
        this.bPadding = plugin.getConfiguration().MAP__BUILDING_PADDING;

        this.lengthX = area.lengthX();
        this.lengthY = area.lengthY();
        if (lengthY == 0) lengthY = 1;
        this.lengthZ = area.lengthZ();
        this.laySquare = lengthX * lengthZ;
        this.sfMapSize = laySquare * lengthY;
        this.surfaceMap = new byte[sfMapSize];

        return runTaskAsynchronously(plugin);
    }

    @Override
    public void run() {
        long now = System.currentTimeMillis();
        int idx = 0;
        for (int y = pos1.getBlockY(); y <= pos2.getBlockY(); y++) {
            evalLayZBorder(y - pos1.getBlockY());
            for (int z = pos1.getBlockZ(); z <= pos2.getBlockZ(); z++) {
                for (int x = pos1.getBlockX(); x <= pos2.getBlockX(); x++) {
                    if (world.getBlockAt(x, y, z).getType() != Material.AIR) {
                        surfaceMap[idx] = bSize;
                        smoothSurface(idx);
                    }
                    idx++;
                }
            }
        }

        Bukkit.broadcastMessage(plugin.getLanguage().CUSTOM +
                TextUtils.coloredFormat("Анализ завершён (t=%s). Вывод содержимого карты поверхностей...", System.currentTimeMillis() - now));

        idx = 0;
        for (int y = pos1.getBlockY(); y <= pos2.getBlockY(); y++) {
            Bukkit.broadcastMessage(plugin.getLanguage().CUSTOM + TextUtils.coloredFormat(
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
            for (int i = rows.size() - 1; i >= 0; i--) Bukkit.broadcastMessage(rows.get(i));
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

}
