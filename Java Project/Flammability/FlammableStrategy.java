package Flammability;
public abstract interface FlammableStrategy {
    default boolean canBurn(){
        return false;
    };
    default boolean isBurnedOut(){
        return false;
    };
    default boolean isBurning(){
        return false;
    }
    void burn(int intensity, float dampener);
    void ignite();
    int getFuel();
    int getBurnRate();
}
