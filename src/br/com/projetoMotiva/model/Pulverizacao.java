package br.com.projetoMotiva.model;

public class Pulverizacao extends IntervencaoOperacional{

    @Override
    public void executarServico() {
        System.out.println("Executando pulverização\n");
    }
}
