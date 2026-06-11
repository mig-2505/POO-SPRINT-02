package br.com.projetoMotiva.main;

import br.com.projetoMotiva.model.TrechoRodovia;
import br.com.projetoMotiva.service.MotorPriorizacao;

import java.util.ArrayList;

public class SistemaMonitoramento {

    public static void main(String[] args) {

        TrechoRodovia trecho1 = new TrechoRodovia(10, 2.0, 80, true);
        TrechoRodovia trecho2 = new TrechoRodovia(20, 1.2, 60, false);
        TrechoRodovia trecho3 = new TrechoRodovia(30, 0.8, 30, true);

        ArrayList<TrechoRodovia> trechos = new ArrayList<>();
        trechos.add(trecho1);
        trechos.add(trecho2);
        trechos.add(trecho3);

        MotorPriorizacao motor = new MotorPriorizacao();
        motor.analisarTrechos(trechos);
    }
}
