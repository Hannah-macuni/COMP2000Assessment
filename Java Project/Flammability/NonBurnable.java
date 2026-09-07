package Flammability;
public class NonBurnable implements FlammableStrategy {
    public void burn(int intensity, int dampener) {
        throw new UnsupportedOperationException("Attempted to burn a non-burnable object");
    }

	public int getFuel() {
		throw new UnsupportedOperationException("Attempted to get fuel of a non-burnable object");
	}

    public int getBurnRate() {
		throw new UnsupportedOperationException("Attempted to get burnRate of a non-burnable object");
	}
}
