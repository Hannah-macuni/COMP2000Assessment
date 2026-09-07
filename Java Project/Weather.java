public class Weather{

//TODO: Decide on strength scale. Suggest no larger than 0 to 5. Perhaps 0 for nothing, 1 for mild, 2 for strong? Then do a switch case to determine which number means what input...

	public int strength;
	public int duration;
	public int[][] location;

	public Weather(int strength, int duration, int[][] location){
		this.strength = strength;
		this.duration = duration;
	}

	public int getstrength(){
		return strength;
	}

	public int getDuration(){
		return duration;
	}

	public int[][] getLocation(){
		return location;
	}

	public void affectTerrain(Terrain t){
		//TODO implement
	}
}