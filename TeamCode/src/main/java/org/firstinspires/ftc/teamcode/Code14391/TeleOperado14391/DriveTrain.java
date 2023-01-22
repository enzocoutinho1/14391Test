/* Copyright (c) 2021 FIRST. All rights reserved.
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

package org.firstinspires.ftc.teamcode.Code14391.TeleOperado14391;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Code14391.FourBar;
import org.firstinspires.ftc.teamcode.Code14391.IniciarHardware;
import org.firstinspires.ftc.teamcode.Code14391.Intake;

// Define o nome que irá aparecer na Drive Station
@TeleOp(name="TeleOperado 14391", group="Linear Opmode")
public class DriveTrain extends LinearOpMode {

    // Declara a variável que cronometara o tempo
    private final ElapsedTime runtime = new ElapsedTime();

    boolean r1Anterior = false;

    @Override
    public void runOpMode() {

        IniciarHardware hardware = new IniciarHardware();
        FourBar fourBar = new FourBar();
        Intake intake = new Intake();
        hardware.iniciarHardware(hardwareMap);

        // Caso não dê erro de inicialização dos hardware a seguinte mensagem é exibida
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Espera o botão de start ser pressionado na Ds
        waitForStart();

        //Reseta o tempo
        runtime.reset();

        double poderEsquerda;
        double poderDireita;
        double poderEsquerdaTras;
        double poderDireitaTras;
        double reducaoVelocidade = 0.6;

        // Loop que rodará até o fim do período TeleOperado
        while (opModeIsActive()) {
            double max;

            // Pega os valores do joytsick para fazer o calculo da tração para Mechanum
            double Drive   = -gamepad1.left_stick_y;  // Eixo que define se o robô vai para frente ou para trás
            double lateral =  gamepad1.left_stick_x; // Define se o robô vai andar lateralmente
            double turn     =  gamepad1.right_stick_x; // Eixo que define o quanto ele irá girar

            // Calculo de tração para mechanum (rodas espelhadas terão valores iguais no quesito lateral)
            // Então uma variável recebe cada valor separadamente
            poderEsquerda  = (Drive + lateral + turn);
            poderDireita = (Drive - lateral - turn);
            poderEsquerdaTras   = (Drive - lateral + turn);
            poderDireitaTras  = (Drive + lateral - turn);

           // Acha o maior valor entre as 4 variáveis (valor absoluto)
            max = Math.max(Math.abs(poderEsquerda), Math.abs(poderDireita));
            max = Math.max(max, Math.abs(poderEsquerdaTras));
            max = Math.max(max, Math.abs(poderDireitaTras));

            // Verifica se esse valor é maior do que 1 (máximo que é possível para cada motor)
            if (max > 1.0) {
                // Caso seja verdaideiro
                // Divide o valor de cada motor pelo valor mais alto
                poderEsquerda  /= max;
                poderDireita /= max;
                poderEsquerdaTras   /= max;
                poderDireitaTras  /= max;
                /* É necessário fazer isso pois o valor máximo permitido é 1.0
                 * entretanto a soma pode exceder 1.0, por isso é preciso fazer
                 * essa proporção para nivelar os motores no limite -1.0 até 1.0
                 */
            }

            if(gamepad1.right_bumper && !r1Anterior) {
                if(reducaoVelocidade == 0.6) {
                    reducaoVelocidade = 0.4;
                } else {
                    reducaoVelocidade = 0.6;
                }
                r1Anterior = true;
            } else if(!gamepad1.right_bumper) {
                r1Anterior = false;
            }

            // Manda o valor calculado para cada roda individualmente
            hardware.motorEsquerda.setPower(poderEsquerda * reducaoVelocidade);
            hardware.motorDireita.setPower(poderDireita * reducaoVelocidade);
            hardware.motorEsquerdaTras.setPower(poderEsquerdaTras * reducaoVelocidade);
            hardware.motorDireitaTras.setPower(poderDireitaTras * reducaoVelocidade);

            // Mostra o valor em cada roda e o tempo decorrido desde o inicio da partida
            telemetry.addData("Status", "Run Time: " + runtime);
            telemetry.addData("Front left/Right", "%4.2f, %4.2f", poderEsquerda, poderDireita);
            telemetry.addData("Back  left/Right", "%4.2f, %4.2f", poderEsquerdaTras, poderDireitaTras);
            telemetry.update();

            // Método que fará toda escolha de estágio lendo os valores booleanos dos botões
            // A e Y do gamepad1
            fourBar.estagio(gamepad1.a, gamepad1.y);
            // Método que comanda o servo que segura o cone
            // R1 do gamepad1
            intake.intake(gamepad1.left_bumper);
        }
    }
}
