package devPackage;

import lejos.hardware.Button;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class EmergencyStopBehavior implements Behavior {
    private boolean suppressed = false;  
    private MovePilot pilot;

    public EmergencyStopBehavior(MovePilot pilot) {
        this.pilot = pilot;
    }

    public boolean takeControl() {
        return Button.ESCAPE.isDown();
    }

    public void action() {
        EV3TouchSensor touchSensor = new EV3TouchSensor(SensorPort.S2);
        float[] touchSample = new float[1];

        SampleProvider touchProvider = touchSensor.getTouchMode();

        suppressed = false;
        while (!suppressed) {
            touchProvider.fetchSample(touchSample, 0);
            if (touchSample[0] >= 1)
                pilot.stop();
        }
    }

    public void suppress() {
        suppressed = true;  
    }
}
