package Flammability;
/**
 * Burnable
 */
public class Burnable implements FlammableStrategy {
    private boolean burning;
    private int fuel;
    private int burnRate;

    public Burnable(){
        burning = false;
        fuel = 0;
        burnRate = 0;
    }

    public Burnable(int f, int bR) {
        burning = false;
        fuel = f;
        burnRate = bR;
    }

    public boolean canBurn(){
        return true;
    }

    public boolean isBurning(){
        return burning;
    }

    public void burn (int intensity, float dampener){
        if(!burning){
            return;
        }

        if(intensity<0){
            throw new IllegalArgumentException("Fire intensity cannot be negative: " + intensity);
        }

        int fuelLoss = (int)(intensity * burnRate * (1 - dampener)); //how much fuel is lost after a burn
        fuel = Math.max(0, fuel - fuelLoss); //remove fuel after its gone

        if(isBurnedOut()){
            burning = false;
        }
    }

    public boolean isBurnedOut() {
        return fuel <= 0;
    }

    public void ignite(){
        burning = true;
    }

    public int getFuel() {
        return fuel;
    }

    public int getBurnRate() {
        return burnRate;
    }
}