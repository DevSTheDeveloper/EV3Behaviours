package devPackage;

import lejos.hardware.Battery;
import lejos.hardware.lcd.LCD;
import lejos.hardware.Sound;
import lejos.robotics.subsumption.Behavior;

public class BattLevelBehavior implements Behavior {
    private boolean suppressed = false;  
    private static final float LOW_BATTERY_THRESHOLD = 6.5f;  

    public boolean takeControl() {
        return Battery.getVoltage() < LOW_BATTERY_THRESHOLD; //return true, initiate take control behaviour 
    }

    public void action() {
        suppressed = false;
        while (suppressed == false) {
            LCD.clear();
            LCD.drawString("WARNING: Low Battery", 0, 4); //low batt warning
            Sound.beep(); 

            try {
                Thread.sleep(800);  //wait for 0.8 seconds before every voltage check
            } 
            
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    
    public void suppress() {
        suppressed = true;  //higher priority process takes over with this call
    }
}
