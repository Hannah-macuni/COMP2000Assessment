public abstract class Vegetation {
    private int age;
    private int fuel;
    private int burnRate;
    private double moisture;

    public Vegetation(int age, int fuel, int burnRate, double moisture){
        this.age = age;
        this.fuel = fuel;
        this.burnRate = burnRate;
        this.moisture = moisture;
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
}