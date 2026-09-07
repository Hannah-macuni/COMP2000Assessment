public abstract class Vegetation extends Terrain{
    private int age;
    private int fuel;
    private int burnRate;
    private double moisture;
    private boolean burning;
    public static FlammableStrategy flammable = new Burnable();

    public Vegetation(int age, int fuel, int burnRate, double moisture){
        this.age = age;
        this.fuel = fuel;
        this.burnRate = burnRate;
        this.moisture = moisture;
        this.burning = false;
    }

    public int getAge(){
        return age;
    }

    public int getFuel(){
        return fuel;
    }

    public int getBurnRate(){
        return burnRate;
    }

    public double getMoisture(){
        return moisture;
    }

    public void setMoisture(double moisture){
        this.moisture = moisture;
    }

    @Override
    public boolean isBurning(){
        return burning;
    }

    @Override
    public void burn (int intensity){
        if(!burning){
            return;
        }

        if(intensity<0){
            throw new IllegalArgumentException("Fire intensity cannot be negative: " + intensity);
        }

        int fuelLoss = (int)(intensity * burnRate * (1 - moisture)); //how much fuel is lost after a burn
        fuel = Math.max(0, fuel - fuelLoss); //remove fuel after its gone

        if(isBurnedOut()){
            burning = false;
        }
    }

    @Override
    public boolean isBurnedOut() {
        return fuel <= 0;
    }

    public abstract double calculateSpreadHeat(int fireIntensity);
    public void update(){
        age++;
    }
}