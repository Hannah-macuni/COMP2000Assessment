public class Tree extends Vegetation {
    private int height;

    public Tree(int age, int fuel, int burnRate, double moisture, int height) {
        super(age, fuel, burnRate, moisture);
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public double calculateSpreadHeat(int fireIntensity) {
        // Calls case calculation from Vegetation and adds the height bonus
        return super.calculateSpreadHeat(fireIntensity) + this.height;
    }

    @Override
    public void update() {
        super.update(); // Increases age via parent update logic
        if (getAge() % 5 == 0) {
            this.height++; // Increase height every 5 age units
        }
    }
}
