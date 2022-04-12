package me.adorerose.worms.map.selection;

import org.bukkit.Location;

public interface AreaSelection {
    Location firstPoint();
    Location secondPoint();
    Location setFirstPoint(Location value);
    Location setSecondPoint(Location value);
    double deltaX();
    double deltaY();
    double deltaZ();
    boolean entireSelected();
}
