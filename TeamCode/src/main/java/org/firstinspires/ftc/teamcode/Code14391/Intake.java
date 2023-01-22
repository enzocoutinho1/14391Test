package org.firstinspires.ftc.teamcode.Code14391;

public class Intake extends IniciarHardware {
    // Variável que guarda o valor anterior do R1
    Boolean r1Anterior = false;

    public void intake(boolean r1) {
        // Objeto da classe FourBar
        FourBar fourBar = new FourBar();

        /* Constantes que definem a abertura do servo
         * Obs: É aqui que se deve alterar o código caso o servo não esteja abrindo ou fechando
         * suficiente
         */
        final double FECHADA = 0;
        final double ABERTA = 1.0;
        final double SEMI_ABERTA = 0.9;

        // Verifica se é a primeira vez que se entra nessa condicional
        if(r1 && !r1Anterior) {
            // Caso seja, pega a posição do servo e verifica se esta fechada
            if(servoGarra.getPosition() == FECHADA) {
                // Caso a garra esteja fechada, verifica se a 4-bar está na base
                if(fourBar.getEstagio().equals("Ground")) {
                    // Se estiver abre a garra que pega o cone inteiramente
                    servoGarra.setPosition(ABERTA);
                } else {
                    // Se não estiver, ou seja, estiver em outro "andar"
                    // A garra abre somente um pouco
                    servoGarra.setPosition(SEMI_ABERTA);
                }
                // Torna o R1 anterior verdadeiro para fechar esse if na próxima iteração
                r1Anterior = true;
            } else {
                //Se o servo não estiver fechado, manda fechar
                servoGarra.setPosition(FECHADA);
            }
            // Caso o R1 seja solto torna o R1 anterior falso
            // Assim, abrindo o primeiro if (lógica toggle)
        } else if(!r1) {
            r1Anterior = false;
        }
    }

    public void ajusteIntake(String estagio) {
        /* Constantes que definem o ajuste do servo
         * Obs: É aqui que se deve alterar o código caso o servo não esteja girando suficiente
         */
        final double AUXILIAR_HIGH = 1.0;
        final double AUXILIAR_MEDIUM = 0.6;
        final double AUXILIAR_LOW = 0.4;
        final double AUXILIAR_GROUND = 0.0;

        // Condicional para verificarmos o estágio que estamos
        switch (estagio) {
            // Dependendo do estágio é feito o ajuste no servo acoplado a 4-bar
            case "Ground":
                servoAjuste.setPosition(AUXILIAR_GROUND);
                break;
            case "Low":
                servoAjuste.setPosition(AUXILIAR_LOW);
                break;
            case "Medium":
                servoAjuste.setPosition(AUXILIAR_MEDIUM);
                break;
            case "High":
                servoAjuste.setPosition(AUXILIAR_HIGH);
        }
    }
}