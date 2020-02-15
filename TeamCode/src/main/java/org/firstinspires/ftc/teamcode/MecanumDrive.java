package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


/**
 * Created by Lyesome on 2018-01-13.
 * This Class contains all the methods from controlling the drive system using mecanum wheels
 */

public class MecanumDrive {
    public DcMotor motorFL = null;
    public DcMotor motorFR = null;
    public DcMotor motorBL = null;
    public DcMotor motorBR = null;

    public double JimtheMotorWeightDistrobutionHelpingNumber = 0.88;
    public static double STEERING = 0.004;
    public static double Turn_Power = 0.15;
    double DRIVE_POWER_MAX_LOW = 0.4; //Minimum drive power without throttle
    double TURN_POWER_MIN = 0.17; //Minumun turn power
    static final double     HEADING_THRESHOLD       = 1 ;      // As tight as we can make it with an integer gyro
    static final double     P_TURN_COEFF            = 0.1;     // Larger is more responsive, but also less stable
    static final double     P_DRIVE_COEFF           = 0.15;     // Larger is more responsive, but also less stable
    // IMU sensor object (gyro)
    BNO055IMU imu;

    public MecanumDrive(){ //constructor
    }

    public void initAuto(HardwareMap myHWMap){

        initMotors(myHWMap);

        DriveMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        DriveMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Set up the parameters with which we will use our IMU. Note that integration
        // algorithm here just reports accelerations to the logcat log; it doesn't actually
        // provide positional information.
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        // Retrieve and initialize the IMU. We expect the IMU to be attached to an I2C port
        // on a Core Device Interface Module, configured to be a sensor of type "AdaFruit IMU",
        // and named "imu".
        imu = myHWMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
    }

    public void initMotors(HardwareMap myHWMap) {

        //Initialize wheel motors
        motorFL = myHWMap.get(DcMotor.class, "motorFL");
        motorFR = myHWMap.get(DcMotor.class, "motorFR");
        motorBL = myHWMap.get(DcMotor.class, "motorBL");
        motorBR = myHWMap.get(DcMotor.class, "motorBR");

        // eg: Set the drive motor directions:
        // "Reverse" the motor that runs backwards when connected directly to the battery
        motorFL.setDirection(DcMotor.Direction.REVERSE); // Set to REVERSE if using AndyMark motors
        motorFR.setDirection(DcMotor.Direction.FORWARD);// Set to FORWARD if using AndyMark motors
        motorBL.setDirection(DcMotor.Direction.REVERSE); // Set to REVERSE if using AndyMark motors
        motorBR.setDirection(DcMotor.Direction.FORWARD);// Set to FORWARD if using AndyMark motors

    }

    public void initGyro(HardwareMap myHWMap) {
        // Set up the parameters with which we will use our IMU. Note that integration
        // algorithm here just reports accelerations to the logcat log; it doesn't actually
        // provide positional information.
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        // Retrieve and initialize the IMU. We expect the IMU to be attached to an I2C port
        // on a Core Device Interface Module, configured to be a sensor of type "AdaFruit IMU",
        // and named "imu".
        imu = myHWMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
    }

    public void initTele(HardwareMap myHWMap) {
        initMotors(myHWMap);
        //help me

        //Initialize wheel motors

    }

    public void DriveMode(DcMotor.RunMode JoeMode) {
        motorFL.setMode(JoeMode);
        motorFR.setMode(JoeMode);
        motorBL.setMode(JoeMode);
        motorBR.setMode(JoeMode);
    }

    /**Method for manual control of drive system
     yStick controls forward and backward motion
     xStick controls lateral motion (strafe)
     turnStick control rotation (turn)
     Trigger controls the throttle (speed) */
    public void DriveControl(double yStick, double xStick, double turnStick, double trigger){
        DriveMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        double r = Math.hypot(xStick, yStick);
        double robotAngle = Math.atan2(-yStick, xStick) - Math.PI / 4;
        //Set minimum throttle value so the trigger does not need to be pressed to drive
        double throttle = 1 - trigger/2;
        //double throttle = trigger * (1-DRIVE_POWER_MAX_LOW) + DRIVE_POWER_MAX_LOW;
        //Cube the value of turnStick so there's more control over low turn speeds
        double rightX = turnStick; //Math.pow(turnStick, 3);
        final double v1 = r * Math.cos(robotAngle) + rightX;
        final double v2 = r * Math.sin(robotAngle) - rightX;
        final double v3 = r * Math.sin(robotAngle) + rightX;
        final double v4 = r * Math.cos(robotAngle) - rightX;

        motorFL.setPower(v1*throttle);
        motorFR.setPower(v2*throttle);
        motorBL.setPower(v3*throttle * JimtheMotorWeightDistrobutionHelpingNumber);
        motorBR.setPower(v4*throttle * JimtheMotorWeightDistrobutionHelpingNumber);
    }

