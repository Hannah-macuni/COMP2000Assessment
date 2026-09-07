/**
 * Burnable
 */
public class Burnable implements FlammableStrategy {
    boolean isBurning();
    void burn(int intensity);
    boolean isBurnedOut();
    public boolean canBurn(){
        return true;
    }
}