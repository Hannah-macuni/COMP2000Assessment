// Something to do with one cell of a Grid, given its position and its value.
// It has a single method, so callers can pass a lambda: (row, column, value) -> ...
@FunctionalInterface
public interface CellAction<T> {
    void apply(int row, int column, T value);
}
