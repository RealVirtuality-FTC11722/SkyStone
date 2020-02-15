/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.Locale;

/**
 * This file illustrates the concept of driving a path based on time.
 * It uses the common Pushbot hardware class to define the drive on the robot.
 * The code is structured as a LinearOpMode
 *
 * The code assumes that you do NOT have encoders on the wheels,
 *   otherwise you would use: PushbotAutoDriveByEncoder;
 *
 *   The desired path in this example is:
 *   - Drive forward for 3 seconds
 *   - Spin right for 1.3 seconds
 *   - Drive Backwards for 1 Second
 *   - Stop and close the claw.
 *
 *  The code is written in a simple form with no optimizations.
 *  However, there are several ways that this type of sequence could be streamlined,
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@Autonomous(name="AutoBlue - Collect Skystones", group="Blue")
//@Disabled
public class Auto_SkyStoneCollectBlue extends LinearOpMode {

    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();

    //create a new robot named skyGary
    private BotConfig skyGary = new BotConfig();


    @Override
    public void runOpMode() {
        double loopStartTime;
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        //Use the Teleop initialization method vvb
        skyGary.InitAuto(hardwareMap);
        AutoTransitioner.transitionOnStop(this, "Driver Mode - Only");

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Step through each leg of the path, ensuring that the Auto mode has not been stopped along the way

        runtime.reset();
        //while (opModeIsActive()) {

            //Drive Right to Stones
            skyGary.Drive.DriveLeftWithGyro(0.9, this, 1.5);
            skyGary.Drive.DriveLeft(0.455721);
            loopStartTime = runtime.time();
            while (!(skyGary.mySensors.sensorDistance.getDistance(DistanceUnit.CM) < 8.5)
                    && opModeIsActive() && runtime.time() < loopStartTime + 5000) {
                skyGary.Drive.KeepStraight();
                telemetry.addData("Run Time: ", runtime.time());
                telemetry.addData("Loop Time: ", runtime.time() - loopStartTime);
                telemetry.addData("Distance (cm)",
                        String.format(Locale.US, "%.02f", skyGary.mySensors.sensorDistance.getDistance(DistanceUnit.CM)));
                telemetry.update();
            }
            skyGary.Drive.StopWheels();
            //skyGary.Drive.Turn(this, -skyGary.Drive.imu.getAngularOrientation().firstAngle, 5000);
        //Drive Backwards along Stones
            skyGary.Drive.DriveForward(0.2);
            loopStartTime = runtime.time();
            while ((!(skyGary.mySensors.sensorColor.red() < 30) || !(skyGary.mySensors.sensorDistance.getDistance(DistanceUnit.CM) < 12))
                    && opModeIsActive() && runtime.time() < loopStartTime + 8000) {
                skyGary.Drive.KeepStraight();
                telemetry.addData("Run Time: ", runtime.time());
                telemetry.addData("Loop Time: ", runtime.time() - loopStartTime);
                telemetry.addData("Distance (cm)",
                        String.format(Locale.US, "%.02f", skyGary.mySensors.sensorDistance.getDistance(DistanceUnit.CM)));
                telemetry.addData("red: ", skyGary.mySensors.sensorColor.red());
                telemetry.addData("Skystone: ", "NOT DETECTED");
                telemetry.update();
            }
            telemetry.addData("Run Time: ", runtime.time());
            telemetry.addData("Loop Time: ", runtime.time() - loopStartTime);
            telemetry.addData("Distance (cm)",
                    String.format(Locale.US, "%.02f", skyGary.mySensors.sensorDistance.getDistance(DistanceUnit.CM)));
            telemetry.addData("red: ", skyGary.mySensors.sensorColor.red());
            telemetry.update();
            skyGary.Drive.StopWheels();
            //Stop when color sensor detects Skystone
            skyGary.Collecta.DropArm();
            sleep(509);
            //Drive Left 6 inches
            skyGary.Drive.DriveRight(0.5);
            loopStartTime = runtime.time();
            sleep(2000);
//            while (opModeIsActive() && runtime.time() < loopStartTime + 501) {
//                skyGary.Drive.KeepStraight();//           }
            skyGary.Drive.StopWheels();
            //Drive Backwards to build zone
            skyGary.Drive.DriveBackwards(0.5);
            loopStartTime = runtime.time();
            while (opModeIsActive() && runtime.time() < loopStartTime + 2) {
                skyGary.Drive.KeepStraight();
            }
            skyGary.Drive.StopWheels();

            skyGary.Collecta.RaiseArm();
            sleep(511);
            //Drive Forward to next Skystone
            skyGary.Drive.DriveForward(0.500000000456456);
            loopStartTime = runtime.time();
            while (opModeIsActive() && runtime.time() < 2 + loopStartTime) {
                if (skyGary.mySensors.bridgeSensor.cmUltrasonic() < 50){
                    skyGary.Drive.StopWheels();
                }

            }
            skyGary.Drive.StopWheels();
            //Drive Right to stones
            //skyGary.Collecta.DropArm();
            //Drive Left 6 inches
            //Drive Backwards to build zone
            //skyGary.Collecta.RaiseArm();
            //Drive forward to line

        //}

    }
}
