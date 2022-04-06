package me.adorerose.worms.map.selection;

import org.bukkit.Location;

public class CuboidAreaSelection implements AreaSelection {
    private Location firstPoint;
    private Location secondPoint;

    @Override
    public Location firstPoint() {
        return firstPoint;
    }

    @Override
    public Location secondPoint() {
        return secondPoint;
    }

    @Override
    public void setFirstPoint(Location value) {
        placeAscending(value, secondPoint);
    }

    @Override
    public void setSecondPoint(Location value) {
        placeAscending(firstPoint, value);
    }

    private void placeAscending(Location pos1, Location pos2) {
        if (pos1 != null && pos2 != null) {
            if (pos1.getX() > pos2.getX()) {
                pos1.add(pos2.getX(), 0, 0);
                pos2.setX(pos1.getX() - pos2.getX());
                pos1.setX(pos1.getX() - pos2.getX());
            }
            if (pos1.getY() > pos2.getY()) {
                pos1.add(0, pos2.getY(), 0);
                pos2.setY(pos1.getY() - pos2.getY());
                pos1.setY(pos1.getY() - pos2.getY());
            }
            if (pos1.getZ() > pos2.getZ()) {
                pos1.add(0, 0, pos2.getZ());
                pos2.setZ(pos1.getZ() - pos2.getZ());
                pos1.setZ(pos1.getZ() - pos2.getZ());
            }
        }
        this.firstPoint = pos1;
        this.secondPoint = pos2;
    }

    @Override
    public double deltaX() {
        return firstPoint.getBlockX() - secondPoint.getBlockX();
    }

    @Override
    public double deltaY() {
        return firstPoint.getBlockY() - secondPoint.getBlockY();
    }

    @Override
    public double deltaZ() {
        return firstPoint.getBlockZ() - secondPoint.getBlockZ();
    }

    @Override
    public boolean entireSelected() {
        return firstPoint != null && secondPoint != null;
    }
}
