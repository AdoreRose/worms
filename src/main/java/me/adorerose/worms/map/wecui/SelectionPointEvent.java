package me.adorerose.worms.map.wecui;

import org.bukkit.Location;

public class SelectionPointEvent implements CUIEvent {
    public static final int POS1 = 0;
    public static final int POS2 = 1;

    protected final int id;
    protected final Location pos;
    protected final int area;

    public SelectionPointEvent(int id, Location pos, int area) {
        this.id = id;
        this.pos = pos;
        this.area = area;
    }

    @Override
    public String getTypeId() {
        return "p";
    }

    @Override
    public String[] getParameters() {
        return new String[] {
                    String.valueOf(id),
                    String.valueOf(pos.getBlockX()),
                    String.valueOf(pos.getBlockY()),
                    String.valueOf(pos.getBlockZ()),
                    String.valueOf(area)
                };
    }

}
