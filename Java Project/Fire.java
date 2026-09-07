public class Fire {
    int intensity;
    public Fire(){
        intensity = 0;
    }

    public Fire(int i){
        intensity = i;
    }

    public Fire(Fire f){
        intensity = f.getIntensity();
    }
    
    public int getIntensity() {
        return intensity;
    }
    //TODO implement this class
}
