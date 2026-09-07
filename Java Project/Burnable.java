/**
 * Burnable
 */
public interface Burnable {
    boolean isBurning();
    void burn(int intensity);
    boolean isBurnedOut();
}