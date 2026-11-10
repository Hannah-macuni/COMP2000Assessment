@FunctionalInterface
public interface CellAction<T> {
    void apply(int row, int column, T value);
}