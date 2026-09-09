import Flammability.NonBurnable;

public class River extends Terrain {
    private float coolingStrength;

    public River(float coolingStrength) {
        this.flammable = new NonBurnable();
        this.coolingStrength = coolingStrength;
    }

    @Override
    public void update() {
        // Keeps the Terrain unchanged by default
    }

    public float getCoolingStrength() {
        return this.coolingStrength;
    }
}