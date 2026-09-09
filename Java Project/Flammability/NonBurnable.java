package Flammability;
public class NonBurnable implements FlammableStrategy {
    public void burn(int intensity, int dampener) {
        throw new UnsupportedOperationException("Tried to burn a non-burnable object");
    }

	public int getFuel() {
		throw new UnsupportedOperationException("Tried to get fuel of a non-burnable object");
	}

    public int getBurnRate() {
		throw new UnsupportedOperationException("Tried to get burnRate of a non-burnable object");
	}

	public void ignite() {
		throw new UnsupportedOperationException("Tried igniting a non-burnable object");
	}
}
