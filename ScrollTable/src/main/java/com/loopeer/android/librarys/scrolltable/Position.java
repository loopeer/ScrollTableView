package com.loopeer.android.librarys.scrolltable;

public class Position {

    public int x;
    public int y;

    public String getPositionString() {
        return Integer.toString(x) + ":" + Integer.toString(y);
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Position)) {
            return false;
        } else {
            return x == ((Position)o).x && y == ((Position)o).y;
        }
    }
}
