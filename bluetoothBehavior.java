import lejos.robotics.subsumption.Behavior;
import lejos.hardware.Bluetooth;
import lejos.hardware.lcd.LCD;
import lejos.hardware.Button;
import lejos.remote.nxt.BTConnection;
import lejos.remote.nxt.BTConnector;
import lejos.remote.nxt.NXTCommConnector;
import lejos.remote.nxt.NXTConnection;

public class BluetoothBehavior implements Behavior {
    private volatile boolean suppressed = false;
    private boolean messageReceived = false;

  
    public boolean takeControl() {
        return messageReceived;
    }

   
    public void action() {
        suppressed = false;
        LCD.drawString("Processing BT Message", 0, 0);
        messageReceived = false;
        while (!suppressed) {
            Thread.yield();
        }
        LCD.clear();
    }

    public void suppress() {
        suppressed = true;
    }
    public void listenForMessages() {
        NXTCommConnector connector = Bluetooth.getNXTCommConnector();
        LCD.drawString("Waiting for BT...", 0, 0);
        NXTConnection connection = connector.waitForConnection(0, NXTConnection.RAW);
        if (connection != null) {
            LCD.drawString("BT Connected", 0, 1);
            messageReceived = true;
        }
    }
}
