public class WeatherManager {
    public boolean isActive(Weather w){
        if(w.getDuration()<0){
            return false;
        }
        return true;
    }
}
