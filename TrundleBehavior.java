package devPackage;
import lejos.robotics.subsumption.Behavior;
import lejos.robotics.navigation.MovePilot;

public class TrundleBehavior implements Behavior {
    private MovePilot pilot;
    private boolean suppressed = false;

    public Trundle(MovePilot pilot) {
        this.pilot = pilot;
    }

   
    public boolean takeControl() {
        // This behavior always wants to take control
        return true;
    }

  
    public void action() {
        suppressed = false;
        pilot.forward();
        while (!suppressed) {
            Thread.yield(); // Allow other threads to run
        }
        pilot.stop();
    }

 
    public void suppress() {
        suppressed = true;
    }
}
