public class Lightning extends Weather{

	public int strikesPerUpdate;

	public Lightning(int strength, int duration, int[][] location, int strikesPerUpdate){
		super(strength, duration, location);
		this.strikesPerUpdate = strikesPerUpdate;
	}

	public int getStrikesPerUpdate(){
		return strikesPerUpdate;
	}

	@Override
	public void affectSimulation(ForestFireSimulation simulation){
		simulation.applyLightning(strength, duration, location, strikesPerUpdate);
	}
}
