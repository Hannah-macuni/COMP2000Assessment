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
		for(int i = 0; i < strikesPerUpdate; i++){
			//Math.random is between 0.0 and 1.0, multipley it by the length of the location array to get a number that will be within the required range.
			int randomIndex = (int)Math.random() * location.length;

			//Get a single coordinate value within the specified range.
			int[] coordinate = location[randomIndex];

			//Get individual row and column info.
			int row = coordinate[0];
			int column = coordinate[1];

			simulation.igniteCell(row, column, strength);
		}		
	}
}
