public class Grass extends Vegetation {
    private int density;

    public Grass(int age, int fuel, int burnRate, float moisture, int density) {
        super(age, fuel, burnRate, moisture);
        this.density = density;
    }

    public int getDensity() {
        return density;
    }

    public void setDensity(int density) {
        this.density = density;
    }

    @Override
    public double calculateSpreadHeat(int fireIntensity) {
        // Calls base calculation from Vegetation and factors in density
        return calculateSpreadHeat(fireIntensity) + this.density;
    }
}
