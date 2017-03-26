package ru.doktorov.smarthome.controller;

import com.pi4j.io.gpio.*;
import java.util.BitSet;

import ru.doktorov.smarthome.rf.RCSwitch;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.doktorov.smarthome.rf.DHT11;

@RestController
public class LedController {

    private static GpioPinDigitalOutput mPin03;
    private static GpioPinDigitalOutput mPin01;
    private static GpioPinDigitalOutput mPin07;

    @RequestMapping("/")
    public String greeting() {
        return "Hello world!";
    }

    @RequestMapping("/light")
    public String light() {
        if (mPin01 == null) {
            GpioController gpio = GpioFactory.getInstance();
            mPin01 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "MyLED", PinState.LOW);
        }

        mPin01.toggle();

        return "OK";
    }
    
    @RequestMapping("/switchOn")
    public String switchOn() {
        GpioController gpio = GpioFactory.getInstance();       

        if (mPin07 == null) {
            mPin07 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_07, "MyLED", PinState.LOW);
        }
        
        BitSet address = RCSwitch.getSwitchGroupAddress("11111");
        
        RCSwitch transmitter = new RCSwitch(mPin07);
        transmitter.switchOn(address, 3);
        
        gpio.shutdown();

        return "switchOn";
    }
    
    @RequestMapping("/switchOff")
    public String switchOff() {       
        GpioController gpio = GpioFactory.getInstance();       

        if (mPin07 == null) {
            mPin07 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_07, "MyLED", PinState.LOW);
        }
        
        BitSet address = RCSwitch.getSwitchGroupAddress("11111");
        
        RCSwitch transmitter = new RCSwitch(mPin07);
        transmitter.switchOff(address, 3);
        
        gpio.shutdown();

        return "switchOff";
    }
    
    @RequestMapping("/dht11")
    public String dht11() {       
        GpioController gpio = GpioFactory.getInstance();       

        if (mPin03 == null) {
            mPin03 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, "MyLED", PinState.LOW);
        }
        
        DHT11 transmitter = new DHT11(mPin03);

        
        gpio.shutdown();

        return "dht11";
    }
    
    @RequestMapping("/connectToRelay")
    public String connectToRelay() throws InterruptedException {
        GpioController gpio = GpioFactory.getInstance();       

        if (mPin07 == null) {
            mPin07 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_07, "MyLED", PinState.LOW);
        }
        
        BitSet address = RCSwitch.getSwitchGroupAddress("00001");
        
        RCSwitch transmitter = new RCSwitch(mPin07);
        transmitter.switchOn(address, 1);
        
        Thread.sleep(2000);
        
        transmitter.switchOff(address, 1);
        
        gpio.shutdown();

        return "connectToRelay";
    }

    @RequestMapping("/relayOn")
    public String relayOn() {
        GpioController gpio = GpioFactory.getInstance();

        if (mPin07 == null) {
            mPin07 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_07, "MyLED", PinState.LOW);
        }

        BitSet address = RCSwitch.getSwitchGroupAddress("00001");

        RCSwitch transmitter = new RCSwitch(mPin07);
        transmitter.switchOn(address, 1);
        gpio.shutdown();

        return "relayOn";
    }

    @RequestMapping("/relayOff")
    public String relayOff() {
        GpioController gpio = GpioFactory.getInstance();

        if (mPin07 == null) {
            mPin07 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_07, "MyLED", PinState.LOW);
        }

        BitSet address = RCSwitch.getSwitchGroupAddress("00001");

        RCSwitch transmitter = new RCSwitch(mPin07);
        transmitter.switchOff(address, 1);
        gpio.shutdown();

        return "relayOff";
    }
}