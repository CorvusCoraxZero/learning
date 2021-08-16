package com.zero.service;

import com.zero.tings.Device;
import com.zero.tings.Thermometer;

public class TingService {

    public Device getDevice(int deviceId, int identifier){
        Thermometer thermometer = new Thermometer(25);
        thermometer.setDeviceId(deviceId);
        thermometer.setIdentifier(identifier);
        return thermometer;
    }
}
