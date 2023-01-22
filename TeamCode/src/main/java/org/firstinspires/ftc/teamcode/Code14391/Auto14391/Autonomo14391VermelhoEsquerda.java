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

package org.firstinspires.ftc.teamcode.Code14391.Auto14391;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Code14391.FourBar;
import org.firstinspires.ftc.teamcode.Code14391.IniciarHardware;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous(name="Autônomo Vermelho Esquerda", group="Robot")
public class Autonomo14391VermelhoEsquerda extends LinearOpMode {

    // Objeto da classe hardware e da classe de tempo
    IniciarHardware hardware = new IniciarHardware();
    TensorFlow tensorFlow = new TensorFlow(this);
    FourBar fourBar = new FourBar();

    double y = 0;
    @Override
    public void runOpMode() {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        hardware.iniciarHardware(hardwareMap);

        tensorFlow.initTensorFlow();
        String zona = tensorFlow.lerTensorFlow();

        if(zona.equals("1 Bolt")) {
            y = -60;
        } else if(zona.equals("2 Bulb")) {
            y = -35;
        } else if(zona.equals("3 Panel")) {
            y = -10;
        }

        Pose2d startPose = new Pose2d(-60, -35.25, Math.toRadians(0));
        drive.setPoseEstimate(startPose);

        TrajectorySequence traj = drive.trajectorySequenceBuilder(startPose)
                .splineTo(new Vector2d(-10.8, -35.25), 0)
                .splineToConstantHeading(new Vector2d(-10.8, -23), 0)
                .addDisplacementMarker(5, () -> {
                    fourBar.setEstagio("Medium");
                })
                .strafeTo(new Vector2d(-10.8, y))
                .build();


        // Espera o piloto da o comando de start na Ds
        waitForStart();

        drive.followTrajectorySequence(traj);
        
        // Mensagem mostrando que acabou toda movimentação
        telemetry.addData("Path", "Complete");
        telemetry.update();
    }


}
