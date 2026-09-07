import Flammability.FlammableStrategy;
import Flammability.NonBurnable;

public class River extends Terrain {
    FlammableStrategy inflammable = new NonBurnable();
    @Override
    public void update() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public Terrain clone() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'clone'");
    }

    public double getCoolingStrength() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCoolingStrength'");
    }
    
}
