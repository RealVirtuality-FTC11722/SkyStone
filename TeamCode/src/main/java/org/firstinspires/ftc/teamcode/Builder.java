package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;


/**
 * Created by Lyesome on 2018-01-13.
 * This Class contains all the methods from controlling the building system
 */

public class Builder {
    public DcMotor motorLifter = null;
    public CRServo plateServo = null;
    public Servo kickOutServo = null;
    public boolean onState = false;
    public Servo liftClamp = null;
    //public Servo ShifterServo = null;
    public Servo FoundationGrabberA;
    public Servo FoundationGrabberB;

    double GRAB = 0.6;
    double RELEASE = 0.3;
    double CLAMP_OPEN = 0;
    double CLAMP_CLOSED = 0.5;
    double LIFTER_SPEED_MAX = 0.8;


    public Builder(){ //constructor
    }

    public void init(HardwareMap myHWMap){

        //Initialize wheel motors
        motorLifter = myHWMap.get(DcMotor.class, "motorLifter");
        plateServo  = myHWMap.get(CRServo.class, "servoPlate"); //was "servoPaddle"
        liftClamp = myHWMap.get(Servo.class, "servoLiftClamp");
        kickOutServo = myHWMap.get(Servo.class, "servoKickOut");
        FoundationGrabberA = myHWMap.get(Servo.class, "FoundationGrabberA");
        FoundationGrabberB = myHWMap.get(Servo.class, "FoundationGrabberB");


        // eg: Set the drive motor directions:
        // "Reverse" the motor that runs backwards when connected directly to the battery

        motorLifter.setDirection(DcMotorSimple.Direction.REVERSE);
        plateServo.setDirection(CRServo.Direction.REVERSE);
        FoundationGrabberA.setDirection(Servo.Direction.FORWARD);
        FoundationGrabberB.setDirection(Servo.Direction.FORWARD);
        plateServo.setPower(0);
    }


    public void BuilderControl(LinearOpMode op, double joeStick, double liftStick, double clampTrigger) {
        plateServo.setPower(joeStick);
        motorLifter.setPower(Range.clip(liftStick, -LIFTER_SPEED_MAX, LIFTER_SPEED_MAX));
        liftClamp.setPosition(Range.clip(clampTrigger, CLAMP_OPEN, CLAMP_CLOSED));
    }

    public void BuilderControl (LinearOpMode op, boolean ShifterP, boolean ShifterN) {
        if (ShifterP) {
            //ShifterServo.setPosition(+0.799);
        }

        if (ShifterN) {
            //ShifterServo.setPosition(-0.1);


        }

    }
    }