public class Tree extends Vegetation {
    private int height;

    public Tree(int age, int fuel, int burnRate, float moisture, int height) {
        super(age, fuel, burnRate, moisture);
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public float calculateSpreadHeat(int fireIntensity) {
        // Calls case calculation from Vegetation and adds the height bonus
        return calculateSpreadHeat(fireIntensity) + this.height;
    }

    public void update() {
        super.update(); // Increases age via parent update logic
        if (getAge() % 5 == 0) {
            this.height++; // Increase height every 5 age units
        }
    }
}
