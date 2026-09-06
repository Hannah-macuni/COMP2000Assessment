import java.util.Random;

public class ForestFireSimulation {
    private Grid<Cell> grid;
    private Grid<Double> heatMap;
    private double ignitionThreshold;
    private WeatherManager weatherManager = new WeatherManager(this);

    public ForestFireSimulation(int rows, int columns, Random random) {
        if (random == null) {
            throw new IllegalArgumentException("Random cannot be null");
        }

        this.grid = new Grid<Cell>(rows, columns);
        initializeGrid(random);

        this.heatMap = new Grid<Double>(rows, columns);
        this.heatMap.fill(0.0);

        this.ignitionThreshold = 100.0;
    }

    private void initializeGrid(Random random) {
        for (int row = 0; row < this.grid.getRows(); row++) {
            for (int column = 0; column < this.grid.getColumns(); column++) {
                int terrainNumber = random.nextInt(100);
                Terrain terrain;

                if (terrainNumber < 60) {
                    terrain = new Tree();
                } else if (terrainNumber < 90) {
                    terrain = new Grass();
                } else {
                    terrain = new River();
                }

                this.grid.setCell(row, column, new Cell(terrain));
            }
        }
    }

    public Grid<Cell> getGrid() {
        return this.grid;
    }

    public Grid<Double> getHeatMap() {
        return this.heatMap;
    }

    public WeatherManager getWeatherManager(){
        return this.weatherManager;
    }

    public double getIgnitionThreshold() {
        return this.ignitionThreshold;
    }

    public void igniteCell(int row, int column, int intensity) {
        if (intensity <= 0) {
            throw new IllegalArgumentException(
                    "Fire intensity must be greater than zero");
        }

        Cell cell = this.grid.getCell(row, column);

        if (cell == null) {
            throw new IllegalStateException(
                    "The selected Grid position has no this Cell coordinate");
        }

        cell.ignite(intensity);
    }

    public void addHeat(int row, int column, double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException(
                    "Heat amount cannot be negative");
        }

        double currentHeat = this.heatMap.getCell(row, column);

        this.heatMap.setCell(row, column, currentHeat + amount);
    }

    public void removeHeat(int row, int column, double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException(
                    "Heat amount cannot be negative");
        }

        double currentHeat = this.heatMap.getCell(row, column);
        double newHeat = Math.max(0.0, currentHeat - amount);

        this.heatMap.setCell(row, column, newHeat);
    }

    public boolean hasReachedIgnitionThreshold(int row, int column) {
        return this.heatMap.getCell(row, column) >= this.ignitionThreshold;
    }

    public void update() {
        weatherManager.update();
        spreadFires();
        applyRiverCooling();
        updateCells();
        igniteHeatedCells();
        evolveTerrain();
    }

    private void igniteHeatedCells() {
        for (int row = 0; row < this.grid.getRows(); row++) {
            for (int column = 0; column < this.grid.getColumns(); column++) {
                if (!hasReachedIgnitionThreshold(row, column)) {
                    continue;
                }

                Cell cell = this.grid.getCell(row, column);

                if (!cell.canBurn()) {
                    this.heatMap.setCell(row, column, 0.0);
                    continue;
                }

                if (cell.isBurning()) {
                    continue;
                }

                int intensity = (int) Math.ceil(this.ignitionThreshold / 10.0);

                cell.ignite(intensity);

                if (cell.isBurning()) {
                    removeHeat(row, column, this.ignitionThreshold);
                }
            }
        }
    }

    private void updateCells() {
        for (int row = 0; row < this.grid.getRows(); row++) {
            for (int column = 0; column < this.grid.getColumns(); column++) {

                Cell cell = this.grid.getCell(row, column);
                cell.updateBurningState();
            }
        }
    }

    private void spreadFires() {
        for (int row = 0; row < this.grid.getRows(); row++) {
            for (int column = 0; column < this.grid.getColumns(); column++) {

                Cell sourceCell = this.grid.getCell(row, column);

                if (!sourceCell.isBurning()) {
                    continue;
                }

                try {
                    int fireIntensity = sourceCell.getFire().getIntensity();

                    double spreadHeat = fireIntensity;
                    Terrain terrain = sourceCell.getTerrain();

                    if (terrain instanceof Vegetation) {
                        Vegetation vegetation = (Vegetation) terrain;

                        spreadHeat = vegetation.calculateSpreadHeat(fireIntensity);
                    }

                    for (Grid.Position neighbour : this.grid.neighbourPositions(row, column)) {

                        addHeat(neighbour.getRow(), neighbour.getColumn(), spreadHeat);
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Can't burn on cell (" + row + ", " + column + ")");
                }
            }
        }
    }

    private void applyRiverCooling() {
        for (int row = 0; row < this.grid.getRows(); row++) {
            for (int column = 0; column < this.grid.getColumns(); column++) {

                Cell cell = this.grid.getCell(row, column);
                Terrain terrain = cell.getTerrain();

                if (!(terrain instanceof River)) {
                    continue;
                }

                River river = (River) terrain;
                double cooling = river.getCoolingStrength();

                removeHeat(row, column, cooling);

                for (Grid.Position neighbour : this.grid.neighbourPositions(row, column)) {

                    removeHeat(neighbour.getRow(), neighbour.getColumn(), cooling / 2.0);
                }
            }
        }
    }

    private void evolveTerrain() {
        for (int row = 0; row < this.grid.getRows(); row++) {
            for (int column = 0; column < this.grid.getColumns(); column++) {

                Cell cell = this.grid.getCell(row, column);

                if (cell != null
                        && !cell.isBurning()
                        && cell.getTerrain() != null) {

                    cell.getTerrain().update();
                }
            }
        }
    }
}
