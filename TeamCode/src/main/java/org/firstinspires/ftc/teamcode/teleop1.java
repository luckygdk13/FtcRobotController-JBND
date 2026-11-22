package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp
public class teleop1 extends LinearOpMode {
    public DcMotor Motor0, Motor1, Motor2, Motor3, IntakeMotor, BeltMotor, ShooterMotorPollen;//ShooterMotorNectar;
    public Servo StopperServoPollen;
    @Override
    public void runOpMode() throws InterruptedException {

        Motor0 = hardwareMap.get(DcMotor.class, "Motor0");
        Motor1 = hardwareMap.get(DcMotor.class, "Motor1");
        Motor2 = hardwareMap.get(DcMotor.class, "Motor2");
        Motor3 = hardwareMap.get(DcMotor.class, "Motor3");
        IntakeMotor = hardwareMap.get(DcMotor.class, "IntakeMotor");
        BeltMotor = hardwareMap.get(DcMotor.class, "BeltMotor");
        ShooterMotorPollen = hardwareMap.get(DcMotor.class, "ShooterMotorPollen");
        //ShooterMotorNectar = hardwaremap.get(DcMotor.class,"ShooterMotorNectar");
        StopperServoPollen = hardwareMap.get(Servo.class, "StopperServoPollen");
        ElapsedTime shooterTimer = new ElapsedTime();
        boolean wasLeftBumperPressed = false;

        Motor0.setDirection(DcMotor.Direction.REVERSE);
        Motor1.setDirection(DcMotor.Direction.REVERSE);


        waitForStart();
        while (opModeIsActive()) {
            double leftY = -gamepad2.left_stick_y;
            double leftX = gamepad2.left_stick_x;
            double rightX = gamepad2.right_stick_x;
            double frontLeftPower = leftY + leftX + rightX;
            double backLeftPower = leftY - leftX + rightX;
            double frontRightPower = leftY - leftX - rightX;
            double backRightPower = leftY + leftX - rightX;
            int dpaddown = 0;
            int dpadup = 0;
            double shootPower = 0.77;
            double max = Math.max(1.0, Math.max(Math.abs(frontLeftPower),
                    Math.max(Math.abs(backLeftPower),
                            Math.max(Math.abs(frontRightPower), Math.abs(backRightPower)))));


            Motor0.setPower(frontRightPower);
            Motor1.setPower(backRightPower);
            Motor2.setPower(backLeftPower);
            Motor3.setPower(frontLeftPower);
            if (gamepad1.x) {
                IntakeMotor.setPower(1);
                BeltMotor.setPower(1);
            }
            if (gamepad1.b) {
                IntakeMotor.setPower(-0.7);
            }if (gamepad1.a) {
                IntakeMotor.setPower(-0.7);
                BeltMotor.setPower(-0.7);
            }
            if (!gamepad1.x && !gamepad1.a && !gamepad1.b) {
                IntakeMotor.setPower(0);
                BeltMotor.setPower(0);
            }
            if (gamepad1.left_bumper) {
                if(!gamepad1.leftBumperWasPressed()) {
                    shooterTimer.reset();
                    wasLeftBumperPressed = true;
                }
                ShooterMotorPollen.setPower(shootPower);
                if (shooterTimer.milliseconds() >= 3000) {
                    StopperServoPollen.setPosition(0.4);
                    BeltMotor.setPower(1);
                }else {
                    wasLeftBumperPressed = false;
                    ShooterMotorPollen.setPower(0);
                    StopperServoPollen.setPosition(0);
                    BeltMotor.setPower(0);
                }
            }
            if (gamepad1.right_bumper) {
                //ShooterMotorNectar.setPower(1);
            }
            if (!gamepad1.right_bumper) {
                //ShooterMotorNectar.setPower(0);
            }

            frontLeftPower /= max;
            backLeftPower /= max;
            frontRightPower /= max;
            backRightPower /= max;

            telemetry.addData("Front Left Power", frontLeftPower);
            telemetry.addData("Back Left Power", backLeftPower);
            telemetry.addData("Front Right Power", frontRightPower);
            telemetry.addData("Back Right Power", backRightPower);
            telemetry.addData("Shooter Power", shootPower);
            telemetry.update();


        }
    }

}