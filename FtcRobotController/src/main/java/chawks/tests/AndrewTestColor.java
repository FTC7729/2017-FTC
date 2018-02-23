package chawks.tests;
import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
@TeleOp(name = "Sensor: MR Color", group = "Sensor")
@Disabled
public class AndrewTestColor extends LinearOpMode {

    ColorSensor colorSensor;
    @Override
    public void runOpMode() {
        float hsvValues[] = {0F,0F,0F};

        final float values[] = hsvValues;

        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);

        boolean bPrevState = false;
        boolean bCurrState = false;

        boolean bLedOn = true;

        colorSensor = hardwareMap.get(ColorSensor.class, "sensor_color");

        colorSensor.enableLed(bLedOn);

        waitForStart();

        while (opModeIsActive()) {

            bCurrState = gamepad1.x;

            if (bCurrState && (bCurrState != bPrevState))  {

                bLedOn = !bLedOn;
                colorSensor.enableLed(bLedOn);
            }

            bPrevState = bCurrState;

            Color.RGBToHSV(colorSensor.red() * 8, colorSensor.green() * 8, colorSensor.blue() * 8, hsvValues);


            telemetry.addData("LED", bLedOn ? "On" : "Off");
            telemetry.addData("Clear", colorSensor.alpha());
            telemetry.addData("Red  ", colorSensor.red());
            telemetry.addData("Green", colorSensor.green());
            telemetry.addData("Blue ", colorSensor.blue());
            telemetry.addData("Hue", hsvValues[0]);

            relativeLayout.post(new Runnable() {
                public void run() {
                    relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
                }
            });

            telemetry.update();
        }

        relativeLayout.post(new Runnable() {
            public void run() {
                relativeLayout.setBackgroundColor(Color.WHITE);
            }
        });
    }
}
