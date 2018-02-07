package app.we.go.emojidraw.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a stroke that the user has drawn.
 */
public class Stroke {
    private final List<Integer> xcoords;
    private final List<Integer> ycoords;

    public Stroke() {
        xcoords = new ArrayList<>();
        ycoords = new ArrayList<>();
    }

    public void addPoint(int x, int y) {
        xcoords.add(x);
        ycoords.add(y);
    }

    public List<Integer> getXcoords() {
        return xcoords;
    }

    public List<Integer> getYcoords() {
        return ycoords;
    }
}
