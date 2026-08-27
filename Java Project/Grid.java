import java.util.ArrayList;
public class Grid<T> {
    ArrayList<ArrayList<T>> grid;
    int dx;
    int dy;

    public Grid(){
        //Returns a Grid of type T with initial dimensions 10x10
        dx = 10;
        dy = 10;
        grid = new ArrayList<>(dx);
        for (int i = 0; i < dx; i++) {
            ArrayList<T> reference = grid.get(i);
            reference = new ArrayList<T>(dy);
        }
    }

    public Grid(int dx, int dy){
        //Returns a Grid of type T with dimensions dx x dy
        this.dx = dx;
        this.dy = dy;
        grid = new ArrayList<>(dx);
        for (int i = 0; i < dx; i++) {
            ArrayList<T> reference = grid.get(i);
            reference = new ArrayList<T>(dy);
        }
    }

    public Grid(Grid<T> g){
        //Returns a Grid of type T with dimensions copied from another Grid.
        //NB: This method only copies the size of the Grid, not any elements in it. -JA
        this.dx = g.dx;
        this.dy = g.dy;
        grid = new ArrayList<>();
        for (int i = 0; i < dx; i++) {
            ArrayList<T> reference = grid.get(i);
            reference = new ArrayList<T>(dy);
        }
    }
}
