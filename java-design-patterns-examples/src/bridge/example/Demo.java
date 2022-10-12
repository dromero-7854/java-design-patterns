package bridge.example;

import bridge.example.devices.Device;
import bridge.example.devices.Radio;
import bridge.example.devices.TV;
import bridge.example.remotes.AdvancedRemote;
import bridge.example.remotes.BasicRemote;

public class Demo {

	public static void main(String[] args) {
        testDevice(new TV());
        testDevice(new Radio());
    }

    public static void testDevice(Device device) {
        System.out.println("Tests with basic remote.");
        BasicRemote basicRemote = new BasicRemote(device);
        basicRemote.power();
        device.printStatus();

        System.out.println("Tests with advanced remote.");
        AdvancedRemote advancedRemote = new AdvancedRemote(device);
        advancedRemote.power();
        advancedRemote.mute();
        device.printStatus();
    }
	
}
