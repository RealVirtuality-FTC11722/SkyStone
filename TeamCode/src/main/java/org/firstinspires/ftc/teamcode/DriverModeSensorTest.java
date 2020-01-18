package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;


/**
 * Created by Lyesome on 2018-01-13.
 * This file contains all the instructions for controlling the robot in Teleop mode.
 */

@TeleOp(name="Driver Mode - Sensor Test", group="Linear OpMode Test")  // @Autonomous(...) is the other common choice
//@Disabled
public class DriverModeSensorTest extends LinearOpMode {

    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();

    //create a new robot named skyGary
    private BotConfig skyGary = new BotConfig();
    //public CRServo testPaddle = null;



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
        //testPaddle = hardwareMap.get(CRServo.class, "servoPaddle");

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
        //indianaGary.myRelicArm.relicGrab.setPosition(indianaGary.myRelicArm.RELIC_GRAB_OPEN);


        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            if (skyGary.mySensors.SkystoneDetected()){
                telemetry.addData("Skystone: ", "DETECTED");
                skyGary.Collecta.DropArm();
            } else {
                telemetry.addData("Skystone: ", "Not Detected");
            }

            telemetry.update();

        }

    }
}
