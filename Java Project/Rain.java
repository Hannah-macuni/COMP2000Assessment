public class Rain extends Weather{

	public Rain(int strength, int duration, int[][] location){
		super(strength, duration, location);
	}

	@Override
	public void affectSimulation(ForestFireSimulation simulation){
		simulation.applyRain(strength, duration, location);
	}
		
}