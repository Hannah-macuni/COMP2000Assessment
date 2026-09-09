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

    public Terrain getTerrain() {
        return terrain;
    }

    public boolean isBurning() {
        if (fire != null) {
            return true;
        }
        return false;
    }

    public boolean canBurn() {
        if (terrain != null) {
            return terrain.canBurn();
        }
        return false;
    }

    public void updateBurningState() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateBurningState'");
    }

    public void ignite(int intensity) {
        if (!hasTerrain())
            throw new IllegalStateException("Attempted to ignite a cell with no terrain.");
        fire = new Fire(intensity);
        terrain.ignite();
    }

    public Fire getFire() {
        return fire;
    }
}
