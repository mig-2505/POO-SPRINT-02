package br.com.projetoMotiva.model;

import br.com.projetoMotiva.interfaces.MonitoravelViaIoT;

public class TrechoRodovia implements MonitoravelViaIoT{

    private int km;
    private double alturaVegetacao;
    private double umidade;
    private boolean possuiSensorIoT;

    public TrechoRodovia(int km, double alturaVegetacao, double umidade, boolean possuiSensorIoT) {
        this.setKm(km);
        this.setAlturaVegetacao(alturaVegetacao);
        this.setUmidade(umidade);
        this.setPossuiSensorIoT(possuiSensorIoT);
    }

    @Override
    public void transmitirDadosSensor() {
        System.out.println("Sensor IoT ativo no KM " + km + ". Altura atual da vegetação: " + alturaVegetacao + "m.");
    }

    public int getKm() {return km;}

    private void setKm(int km) {this.km = km;}

    public double getAlturaVegetacao() {return alturaVegetacao;}

    private void setAlturaVegetacao(double alturaVegetacao) {this.alturaVegetacao = alturaVegetacao;}

    public double getUmidade() {return umidade;}

    private void setUmidade(double umidade) {this.umidade = umidade;}

    public boolean isPossuiSensorIoT() {return possuiSensorIoT;}

    private void setPossuiSensorIoT(boolean possuiSensorIoT) {this.possuiSensorIoT = possuiSensorIoT;}
}
