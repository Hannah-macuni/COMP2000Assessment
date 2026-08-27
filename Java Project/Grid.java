import java.util.ArrayList;
public class Grid<T> {
    ArrayList<ArrayList<T>> grid;
    static int dx;
    static int dy;

    public Grid(){
        dx = 10;
        dy = 10;
        grid = new ArrayList<>(dx);
        for (int i = 0; i < dx; i++) {
            ArrayList<T> reference = grid.get(i);
            reference = new ArrayList<T>();
        }
    }
}
