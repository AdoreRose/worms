package me.adorerose.worms.map.wecui;

import org.bukkit.Location;

public class SelectionPoint2DEvent implements CUIEvent {

    protected final int id;
    protected final int blockx;
    protected final int blockz;
    protected final int area;

    public SelectionPoint2DEvent(int id, Location pos, int area) {
        this.id = id;
        this.blockx = pos.getBlockX();
        this.blockz = pos.getBlockZ();
        this.area = area;
    }

    @Override
    public String getTypeId() {
        return "p2";
    }

    @Override
    public String[] getParameters() {
        return new String[] {
                    String.valueOf(id),
                    String.valueOf(blockx),
                    String.valueOf(blockz),
                    String.valueOf(area)
                };
    }

}
