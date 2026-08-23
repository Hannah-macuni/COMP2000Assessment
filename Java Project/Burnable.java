/**
 * Burnable
 */
public interface Burnable {
    boolean isBurning();
    void ignite();
    void burn(int intensity);
    boolean isBurnedOut();
}