import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Grid<T> {
    private int rows;
    private int columns;
    private List<List<T>> cells;

    public Grid(int rows, int columns) {
        if (rows <= 0 || columns <= 0) {
            throw new IllegalArgumentException(
                "Rows and columns must be greater than zero"
            );
        }

        this.rows = rows;
        this.columns = columns;
        this.cells = new ArrayList<List<T>>();

        // Create each row
        for (int row = 0; row < rows; row++) {
            List<T> currentRow = new ArrayList<T>();

            // create columns in rows
            for (int column = 0; column < columns; column++) {
                currentRow.add(null);
            }

            cells.add(currentRow);
        }
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public T getCell(int row, int column) {
        if (!isInBounds(row, column)) {
            throw new IndexOutOfBoundsException(
                "Invalid grid position: ("
                    + row + ", " + column + ")"
            );
        }

        return cells.get(row).get(column);
    }

    public void setCell(int row, int column, T value) {
        if (!isInBounds(row, column)) {
            throw new IndexOutOfBoundsException(
                "Invalid grid position: ("
                    + row + ", " + column + ")"
            );
        }

        cells.get(row).set(column, value);
    }

    public void fill(T value) {
        forEachCell((row, column, oldValue) -> setCell(row, column, value));
    }

    // The one place that visits every cell: callers say what to do, Grid does the looping
    public void forEachCell(CellAction<T> action) {
        if (action == null) {
            throw new IllegalArgumentException("Cell action cannot be null");
        }

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                action.apply(row, column, cells.get(row).get(column));
            }
        }
    }

    // Cells are shared out across several threads.
    // The action may only change the cell it is given, never a neighbour or any shared value. 
    // The grid's lists are only read here, never resized, so reading them from several threads at once is safe.
    public void forEachCellInParallel(CellAction<T> action) {
        if (action == null) {
            throw new IllegalArgumentException("Cell action cannot be null");
        }

        // Number every cell 0 .. rows*columns-1, then turn each number back into (row, column)
        IntStream.range(0, rows * columns)
                .parallel()
                .forEach(index -> {
                    int row = index / columns;
                    int column = index % columns;
                    action.apply(row, column, cells.get(row).get(column));
                });
    }

    public boolean isInBounds(int row, int column) {
        return row >= 0 && row < rows && column >= 0 && column < columns;
    }

    public List<T> neighbours(int row, int column) {
        List<T> neighbourValues = new ArrayList<T>();

        for (Position position : neighbourPositions(row, column)) {
            T value = getCell(
                position.getRow(),
                position.getColumn()
            );

            neighbourValues.add(value);
        }

        return neighbourValues;
    }

    public List<Position> neighbourPositions(int row, int column) {
        if (!isInBounds(row, column)) {
            throw new IndexOutOfBoundsException(
                "Invalid grid position: ("
                    + row + ", " + column + ")"
            );
        }

        List<Position> positions = new ArrayList<Position>();

        for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
            for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {

                if (rowOffset == 0 && columnOffset == 0) {
                    continue;
                }

                int neighbourRow = row + rowOffset;
                int neighbourColumn = column + columnOffset;

                if (isInBounds(neighbourRow, neighbourColumn)) {
                    positions.add(new Position(neighbourRow, neighbourColumn));
                }
            }
        }
        return positions;
    }

    public static class Position {
        private int row;
        private int column;

        public Position(int row, int column) {
            this.row = row;
            this.column = column;
        }

        public int getRow() {
            return row;
        }

        public int getColumn() {
            return column;
        }
    }
}
