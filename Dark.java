package devPackage;

import lejos.robotics.subsumption.Behavior;
import lejos.robotics.navigation.MovePilot;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.port.SensorPort;
import lejos.robotics.SampleProvider;

public class Dark implements Behavior {
	private MovePilot pilot;
	private EV3ColorSensor lightSensor;
	private SampleProvider light;
	private boolean suppressed = false;
	private float[] sample;
	private float lightThreshold;
	private double slowed;
	
	public Dark(MovePilot pilot, EV3ColorSensor lightSensor, float lightThreshold, double slowed) {
		this.pilot = pilot;
		this.lightSensor = lightSensor;
		this.light = lightSensor.getRedMode();
		this.sample = new float[light.sampleSize()];
		this.lightThreshold = lightThreshold;
		this.slowed = slowed;
	}
	
	public boolean takeControl() {
		light.fetchSample(sample, 0);
		return pilot.getLinearSpeed() > slowed && sample[0] < lightThreshold;
 	}

	public void action() {
		suppressed = false;
		pilot.setLinearSpeed(slowed);
		while (!suppressed) {
			Thread.yield();
		}
	}
	
	public void suppress() {
		suppressed = true;
	}
	
}
