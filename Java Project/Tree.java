public class Tree extends Vegetation{
    private int height;

    public Tree(int age, int fuel, int burnRate, double moisture, int height) {
        super(age, fuel, burnRate, moisture);
        this.height = height;
    }

    public int getHeight() {
        return height;
    } 

    public void setHeight(int height) {
        this.height = height;
    }
}
