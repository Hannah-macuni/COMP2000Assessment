public class Rain extends Weather{

	public Rain(int strength, int duration, int[][] location){
		super(strength, duration, location);
	}

	@Override
	public void affectSimulation(ForestFireSimulation simulation){
		for(int i = 0; i < location.length; i++){	
				int row = location[i][0];
				int column = location[i][1];

		simulation.removeHeat(row, column, strength);
	}		
	}
}