public class Fire {

    private int intensity;
  
    public Fire(int i){
        intensity = i;
    }

    public int getIntensity() {
        return intensity;
    }

    public void setIntensity(int amount){
        intensity += amount;
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