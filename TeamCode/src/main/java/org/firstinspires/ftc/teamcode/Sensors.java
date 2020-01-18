package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.util.Range;
import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;




/**
 * Created by Lyesome on 2018-01-13.
 * This Class contains all the methods from controlling the building system using servos
 */

public class Sensors {
    ColorSensor sensorColor;
    DistanceSensor sensorDistance;
    ModernRoboticsI2cRangeSensor bridgeSensor;
    ModernRoboticsI2cRangeSensor blockSensor;

    // hsvValues is an array that will hold the hue, saturation, and value information.
    float hsvValues[] = {0F, 0F, 0F};

    // values is a reference to the hsvValues array.
    final float values[] = hsvValues;

    // sometimes it helps to multiply the raw RGB values with a scale factor
    // to amplify/attentuate the measured values.
    final double SCALE_FACTOR = 255;



    public Sensors(){ //constructor
    }

    public void init(HardwareMap myHWMap){

        sensorColor = myHWMap.get(ColorSensor.class, "sensorColor");

        // get a reference to the distance sensor that shares the same name.
        sensorDistance = myHWMap.get(DistanceSensor.class, "sensorColor");

        bridgeSensor = myHWMap.get(ModernRoboticsI2cRangeSensor.class, "sensorBridge");
        //blockSensor = myHWMap.get(ModernRoboticsI2cRangeSensor.class, "sensorBlock");
        //blockSensor.setI2cAddress(I2cAddr.create8bit(0x3a));

        // get a reference to the RelativeLayout so we can change the background
        // color of the Robot Controller app to match the hue detected by the RGB sensor.
        int relativeLayoutId = myHWMap.appContext.getResources().getIdentifier("RelativeLayout", "id", myHWMap.appContext.getPackageName());
        final View relativeLayout = ((Activity) myHWMap.appContext).findViewById(relativeLayoutId);
    }

    public int DetectStone(LinearOpMode op){
        // 0 = no stone
        // 1 = yellow stone
        // 2 = Skystone
        return 0;
    }

    public boolean SkystoneDetected(){
        Color.RGBToHSV((int) (sensorColor.red() * SCALE_FACTOR),
                (int) (sensorColor.green() * SCALE_FACTOR),
                (int) (sensorColor.blue() * SCALE_FACTOR),
                hsvValues);
        if (hsvValues[0] < 55 && hsvValues[0] > 39) {
            return true;
        } else {
            return false;
        }
    }

}