import lejos.robotics.subsumption.Behavior;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.port.SensorPort;
import lejos.robotics.SampleProvider;
import lejos.hardware.lcd.LCD;
import lejos.hardware.Button;

public class CalibrateBehavior implements Behavior {
    private volatile boolean suppressed = false;
    private boolean calibrated = false;
    private float minValue;
    private float maxValue;

    public boolean takeControl() {
        return !calibrated;
    }

    public void action() {
        suppressed = false;
        LCD.clear();
        LCD.drawString("Calibrating...", 0, 0);

        EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S1);
        SampleProvider sp = colorSensor.getRedMode();
        float[] sample = new float[sp.sampleSize()];

        // Calibrate for black
        LCD.drawString("Place on BLACK", 0, 1);
        Button.ENTER.waitForPress();
        sp.fetchSample(sample, 0);
        minValue = sample[0];

        // Calibrate for white
        LCD.drawString("Place on WHITE", 0, 2);
        Button.ENTER.waitForPress();
        sp.fetchSample(sample, 0);
        maxValue = sample[0];

        LCD.clear();
        LCD.drawString("Calibration Done", 0, 0);
        LCD.drawString("Min: " + minValue, 0, 1);
        LCD.drawString("Max: " + maxValue, 0, 2);
        Button.ENTER.waitForPress();

        calibrated = true;
        colorSensor.close();
    }

    public void suppress() {
        suppressed = true;
    }
}
