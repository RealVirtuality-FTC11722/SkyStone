package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by Lyesome on 2018-01-13.
 * This class is used to pass the gamepad controls to generalized methods that the other classes can use.
 * This allows all the controls to be defined in one location and makes it easier to change the controls if need.
 */

public class BotControls {

    public BotControls()  { // constructor
    }

    //GAMEPAD 1
    //Drive controls
    public static double  DriveYStick(LinearOpMode op)              {return op.gamepad1.left_stick_y; }
    public static double  DriveXStick(LinearOpMode op)              {return op.gamepad1.left_stick_x; }
    public static double  TurnStick(LinearOpMode op)                {return op.gamepad1.right_stick_x; }
    public static double  DriveThrottle(LinearOpMode op)            {return op.gamepad1.right_trigger;}

    //Build Platform Grabber
    public static boolean PlatformGrabButton(LinearOpMode op)      {
        if (op.gamepad1.right_trigger > 0.5) {
            return true;
        } else {
            return false;
        }
    }

    //Parking Scorer
    public static boolean ParkTrigger(LinearOpMode op)              {return op.gamepad1.right_bumper;}

    //GAMEPAD 2
    //Collector controls
    public static boolean SpinnerInButton(LinearOpMode op)          {return  op.gamepad2.x;}
    public static boolean SpinnerOutButton(LinearOpMode op)         {return  op.gamepad2.b;}
    public static boolean SpinnerStopButton(LinearOpMode op)        {return  op.gamepad2.a;}
    public static boolean SideCollectorButton(LinearOpMode op)      {return  op.gamepad2.y;}
    public static boolean SideArmDownButton(LinearOpMode op)        {return  op.gamepad2.dpad_down;}
    public static boolean SideArmUpButton(LinearOpMode op)          {return  op.gamepad2.dpad_up;}

    //Builder controls
    public static double  LifterStick(LinearOpMode op)              {return op.gamepad2.right_stick_y;}
    public static double  ClampButton(LinearOpMode op)              {return op.gamepad2.right_trigger;}
    public static double  PlateStick(LinearOpMode op)               {return op.gamepad2.left_stick_y;}
    public static double  KickOutButton(LinearOpMode op)            {return op.gamepad2.left_trigger;}
}

