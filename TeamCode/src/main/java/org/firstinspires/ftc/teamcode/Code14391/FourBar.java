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

package org.firstinspires.ftc.teamcode.Code14391;

import com.qualcomm.robotcore.hardware.DcMotor;

public class FourBar {

    // Definindo as variáveis que recebem uma constante baseada na altura medida pelo encoder
    static final double LOW  = 0;
    static final double MEDIUM  = 0;
    static final double HIGH  = 0;
    static final double GROUND  = 0;
    static final double SPEED = 0;

    // Objeto da classe IniciarHardware (serve para inicializar os motores)
    IniciarHardware hardware = new IniciarHardware();

    // Variável que guarda o estado anterior do botão A
    boolean aAnterior = false;
    boolean trava = false;
    // Variável que guarda o estágio atual do elevador (inicializada como ground)
    private String estagio = "Ground";

    // Metódo que define o estágio para o qual a 4-bar irá
    public void setEstagio(String estagio) {
        Intake intake = new Intake();
        // Switch onde é verificado o estágio atual e então subimos um
        // Exemplo: Estamos no Low, logo no próximo queremos ir para o Medium
            switch(estagio) {
                // Valores que serão verificados pelo Switch (case "x") quase como
                // estagio == "x"?
                case "Ground":
                    // Manda os motores subirem até o Low
                    elevarCone(LOW);
                    // Importante alterar o valor da variável global que guarda o estágio no qual estamos
                    // this.estagio = "estagio_atual";
                    this.estagio = "Low";
                    // Quebra o escopo para não lermos o resto do switch
                    break;
                case "Low":
                    // Manda os motores subirem até o meio
                    elevarCone(MEDIUM);
                    this.estagio = "Medium";
                    break;
                case "Medium":
                    // Manda os motores subirem até o High
                    elevarCone(HIGH);
                    this.estagio = "High";
                }
                // Ajustamos a posição do servo que segura o cone no fim de cada estágio
        intake.ajusteIntake(this.estagio);
    }

    // Função getter para ler o estágio atual
    public String getEstagio() {
        return estagio;
    }

    // Método que será usado para controlar para qual estágio a 4-bar irá
    public void estagio(boolean a, boolean y) {
        trava = hardware.bumperSwitch.getState();
        // Verifica se o botão A está apertado e se o estado anterior é falso
            if((a && !aAnterior) && (!trava ^ getEstagio().equals("Ground"))) {
                // Se for, o estagio é lido
                String estagio = getEstagio();
                // Tornamos o valor do A anterior verdadeiro para fechar o if
                aAnterior = true;
                // Então setamos o estagio para o qual queremos ir
                setEstagio(estagio);
            } else if(y || trava) {
                // Caso o botão Y seja apertado ele irá retornar para o estágio
                // Ground
                elevarCone(GROUND);
                this.estagio = "Ground";
            } else if(!a) {
                // Se o botão A for soltado, o valor anterior de A é falso
                aAnterior = false;
                // Assim reabrimos o if inicial (lógica toggle)
            }
            // Verificamos os motores para descobrir se eles chegaram na altura
        // Caso cheguem, os motores param e travam
            verificarMototores();
    }

    // Método para passar os valores de encoder para os motores da 4-bar
    private void elevarCone(double altura) {

        // Cria as variáveis inteiras que receberão os valores de encoder
        int newAlturaEsquerda;
        int newAlturaDireita;

            // Determina o valor das variáveis com base no estágio escolhido
            newAlturaEsquerda = (int)altura - hardware.motor1.getCurrentPosition();
            newAlturaDireita = (int)altura - hardware.motor2.getCurrentPosition() ;


            // Seta o alvo dos motores para esses valores
            hardware.motor1.setTargetPosition(newAlturaEsquerda);
            hardware.motor2.setTargetPosition(-1 * newAlturaDireita);

            // Coloca os motores de volta ao estado normal
            hardware.motor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            hardware.motor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

            // Coloca os motores para ir até a posição
            hardware.motor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            hardware.motor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // Seta as velocidades que os motores irão se movimentar
            // Aqui que eles começam a se mover
            hardware.motor1.setPower(Math.abs(FourBar.SPEED));
            hardware.motor2.setPower(Math.abs(FourBar.SPEED));
        }

        private void verificarMototores(){
            if(!(hardware.motor1.isBusy() && hardware.motor2.isBusy())) {
                hardware.motor1.setPower(0);
                hardware.motor2.setPower(0);

                hardware.motor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                hardware.motor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                hardware.motor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                hardware.motor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            }
        }
}