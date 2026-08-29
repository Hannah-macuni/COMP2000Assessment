import java.util.ArrayList;
import java.util.List;

public class Grid<T> {
    ArrayList<ArrayList<T>> grid;
    int dx;
    int dy;

    public Grid() {
        // Returns a Grid of type T with initial dimensions 10x10
        dx = 10;
        dy = 10;
        grid = new ArrayList<>(dx);
        for (int i = 0; i < dx; i++) {
            ArrayList<T> reference = grid.get(i);
            reference = new ArrayList<T>(dy);
        }
    }

    public Grid(int dx, int dy) {
        // Returns a Grid of type T with dimensions dx x dy
        this.dx = dx;
        this.dy = dy;
        grid = new ArrayList<>(dx);
        for (int i = 0; i < dx; i++) {
            ArrayList<T> reference = grid.get(i);
            reference = new ArrayList<T>(dy);
        }
    }

    public Grid(Grid<T> g) {
        // Returns a Grid of type T with dimensions copied from another Grid.
        // NB: This method only copies the size of the Grid, not any elements in it. -JA
        this.dx = g.dx;
        this.dy = g.dy;
        grid = new ArrayList<>();
        for (int i = 0; i < dx; i++) {
            ArrayList<T> reference = grid.get(i);
            reference = new ArrayList<T>(dy);
        }
    }

    public T getCell(int row, int column) {
        return grid.get(row).get(column);
    }

    public void setCell(int row, int column, T element) {
        grid.get(row).set(column, element);
    }

    public void fill(T element) {
        for (ArrayList<T> arrayList : grid) {
            for (int i = 0; i < arrayList.size(); i++) {
                arrayList.set(i, element);
            }
        }
    }

    public boolean isInBounds(int row, int column) {
        if (grid.size() > row + 1) {
            return false;
        } else if (grid.get(row).size() > column + 1) {
            return false;
        }
        return true;
    }

    public List<T> neighbors(int row, int column) {
        if (!isInBounds(row, column))
            return null;

        ArrayList<T> list = new ArrayList<>();
        if (!isInBounds(row - 1, column - 1))
            list.add(getCell(row - 1, column - 1));
        if (!isInBounds(row - 1, column))
            list.add(getCell(row - 1, column));
        if (!isInBounds(row - 1, column + 1))
            list.add(getCell(row - 1, column + 1));
        if (!isInBounds(row, column - 1))
            list.add(getCell(row, column - 1));
        if (!isInBounds(row, column + 1))
            list.add(getCell(row, column + 1));
        if (!isInBounds(row + 1, column - 1))
            list.add(getCell(row + 1, column - 1));
        if (!isInBounds(row + 1, column))
            list.add(getCell(row + 1, column));
        if (!isInBounds(row + 1, column + 1))
            list.add(getCell(row + 1, column + 1));
        return list;
    }
}
