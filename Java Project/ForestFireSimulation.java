import java.util.Random;

public class ForestFireSimulation {
    private Grid<Cell> grid;
    private Grid<Double> heatMap;
    private double ignitionThreshold;
    private WeatherManager weatherManager = new WeatherManager();

    public ForestFireSimulation(int rows, int columns, Random random) {
        if (random == null) {
            throw new IllegalArgumentException("Random cannot be null");
        }

        grid = new Grid<Cell>(rows, columns);
        initializeGrid(random);

        heatMap = new Grid<Float>(rows, columns);
        heatMap.fill(0f));

        //weather = null; Is this needed?

        ignitionThreshold = 100f;
    }

    private void initializeGrid(Random random) {
        for (int row = 0; row < grid.getRows(); row++) {
            for (int column = 0; column < grid.getColumns(); column++) {
                int terrainNumber = random.nextInt(100);
                Terrain terrain;

                if (terrainNumber < 60) {                    
                    terrain = new Tree();
                } else if (terrainNumber < 90) {
                    terrain = new Grass();
                } else {
                    terrain = new River();
                }

                grid.setCell(row, column, new Cell(terrain));
            }
        }
    }

    public Grid<Cell> getGrid() {
        return grid;
    }

    public WeatherManager getWeatherManager(){
        return this.weatherManager;
    }

    public float getIgnitionThreshold() {
        return ignitionThreshold;
    }

    public void igniteCell(int row, int column, int intensity) {
        if (intensity <= 0) {
            throw new IllegalArgumentException(
                    "Fire intensity must be greater than zero");
        }

        Cell cell = grid.getCell(row, column);

        if (cell == null) {
            throw new IllegalStateException(
                    "The selected Grid position has no this Cell coordinate");
        }

        cell.ignite(intensity);
    }

    public void addHeat(int row, int column, float amount) {
        if (amount < 0) {
            throw new IllegalArgumentException(
                    "Heat amount cannot be negative");
        }

        float currentHeat = heatMap.getCell(row, column);

        heatMap.setCell(row, column, currentHeat + amount);
    }

    public void removeHeat(int row, int column, float amount) {
        if (amount < 0) {
            throw new IllegalArgumentException(
                    "Heat amount cannot be negative");
        }

        float currentHeat = heatMap.getCell(row, column);
        float newHeat = Math.max(0f, currentHeat - amount);

        heatMap.setCell(row, column, newHeat);
    }

    public boolean hasReachedIgnitionThreshold(int row, int column) {
        return heatMap.getCell(row, column) >= ignitionThreshold;
    }

    public void update() {
        weatherManager.update(this);
        spreadFires();
        applyRiverCooling();
        updateCells();
        igniteHeatedCells();
        evolveTerrain();
    }

    public void addWind(int direction, int strength) {
        for(int row = 0; row < grid.getRows(); row++){
            for (int column = 0; column < grid.getColumns(); column++){
                Cell cell = grid.GetCell(row, column);
                if(cell.isBurning){
                    switch (direction) {
                    //East
                    case 1:    
                    addHeat(row, column + 1, strength);
                        break;
                
                    //South
                    case 2:
                        addHeat(row + 1, column, strength);
                        break;

                    //West
                    case 3:
                        addHeat(row, column - 1, strength);
                        break;

                    //North
                    default:
                        addHeat(row - 1, column, strength);
                        break;
                }
                }               
            }
        }
    }

    private void igniteHeatedCells() {
        for (int row = 0; row < grid.getRows(); row++) {
            for (int column = 0; column < grid.getColumns(); column++) {
                if (!hasReachedIgnitionThreshold(row, column)) {
                    continue;
                }

                Cell cell = grid.getCell(row, column);

                if (!cell.canBurn()) {
                    heatMap.setCell(row, column, 0f);
                    continue;
                }

                if (cell.isBurning()) {
                    continue;
                }

                int intensity = (int) Math.ceil(ignitionThreshold / 10f);

                cell.ignite(intensity);

                if (cell.isBurning()) {
                    removeHeat(row, column, ignitionThreshold);
                }
            }
        }
    }

    private void updateCells() {
        for (int row = 0; row < grid.getRows(); row++) {
            for (int column = 0; column < grid.getColumns(); column++) {

                Cell cell = grid.getCell(row, column);
                cell.updateBurningState();
            }
        }
    }

    private void spreadFires() {
        for (int row = 0; row < grid.getRows(); row++) {
            for (int column = 0; column < grid.getColumns(); column++) {

                Cell sourceCell = grid.getCell(row, column);

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
        for (int row = 0; row < grid.getRows(); row++) {
            for (int column = 0; column < grid.getColumns(); column++) {

                Cell cell = grid.getCell(row, column);
                Terrain terrain = cell.getTerrain();

                if (!(terrain instanceof River)) {
                    continue;
                }

                River river = (River) terrain;
                float cooling = river.getCoolingStrength();

                removeHeat(row, column, cooling);

                for (Grid.Position neighbour : grid.neighbourPositions(row, column)) {

                    removeHeat(neighbour.getRow(), neighbour.getColumn(), cooling / 2.0f);
                }
            }
        }
    }

    private void evolveTerrain() {
        for (int row = 0; row < this.grid.getRows(); row++) {
            for (int column = 0; column < this.grid.getColumns(); column++) {

                Cell cell = grid.getCell(row, column);

                if (cell != null
                        && !cell.isBurning()
                        && cell.getTerrain() != null) {

                    cell.getTerrain().update();
                }
            }
        }
    }
}
