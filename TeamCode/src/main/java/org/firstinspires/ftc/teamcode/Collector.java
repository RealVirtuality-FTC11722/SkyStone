package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


/**
 * Created by Lyesome on 2018-01-13.
 * This Class contains all the methods from controlling the drive system using mecanum wheels
 */

public class Collector {
    public DcMotor SpinnerL = null;
    public DcMotor SpinnerR = null;
    public Servo Joint = null;
    public CRServo pushInServo = null;
    public boolean SIMPLE_MODE = false;
    public boolean ARM_DOWN = false;
    public boolean ARM_UP = true;

    double PULL_IN = 1.0;
    double SPIT_OUT = -0.3;
    double DOWN = 0.62;
    double UP = 0;

//please send help i am tired

    public Collector(){ //constructor
    }

    public void init(HardwareMap myHWMap){

        //Initialize wheel motors
        SpinnerL  = myHWMap.get(DcMotor.class, "motorSpinnerL");
        SpinnerR  = myHWMap.get(DcMotor.class, "motorSpinnerR");
        pushInServo = myHWMap.get(CRServo.class, "servoPushIn");

        // eg: Set the drive motor directions:
        // "Reverse" the motor that runs backwards when connected directly to the battery
        SpinnerL.setDirection(DcMotorSimple.Direction.REVERSE);
        SpinnerR.setDirection(DcMotorSimple.Direction.FORWARD);
        pushInServo.setDirection(CRServo.Direction.REVERSE);
        SpinnerL.setPower(0);
        SpinnerR.setPower(0);
        pushInServo.setPower(0);

        Joint = myHWMap.servo.get("servoJoint");
        Joint.setDirection(Servo.Direction.FORWARD);
    }

    public void SpinnerControl(boolean inButton, boolean outButton, boolean stopButton){
        if (inButton){
            SpinnerL.setPower(PULL_IN);
            SpinnerR.setPower(PULL_IN);
            if (!SIMPLE_MODE) {
                pushInServo.setPower(0.75);
            } else {
                pushInServo.setPower(0.0);
            }
        }

        if (outButton){
            SpinnerL.setPower(SPIT_OUT);
            SpinnerR.setPower(SPIT_OUT);
            if (!SIMPLE_MODE) {
                pushInServo.setPower(-0.75);
            } else {
                pushInServo.setPower(0.0);
            }
        }

        if (stopButton){
            SpinnerL.setPower(0);
            SpinnerR.setPower(0);
            pushInServo.setPower(0);
        }
    }
    public void ChangeArmState() {
        if (ARM_DOWN) {
            RaiseArm();
        }
        if (ARM_UP) {
            DropArm();
        }
    }

    public void DropArm() {
        Joint.setPosition(DOWN);
        ARM_DOWN = true;
        ARM_UP = false;
    }

    public void RaiseArm() {
        Joint.setPosition(UP);
        ARM_UP = true;
        ARM_DOWN = false;
    }
}
