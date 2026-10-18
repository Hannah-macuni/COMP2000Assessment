public class Wind extends Weather{

	private int direction;

	public Wind(int strength, int duration, int[][] location, int direction){
		super(strength, duration, location);
		this.direction = direction;
	}

	public int getDirection(){
		return direction;
	}

	@Override
	public void affectSimulation(ForestFireSimulation simulation){
		simulation.addWind(direction, strength);
	}
}