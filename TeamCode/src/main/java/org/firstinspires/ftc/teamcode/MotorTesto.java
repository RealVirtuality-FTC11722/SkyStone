package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;


/**
 * Created by Lyesome on 2018-01-13.
 * This file contains all the instructions for controlling the robot in Teleop mode.
 */

@TeleOp(name="Driver Mode - Motor Testo", group="Test")  // @Autonomous(...) is the other common choice
//@Disabled
public class MotorTesto extends LinearOpMode {

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



    // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            skyGary.Drive.motorBL.setPower(1);

            skyGary.Drive.motorBR.setPower(1);

            skyGary.Drive.motorFL.setPower(1);

            skyGary.Drive.motorFR.setPower(1);

            skyGary.Collecta.SpinnerR.setPower(1);

            skyGary.Collecta.SpinnerL.setPower(1);
        }

    }
}
