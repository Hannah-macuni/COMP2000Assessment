import Flammability.Burnable;

public abstract class Vegetation extends Terrain{
    private int age;
    private float moisture;

    public Vegetation(int age, int fuel, int burnRate, float moisture){
        this.age = age;
        flammable = new Burnable(fuel, burnRate);
        this.moisture = moisture;
    }

    public int getAge(){
        return age;
    }

    public int getFuel(){
        return flammable.getFuel();
    }

    public int getBurnRate(){
        return flammable.getBurnRate();
    }

    public double getMoisture(){
        return moisture;
    }

    public void setMoisture(float moisture){
        this.moisture = moisture;
    }

    public abstract double calculateSpreadHeat(int fireIntensity);
    public void update(){
        age++;
    }
}