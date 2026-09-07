public class Cell {
    Terrain terrain;
    Fire fire;

    public Cell() {
        terrain = null;
        fire = null;
    }

    public Cell(Terrain t) {
        terrain = t;
        fire = null;
    }

    public Cell(Terrain t, Fire f) {
        terrain = t;
        fire = f;
    }

    public Cell(Fire f) {
        terrain = null;
        fire = f;
    }

    public Boolean hasFire() {
        if (fire != null) {
            return true;
        }
        return false;
    }

    public Boolean hasTerrain() {
        if (terrain != null) {
            return true;
        }
        return false;
    }

    public void applyWeather(Weather w) {
        if (terrain != null) {
            terrain.applyWeather(w);
            // TODO in Terrain - add an applyWeather class that calls the Weather to modify
            // the cell?
            return;
        }
        return;
    }
    // TODO perhaps implement a copy constructor? Would need a clone() function on
    // Terrain that's implemented by subclasses.

    public Terrain getTerrain() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTerrain'");
    }

    public boolean isBurning() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isBurning'");
    }

    public boolean canBurn() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'canBurn'");
    }

    public void updateBurningState() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateBurningState'");
    }

    public void ignite(int intensity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'ignite'");
    }

    public Fire getFire() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFire'");
    }
}