package org.firstinspires.ftc.teamcode.Code14391;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class IniciarHardware {

    // Cria os objetos da classe motor
    public DcMotor motor1;
    public DcMotor motor2;
    public DcMotor motorEsquerda;
    public DcMotor motorEsquerdaTras;
    public DcMotor motorDireita;
    public DcMotor motorDireitaTras;
    public Servo servoGarra;
    public Servo servoAjuste;
    public DigitalChannel bumperSwitch;
    public void iniciarHardware(HardwareMap hardwareMap) {
        // os objetos da classe motor recebem o nome que está na Drive Stration (Ds)
        // Logo, o nome entre aspas duplas abaixo tem que ser o mesmo que está na Ds
        motorEsquerda = hardwareMap.get(DcMotor.class, "motorEsquerda");
        motorEsquerdaTras = hardwareMap.get(DcMotor.class, "motorEsquerdaTras");
        motorDireita = hardwareMap.get(DcMotor.class, "motorDireita");
        motorDireitaTras = hardwareMap.get(DcMotor.class, "motorDireitaTras");
        motor1 = hardwareMap.get(DcMotor.class, "motor4Bar1");
        motor2 = hardwareMap.get(DcMotor.class, "motor4Bar2");

        //  Servos recebem o nome da Ds
        servoGarra = hardwareMap.get(Servo.class, "servo_garra");
        servoAjuste = hardwareMap.get(Servo.class, "servo_auxiliar");
        bumperSwitch = hardwareMap.get(DigitalChannel.class, "bumper_switch");
        // Seta a direção dos motores
        // Como os motores são colocados invertidos em cada lado da tração, é necessário saber
        // quais dos lados está invertido em relação ao outro (vai depender de onde é a frente do robô)
        motorEsquerda.setDirection(DcMotor.Direction.REVERSE);
        motorEsquerdaTras.setDirection(DcMotor.Direction.REVERSE);
        motorDireita.setDirection(DcMotor.Direction.FORWARD);
        motorDireitaTras.setDirection(DcMotor.Direction.FORWARD);

        // Para e reseta os encoders da tração e 4-bar
        motorEsquerda.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorEsquerdaTras.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorDireita.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorDireitaTras.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
}
