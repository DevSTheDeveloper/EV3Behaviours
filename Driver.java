package devPackage;

import lejos.hardware.port.SensorPort;
import lejos.robotics.chassis.*;
import lejos.robotics.subsumption.Arbitrator;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.navigation.MovePilot;
import lejos.hardware.port.MotorPort;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.robotics.subsumption.Behavior;

public class Driver {
    final static float ANGULAR_SPEED = 100; //from previous document
    final static float AXLE_LENGTH = 50;
    final static float WHEEL_DIAMETER = 51;
    final static float LINEAR_SPEED = 120;
    
    public static void main(String[] args) {
        MovePilot pilot = createMovePilot();
        
        EV3UltrasonicSensor sensor = new EV3UltrasonicSensor(SensorPort.S3); 
        sensor.enable(); 
        
        EV3ColorSensor lightSensor = new EV3ColorSensor(SensorPort.S1);
        Behavior emergencyStop = new EmergencyStopBehavior(pilot); 
        Behavior batteryLevel = new BattLevelBehavior(); 
        Behavior darkBehavior = new Dark(pilot, lightSensor, 0.2f, 10.0);
        Behavior lightBehavior = new Light(pilot, lightSensor, 0.5f, 50.0);
        Behavior backupBehavior = new BackupBehaviour(pilot);
        
        Behavior trundleBehavior = new TrundleBehavior(pilot); 
        Behavior bluetoothBehavior = new BluetoothBehavior();  
        Behavior calibrateBehavior = new CalibrateBehavior();  

        Behavior[] behaviors = {emergencyStop, batteryLevel, darkBehavior, lightBehavior, backupBehavior, trundleBehavior, bluetoothBehavior, calibrateBehavior};   
        
        Arbitrator arbitrator = new Arbitrator(behaviors);
        arbitrator.go();
    }
    
    private static MovePilot createMovePilot() {
        EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.B);
        EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.C);
        Wheel wheel1 = WheeledChassis.modelWheel(leftMotor,  WHEEL_DIAMETER).offset(-6);
        Wheel wheel2 = WheeledChassis.modelWheel(rightMotor, WHEEL_DIAMETER).offset(6); 
        return new MovePilot(new WheeledChassis(new Wheel[] {wheel1, wheel2}, WheeledChassis.TYPE_DIFFERENTIAL));
    }
}
