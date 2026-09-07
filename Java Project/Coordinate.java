public class Coordinate {
    //Helper class that contains two ints referring to a position in a two-dimensional grid.
    int x, y;
    public Coordinate(){
        x = 0; y = 0;
    }

    public Coordinate(int X, int Y){
        x = X; y = Y;
    }

    public Coordinate(Coordinate c){
        this.x = c.x;
        this.y = c.y;
    }
}
