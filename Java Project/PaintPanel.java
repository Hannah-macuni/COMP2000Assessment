import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

@SuppressWarnings({"serial", "this-escape"})
public class PaintPanel extends JPanel {
    private ForestFireSimulation simulation;
    private int cellSize;

    public PaintPanel(ForestFireSimulation simulation) {
        if (simulation == null) {
            throw new IllegalArgumentException(
                "Simulation cannot be null"
            );
        }

        this.simulation = simulation;
        this.cellSize = 12;

        Grid<Cell> grid = this.simulation.getGrid();

        setPreferredSize(
            new Dimension(
                grid.getColumns() * this.cellSize,
                grid.getRows() * this.cellSize
            )
        );

        setBackground(Color.WHITE);
        setToolTipText(
            "Click a green cell to start a fire"
        );
    }

    public void setSimulation(ForestFireSimulation simulation) {
        if (simulation == null) {
            throw new IllegalArgumentException(
                "Simulation cannot be null"
            );
        }

        this.simulation = simulation;

        Grid<Cell> grid = this.simulation.getGrid();

        setPreferredSize(
            new Dimension(
                grid.getColumns() * this.cellSize,
                grid.getRows() * this.cellSize
            )
        );

        revalidate();
        repaint();
    }

    public Grid.Position getGridPositionAt(int x,int y) {

        if (x < 0 || y < 0) {
            return null;
        }

        int column = x / this.cellSize;
        int row = y / this.cellSize;

        Grid<Cell> grid = this.simulation.getGrid();

        if (!grid.isInBounds(row, column)) {
            return null;
        }

        return new Grid.Position(row, column);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Grid<Cell> grid = this.simulation.getGrid();
        drawGrid(graphics, grid);
    }

    private void drawGrid(Graphics graphics, Grid<Cell> grid) {
        for (int row = 0; row < grid.getRows(); row++) {
            for (int column = 0; column < grid.getColumns(); column++) {
                Cell cell = grid.getCell(row, column);

                drawCell(graphics, cell, row, column);
            }
        }
    }

    private void drawCell(Graphics graphics, Cell cell, int row, int column) {
        int x = column * this.cellSize;
        int y = row * this.cellSize;

        graphics.setColor(getCellColor(cell));

        graphics.fillRect(x, y, this.cellSize, this.cellSize);

        graphics.setColor(Color.DARK_GRAY);

        graphics.drawRect(x, y, this.cellSize - 1, this.cellSize - 1);
    }

    private Color getCellColor(Cell cell) {
        if (cell == null) {
            return Color.WHITE;
        }

        if (cell.isBurning()) {
            int intensity = cell.getFire().getIntensity();

            if (intensity >= 8) {
                return Color.RED;
            } else if (intensity >= 4) {
                return Color.ORANGE;
            } else {
                return Color.YELLOW;
            }
        }

        Terrain terrain = cell.getTerrain();

        if (terrain instanceof Burnable) {
            Burnable burnable = (Burnable) terrain;

            if (burnable.isBurnedOut()) {
                return Color.DARK_GRAY;
            }
        }

        if (terrain instanceof Tree) {
            return new Color(34, 110, 45);
        }

        if (terrain instanceof Grass) {
            return new Color(120, 190, 70);
        }

        if (terrain instanceof River) {
            return new Color(50, 140, 220);
        }

        return Color.LIGHT_GRAY;
    }
}
