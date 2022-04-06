package me.adorerose.worms.map.selection;

import org.bukkit.Location;

public interface AreaSelection {
    Location firstPoint();
    Location secondPoint();
    void setFirstPoint(Location value);
    void setSecondPoint(Location value);
    double deltaX();
    double deltaY();
    double deltaZ();
    boolean entireSelected();
}
