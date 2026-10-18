import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class WeatherManager {
    private static final int REGION_SIZE = 15;

    private ArrayList<Weather> weatherList = new ArrayList<>();
    private int rows;
    private int columns;
    private Random random;

    public WeatherManager(int rows, int columns, Random random) {
        this.rows = rows;
        this.columns = columns;
        this.random = random;
    }

    // Every {row, column} pair on the grid
    private int[][] wholeGrid() {
        int[][] location = new int[rows * columns][];
        int index = 0;

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                location[index++] = new int[] { row, column };
            }
        }

        return location;
    }

    // A REGION_SIZE x REGION_SIZE square at a random position, clipped to the grid
    private int[][] randomRegion() {
        int height = Math.min(REGION_SIZE, rows);
        int width = Math.min(REGION_SIZE, columns);
        int startRow = random.nextInt(rows - height + 1);
        int startColumn = random.nextInt(columns - width + 1);

        int[][] location = new int[height * width][];
        int index = 0;

        for (int row = startRow; row < startRow + height; row++) {
            for (int column = startColumn; column < startColumn + width; column++) {
                location[index++] = new int[] { row, column };
            }
        }

        return location;
    }

    public boolean isActive(Weather w) {
        if (w.getDuration() <= 0) {
            return false;
        }

        return true;
    }

    public void createWeather(String weather) {
        Weather w = null;

        switch (weather) {
            case "Rain":
                w = new Rain(8, 20, randomRegion());
                break;
            case "Heatwave":
                w = new Heatwave(2, 20, randomRegion());
                break;
            case "Lightning":
                w = new Lightning(10, 1, wholeGrid(), 3);
                break;
            default:
                break;
        }

        if (w != null) {
            weatherList.add(w);
        }
    }

    public void createWeather(String weather, String selectedDirection) {
        Weather w = null;
        int direction = 0;

        switch (selectedDirection) {
            case "East":
                direction = 1;
                break;
            case "South":
                direction = 2;
                break;
            case "West":
                direction = 3;
                break;
            default:
                break;
        }

        w = new Wind(8, 20, null, direction);

        if (w != null) {
            weatherList.add(w);
        }
    }

    public void advanceDecrement(Weather w) {
        w.decrementDuration();
    }

    public void update(ForestFireSimulation simulation) {
        Iterator<Weather> it = weatherList.iterator();

        while (it.hasNext()) {
            Weather w = it.next();
            w.affectSimulation(simulation);
            advanceDecrement(w);

            if (!isActive(w)) {
                it.remove();
            }
        }
    }
}
