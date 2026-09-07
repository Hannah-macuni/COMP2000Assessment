import Flammability.FlammableStrategy;

public abstract class Terrain {
    public abstract void update();
    public FlammableStrategy flammable;
    public boolean canBurn(){
        return flammable.canBurn();
    }
    public boolean isBurnedOut(){
        return flammable.isBurnedOut();
    }
}
 