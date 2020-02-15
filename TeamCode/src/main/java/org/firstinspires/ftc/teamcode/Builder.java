package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
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
    public DcMotor liftUpDown = null;
    public DcMotor liftInOut = null;
    public Servo liftClamp = null;
    public CRServo plateServo = null;
    public Servo kickOutServo = null;
    public boolean onState = false;
    //public Servo ShifterServo = null;
    public Servo FoundationGrabberA;
    public Servo FoundationGrabberB;

    double GRAB_POSITION = 0.82;
    double RELEASE_POSITION = 0.5;
    double CLAMP_OPEN = 0;
    double CLAMP_CLOSED = 1.0;
    double LIFTER_SPEED_MAX = 0.8;
    int IN_POSITION = 0;
    int OUT_POSITION = 2400;


    public Builder(){ //constructor
    }

    public void init(HardwareMap myHWMap){

        //Initialize wheel motors
        liftUpDown = myHWMap.get(DcMotor.class, "motorLiftUpDown");
        liftInOut = myHWMap.get(DcMotor.class, "motorLiftInOut");
        liftClamp = myHWMap.get(Servo.class, "servoLiftClamp");
        plateServo  = myHWMap.get(CRServo.class, "servoPlate"); //was "servoPaddle"
        kickOutServo = myHWMap.get(Servo.class, "servoKickOut");
        FoundationGrabberA = myHWMap.get(Servo.class, "FoundationGrabberA");
        FoundationGrabberB = myHWMap.get(Servo.class, "FoundationGrabberB");


        // eg: Set the drive motor directions:
        // "Reverse" the motor that runs backwards when connected directly to the battery

        liftUpDown.setDirection(DcMotorSimple.Direction.REVERSE);
        liftInOut.setDirection(DcMotorSimple.Direction.REVERSE);
        liftClamp.setDirection(Servo.Direction.FORWARD);
        plateServo.setDirection(CRServo.Direction.REVERSE);
        FoundationGrabberA.setDirection(Servo.Direction.FORWARD);
        FoundationGrabberB.setDirection(Servo.Direction.REVERSE);
        kickOutServo.setDirection(Servo.Direction.REVERSE);
        liftInOut.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftInOut.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        plateServo.setPower(0);
    }


    public void LifterControl(double liftStick, boolean openJimboClampo, boolean closedJimboClampo) {
        liftUpDown.setPower(Range.clip(liftStick, -LIFTER_SPEED_MAX, LIFTER_SPEED_MAX));

        if (closedJimboClampo) {
            liftClamp.setPosition(CLAMP_CLOSED);
        }

        if (openJimboClampo) {
            liftClamp.setPosition(CLAMP_OPEN);
        }
    }

    public void LifterExpand(String state, OpMode op) {
        if (state == "in") {
            liftInOut.setTargetPosition(IN_POSITION);
        } else if (state == "out") {
            liftInOut.setTargetPosition(OUT_POSITION);
        }
        liftInOut.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftInOut.setPower(0.5);
    }

    public void MovePlate(double plateStick) {
        plateServo.setPower(plateStick*0.75);
    }

    public void GrabPlatform(boolean GrabButton) {
        if (GrabButton) {
            FoundationGrabberB.setPosition(GRAB_POSITION);
            FoundationGrabberA.setPosition(GRAB_POSITION);
        } else {
            FoundationGrabberA.setPosition(RELEASE_POSITION);
            FoundationGrabberB.setPosition(RELEASE_POSITION);
        }
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