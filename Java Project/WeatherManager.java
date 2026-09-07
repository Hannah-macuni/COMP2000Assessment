import java.util.ArrayList;
import java.util.Iterator;

public class WeatherManager {
    ArrayList<Weather> weatherList = new ArrayList<>();
    
    public boolean isActive(Weather w) {
        if (w.getDuration() <= 0) {
            return false;
        }

        return true;
    }

    public void createWeather(String weather) {
        Weather w = null;

        switch (weather) {
            case "Rain":
                w = new Rain(8, 20, null);
                break;
            case "Heatwave":
                w = new Heatwave(2, 20, null);
                break;
            case "Lightning":
                w = new Lightning(10, 1, null, 3);
                break;
            default:
                break;
        }

        weatherList.add(w);
    }

    public void createWeather(String weather, String selectedDirection) {
        Weather w = null;
        int direction = 0;

        switch (selectedDirection) {
            case "East":
                direction = 1;
                break;
            case "South":
                direction = 2;
                break;
            case "West":
                direction = 3;
                break;
            default:
                break;
        }

        w = new Wind(8, 20, null, direction);
        weatherList.add(w);
    }

    public void advanceDecrement(Weather w) {
        w.decrementDuration();
    }

    public void update(ForestFireSimulation simulation) {
        Iterator<Weather> it = weatherList.iterator();

        while (it.hasNext()) {
            Weather w = it.next();
            advanceDecrement(w);

            if (!isActive(w)) {
                it.remove();
            } else {
                w.affectSimulation(simulation);
            }
        }
    }
}
