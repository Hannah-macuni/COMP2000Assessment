public class Fire {

    private int intensity;

    public Fire(){
        this.intensity = 0;
    }

    public int getIntensity() {
        return intensity;
    }

    public void setIntensity(int amount){
        intensity = intensity + amount;
    }
    
    public Boolean isExtinguished(){
        if(intensity <= 0){
            return true;
        }
        else{
            return false;
        }
    }
}