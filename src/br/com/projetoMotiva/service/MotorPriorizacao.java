package br.com.projetoMotiva.service;

import br.com.projetoMotiva.model.TrechoRodovia;
import br.com.projetoMotiva.model.Pulverizacao;
import br.com.projetoMotiva.model.RocadaMecanizada;

import java.util.ArrayList;

public class MotorPriorizacao {

    public void decidirIot(TrechoRodovia trecho){
        if (!trecho.isPossuiSensorIoT()){
            System.out.println("Não possui IoT.\n");
        }
        else if (trecho.getAlturaVegetacao() > 1.5){
            RocadaMecanizada rocada = new RocadaMecanizada();
            trecho.transmitirDadosSensor();
            calcularCrescimento(trecho);
            rocada.executarServico();
        } else {
            Pulverizacao pulverizacao = new Pulverizacao();
            trecho.transmitirDadosSensor();
            calcularCrescimento(trecho);
            pulverizacao.executarServico();
        }
    }

    public void calcularCrescimento(TrechoRodovia trecho){
        if (trecho.getUmidade() > 70){
            System.out.println("ALERTA: Possivel crecimento mais rapido da grama");
        } else if ( trecho.getUmidade() >= 40) {
            System.out.println("ALERTA: Possivel crescimento um pouco mais rapido da grama");
        } else {
            System.out.println("Umidade normal - Sem alertas");
        }
    }

    public void analisarTrechos(ArrayList<TrechoRodovia> trechos){
        for (TrechoRodovia trecho : trechos){

            if (trecho.getAlturaVegetacao() > 1.5){
                System.out.println(trecho.getKm() + "km - " + "ALERTA: GRAMA ALTA");
                decidirIot(trecho);
            }
            else if (trecho.getAlturaVegetacao() >= 1 && trecho.getAlturaVegetacao() <= 1.5){
                System.out.println(trecho.getKm() + "km - " + "ALERTA: GRAMA MEDIA");
                decidirIot(trecho);
            }
            else if (trecho.getAlturaVegetacao() < 1){
                System.out.println(trecho.getKm() + "km - " + "ALERTA: GRAMA BAIXA");
                decidirIot(trecho);
            }
            else {
                System.out.println(trecho.getKm() + "km - " + "SEM ALERTAS");
            }
        }
    }
}