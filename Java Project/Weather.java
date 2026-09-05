//Abstract because it's more a concept than a solid thing.
public abstract class Weather{

	public int strength;
	public int duration;
	public int[][] location;

	public Weather(int strength, int duration, int[][] location){
		this.strength = strength;
		this.duration = duration;
		this.location = location;
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

	//Public so subclasses can override. Abstract so subclasses have to implement their own specific behaviour.
	public abstract void affectSimulation(ForestFireSimulation simulation);
}