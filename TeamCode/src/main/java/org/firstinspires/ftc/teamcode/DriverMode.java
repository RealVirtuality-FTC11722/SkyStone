package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;


/**
 * Created by Lyesome on 2018-01-13.
 * This file contains all the instructions for controlling the robot in Teleop mode.
 */

@TeleOp(name="Driver Mode - Only", group="Linear OpMode")  // @Autonomous(...) is the other common choice
//@Disabled
public class DriverMode extends LinearOpMode {

    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();

    //create a new robot named skyGary
    private BotConfig skyGary = new BotConfig();

    @Override
    public void runOpMode() {

        /* eg: Initialize the hardware variables. Note that the strings used here as parameters
         * to 'get' must correspond to the names assigned during the robot configuration
         * step (using the FTC Robot Controller app on the phone).
         */
        //Provide warning for drivers not to hit play until initializing is complete.
        telemetry.addData("Status", "Initializing. Please Wait...");
        telemetry.update();


        //Use the Teleop initialization method
        skyGary.InitTele(hardwareMap);

        //Set toggle initial states
        boolean togglePressed = false;
        boolean toggleReleased = true;

        boolean ltTogglePressed = false;
        boolean ltToggleReleased = true;

        //Tell drivers that initializing is now complete
        telemetry.setAutoClear(true);
        telemetry.addData("Status", "Initialized");
        telemetry.update();


        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        //skyGary.Builda.LifterExpand("out", this);
        skyGary.Builda.liftInOut.setTargetPosition(2400);
        skyGary.Builda.liftInOut.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        skyGary.Builda.liftInOut.setPower(0.5);
        skyGary.Collecta.RaiseArm();

    // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            telemetry.addData("Status", "Run Time: " + runtime.toString());

            //Pass controls to the drive control method.
            skyGary.Drive.DriveControl(
                    BotControls.DriveYStick(this),
                    BotControls.DriveXStick(this),
                    BotControls.TurnStick(this),
                    BotControls.DriveThrottle(this)
            );

            skyGary.Collecta.SpinnerControl(
                    BotControls.SpinnerOutButton(this),
                    BotControls.SpinnerInButton(this),
                    BotControls.SpinnerStopButton(this)
            );

            if (BotControls.SideArmDownButton(this)) {
                skyGary.Collecta.DropArm();
            }
            if (BotControls.SideArmUpButton(this)) {
                skyGary.Collecta.RaiseArm();
            }
            skyGary.Builda.kickOutServo.setPosition(BotControls.KickOutButton(this));

            if (gamepad2.dpad_left) {
                skyGary.Collecta.SIMPLE_MODE = true;
            }

            if (gamepad2.dpad_right) {
                skyGary.Collecta.SIMPLE_MODE = false;
            }

            skyGary.Builda.GrabPlatform(BotControls.PlatformGrabButton(this));
            //skyGary.Builda.FoundationGrabberB.setPosition(gamepad1.right_trigger);
            //skyGary.Builda.FoundationGrabberA.setPosition(gamepad1.right_trigger);
            telemetry.addData("Plstform Servos: ", gamepad1.right_trigger);

            skyGary.Builda.LifterControl(
                    BotControls.LifterStick(this),
                    BotControls.ClosenoClampButton(this),
                    BotControls.OpenoClampButton(this)

            );

            //skyGary.Builda.MovePlate(BotControls.PlateStick(this) / 2);
            skyGary.Builda.plateServo.setPower(gamepad2.left_stick_y*0.75);
            telemetry.addData("FR Wheel: ", skyGary.Drive.motorFR.getPower());
            telemetry.addData("FL Wheel: ", skyGary.Drive.motorFL.getPower());
            telemetry.addData("BR Wheel: ", skyGary.Drive.motorBR.getPower());
            telemetry.addData("BL Wheel: ", skyGary.Drive.motorBL.getPower());
            telemetry.addData("Joe's Joint: ", skyGary.Collecta.Joint.getPosition());
            if (skyGary.Collecta.ARM_UP) {
                telemetry.addData("Joint State", "Up and onwards");
            }

            if (skyGary.Collecta.ARM_DOWN) {
                telemetry.addData("Joint State", "Down and backwards");
            }
            if (skyGary.Collecta.SpinnerR.getPower() > 0) {
                telemetry.addData("Spinner", "collecting");
            }

            if (skyGary.Collecta.SpinnerR.getPower() < 0) {
                telemetry.addData("Spinner", "barfing");
            }

            if (skyGary.Collecta.SpinnerR.getPower() == 0) {
                telemetry.addData("Spinner", "standing still and doing nothing like a log");
            }

            telemetry.update();

        }

    }
}
