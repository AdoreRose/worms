package me.adorerose.worms.map.wecui;

public class SelectionPolygonEvent implements CUIEvent {

    protected final int[] vertices;

    public SelectionPolygonEvent(int... vertices) {
        this.vertices = vertices;
    }

    @Override
    public String getTypeId() {
        return "poly";
    }

    @Override
    public String[] getParameters() {
        final String[] ret = new String[vertices.length];
        int i = 0;
        for (int vertex : vertices) {
            ret[i++] = String.valueOf(vertex);
        }

        return ret;
    }

}
