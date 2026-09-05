public class Heatwave extends Weather{

	public Heatwave(int strength, int duration, int location[][]){
		super(strength, duration, location);
	}

	@Override
	public void affectSimulation(ForestFireSimulation simulation){
		simulation.applyHeatwave(strength, duration, location);
	}
}