//Abstract because it's more a concept than a solid thing.
public abstract class Weather{

	private int strength;
	private int duration;
	private int[][] location;

	protected Weather(int strength, int duration, int[][] location){
		if(strength < 0 || duration < 0){
			throw new IllegalArgumentException("Strength or Duration cannot be negative");
		}
		this.strength = strength;
		this.duration = duration;
		this.location = location;
	}

	public int getStrength(){
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