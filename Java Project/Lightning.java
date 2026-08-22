public class Lightning extends Weather{

	public int strikesPerSecond;

	public Lightning(int strength, int duration, int[][] location, int strikesPerSecond){
		super(strength, duration, location, strikesPerSecond);
		this.strikesPerSecond = strikesPerSecond;
	}

	public int getStrikesPerSecond(){
		return strikesPerSecond;
	}
}
