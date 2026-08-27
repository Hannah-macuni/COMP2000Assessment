public abstract class Terrain {
    public abstract void update();
    public void applyWeather(Weather w){
        w.affectTerrain(this);
    };
    public abstract Terrain clone();
}
 