    //Method to stop all power to the wheel motors
    public void StopWheels() {
        motorFR.setPower(0);
        motorBR.setPower(0);
        motorFL.setPower(0);
        motorBL.setPower(0);
        try { //Pause for a moment to let motion come to a stop
            Thread.sleep(500);
        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
    public void DriveForward(double power) {
        DriveMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBL.setPower(power);
        motorBR.setPower(power);
        motorFL.setPower(power);
        motorFR.setPower(power);
    }

    public void DriveLeft(double power) {
        DriveMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBL.setPower(power);
        motorBR.setPower(-power);
        motorFL.setPower(-power);
        motorFR.setPower(power);
    }
    public void DriveLeftWithGyro(double power, LinearOpMode op, double time) {
        DriveMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        ElapsedTime runtime = new ElapsedTime();
        runtime.reset();
        DriveLeft( power / 2);
        op.sleep(500);
        DriveLeft( power / 1.5);
        op.sleep(500);
        DriveLeft( power);
        while (op.opModeIsActive() && runtime.time() < time) {
            op.telemetry.addData("Time: ", runtime.time());
            op.telemetry.update();
            KeepStraight();
        }
    }
    public void DriveRightWithGyro(double power, LinearOpMode op, double time) {
        DriveMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        ElapsedTime runtime = new ElapsedTime();
        runtime.reset();
        DriveRight( power / 2);
        op.sleep(500);
        DriveRight( power / 1.5);
        op.sleep(500);
        DriveRight(power);
        while (op.opModeIsActive() && runtime.time() < time) {
            op.telemetry.addData("Time: ", runtime.time());
            op.telemetry.update();
            KeepStraight();
        }
    }

    public void KeepStraight() {
        if (imu.getAngularOrientation().firstAngle < 0){
            SteerLeft();
        }
        if (imu.getAngularOrientation().firstAngle > 0) {
            SteerRight();
        }
    }

    public void DriveRight(double power) {
        DriveMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBL.setPower(-power);
        motorBR.setPower(power);
        motorFL.setPower(power);
        motorFR.setPower(-power);
    }

    public void DriveBackwards(double power) {
        DriveMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBL.setPower(-power);
        motorBR.setPower(-power);
        motorFL.setPower(-power);
        motorFR.setPower(-power);
    }

    public void SteerRight() {
        motorFL.setPower(Range.clip(motorFL.getPower() + STEERING, -1.0, 1.0) );
        motorBL.setPower(Range.clip(motorBL.getPower() + STEERING, -1.0, 1.0) );
        motorFR.setPower(Range.clip(motorFR.getPower() - STEERING, -1.0, 1.0) );
        motorBR.setPower(Range.clip(motorBR.getPower() - STEERING, -1.0, 1.0) );
    }

    public void SteerLeft() {
        motorFL.setPower(Range.clip(motorFL.getPower() - STEERING, -1.0, 1.0) );
        motorBL.setPower(Range.clip(motorBL.getPower() - STEERING, -1.0, 1.0) );
        motorFR.setPower(Range.clip(motorFR.getPower() + STEERING, -1.0, 1.0) );
        motorBR.setPower(Range.clip(motorBR.getPower() + STEERING, -1.0, 1.0) );
    }

    //Method for autonomous driving forward and backwards
    //distance is specified in inches (positive = drive forward, negative = drive backward)
    //timeout value is used to interrupt drive routine if robot gets stuck
    //and cannot reach the specified destination
    //This method returns true of false to indicate if the robot successfully reached the target
    public boolean Drive(LinearOpMode op, double power, double distance, double timeout){
        //Reset encoder values so target position is relative to the position the robot
        //was at when the method was called


        DriveMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        DriveMode(DcMotor.RunMode.RUN_TO_POSITION);

        ElapsedTime drivetime = new ElapsedTime();
        boolean successfulness = false;
        double scaleFactor = 83.33; //Convert inches to encoder values
        double drivePower = power;
        double heading = imu.getAngularOrientation().firstAngle;
        int startPosition = motorBL.getCurrentPosition();
        int endPosition = startPosition + (int)Math.round(distance * scaleFactor);
        motorBL.setTargetPosition(endPosition);
        motorBR.setTargetPosition(endPosition);
        motorBL.setPower(power);
        motorBR.setPower(power);
        motorFR.setPower(Math.signum(distance)*motorBL.getPower());
        motorFL.setPower(Math.signum(distance)*motorBL.getPower());
        drivetime.reset(); //start timeout timer
        while (motorBL.isBusy() && op.opModeIsActive() && drivetime.seconds() < timeout){
            //Reduce drive power as robot nears the target to improve accuracy
            if (Math.abs(motorBL.getCurrentPosition() - endPosition) < 500) {
                //0.05 prevents drivePower reducing so much that wheels won't turn
                //and never reach their target positions
                drivePower = 0.05 + power * Math.abs(motorBL.getCurrentPosition() - endPosition)/500 ;
            }
            motorBL.setPower(drivePower);
            motorBR.setPower(drivePower);
            //Have front wheels copy what back wheels are doing
            //in RUN_TO_POSITION mode, motor power is always indicated as positive so
            //use sign of distance to tell front wheels which direction to turn
            motorFL.setPower(Math.signum(distance)*motorBL.getPower());
            motorFR.setPower(Math.signum(distance)*motorBR.getPower());
            op.telemetry.addData("Start", startPosition);
            op.telemetry.addData("Heading", imu.getAngularOrientation().firstAngle);
            op.telemetry.addData("Go to", endPosition);
            op.telemetry.addData("BR Position", motorBR.getCurrentPosition());
            op.telemetry.addData("BL Position", motorBL.getCurrentPosition());
            op.telemetry.update();
        }
        //if while loop completed before the timeout, then run was successful
        if (drivetime.seconds() < timeout){
            successfulness = true;
        } else {
            successfulness = false;
        }
        StopWheels(); //stop robot's motion before next method is called
        op.telemetry.addData("Start", startPosition);
        op.telemetry.addData("Heading", imu.getAngularOrientation().firstAngle);
        op.telemetry.addData("Go to", endPosition);
        op.telemetry.addData("BR Position", motorBR.getCurrentPosition());
        op.telemetry.addData("BL Position", motorBL.getCurrentPosition());
        op.telemetry.update();

        return successfulness;
    }

    //Method for autonomous driving right
    //Needs more testing, do not use
    public void Right(LinearOpMode op, double power, double distance) {
        //Drive distance in inches. Use "scaleFactor" to convert inches to encoder values.
        double scaleFactor = 116.94;
        double startPosition = motorBL.getCurrentPosition();
        double endPosition = (startPosition - (distance * scaleFactor));
        while (motorBL.getCurrentPosition() > endPosition && op.opModeIsActive()) {
            motorFL.setPower(power);
            motorFR.setPower(-power);
            motorBL.setPower(-power);
            motorBR.setPower(power);
        }
        StopWheels();
    }

    //Method for autonomous driving left
    //Needs more testing, do not use
    public void Left(LinearOpMode op, double power, double distance) {
        //Drive distance in inches. Use "scaleFactor" to convert inches to encoder values.
        double scaleFactor = 116.94;
        double startPosition = motorBL.getCurrentPosition();
        double endPosition = (startPosition + (distance * scaleFactor));
        while (motorBL.getCurrentPosition() < endPosition && op.opModeIsActive()) {
            motorFL.setPower(-power);
            motorFR.setPower(power);
            motorBL.setPower(power);
            motorBR.setPower(-power);
        }
        StopWheels();
    }
    public void TurnToAngle(LinearOpMode op, double targetAngle) {
        double initialAngle = imu.getAngularOrientation().firstAngle;
        double turnPower = 0.4;
        double threshold = .1;
        double initialDiff;
        double difference = initialAngle - targetAngle;
        if (initialAngle < 180) {
            motorFL.setPower(-turnPower);
            motorBL.setPower(-turnPower);
            motorFR.setPower(turnPower);
            motorFR.setPower(turnPower);
        } else {
            motorFL.setPower(turnPower);
            motorBL.setPower(turnPower);
            motorFR.setPower(-turnPower);
            motorFR.setPower(-turnPower);
        }
        while (Math.abs(difference) > threshold && op.opModeIsActive()) {

        }
        StopWheels();
    }
    public double getError(double targetAngle) {

        double robotError;

        // calculate error in -179 to +180 range  (
        robotError = (targetAngle - imu.getAngularOrientation().firstAngle);
        //while (robotError > 180)  robotError -= 360;
        //while (robotError <= -180) robotError += 360;
        return robotError;
    }

    //Method for autonomous turning
    // + is left (CCW), - is right (CW)
    public void Turn(LinearOpMode op, double angle, double timeout){
        double error = 0;
        double steerPower = 0;
        double leftPower;
        double rightPower;
        double initialAngle = imu.getAngularOrientation().firstAngle;
        boolean onTarget = false;
        //motorBL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //motorBR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
       DriveMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        op.telemetry.setAutoClear(true);
        while (op.opModeIsActive() && !onTarget) {
            error = getError(angle);
            op.telemetry.addData("Initial Angle: ", initialAngle);
            op.telemetry.addData("Direction: ", imu.getAngularOrientation().firstAngle);
            op.telemetry.addData("Error: ", getError(angle));
            op.telemetry.addData("Steer Power: ", steerPower);
            op.telemetry.update();
            if (Math.abs(getError(angle)) <= HEADING_THRESHOLD) {
                steerPower = 0.0;
                leftPower = 0.0;
                rightPower = 0.0;
                onTarget = true;
            } else {
                steerPower = getError(angle)/180;
                if (steerPower > -TURN_POWER_MIN && steerPower < 0) {
                    steerPower = -TURN_POWER_MIN;
                }
                if (steerPower > 0 && steerPower < TURN_POWER_MIN) {
                    steerPower = TURN_POWER_MIN;
                }
                rightPower = steerPower;
                leftPower = -rightPower;
            }
            motorFL.setPower(leftPower);
            motorBL.setPower(leftPower);
            motorFR.setPower(rightPower);
            motorBR.setPower(rightPower);
        }
        op.telemetry.setAutoClear(false);
        op.telemetry.addData("Direction: ", imu.getAngularOrientation().firstAngle);
        op.telemetry.addData("Error: ", getError(angle));
        op.telemetry.addData("Steer Power: ", steerPower);
        op.telemetry.update();
    }



}