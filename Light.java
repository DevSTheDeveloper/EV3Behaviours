package devPackage;

import lejos.robotics.subsumption.Behavior;
import lejos.robotics.navigation.MovePilot;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.port.SensorPort;
import lejos.robotics.SampleProvider;

public class Light implements Behavior {
	private MovePilot pilot;
	private EV3ColorSensor lightSensor;
	private SampleProvider light;
	private boolean suppressed = false;
	private float[] sample;
	private float lightThreshold;
	private double faster;
	
	public Light(MovePilot pilot, EV3ColorSensor lightSensor, float lightThreshold, double faster) {
		this.pilot = pilot;
		this.lightSensor = lightSensor;
		this.light = lightSensor.getRedMode();
		this.sample = new float[light.sampleSize()];
		this.lightThreshold = lightThreshold;
		this.faster = faster;
		
	}
	
	public boolean takeControl() {
		light.fetchSample(sample, 0);
		return pilot.getLinearSpeed() < faster && sample[0] > lightThreshold;
		
	}
	
	public void action() {
		suppressed = false;
		pilot.setLinearSpeed(faster);
		while (!suppressed) {
			Thread.yield();
		}
	}
	
	public void suppress() {
		suppressed = true;
	}
	
}
