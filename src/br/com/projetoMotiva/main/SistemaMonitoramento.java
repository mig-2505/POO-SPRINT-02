package br.com.projetoMotiva.main;

import br.com.projetoMotiva.mock.SensorMock;
import br.com.projetoMotiva.model.TrechoRodovia;
import br.com.projetoMotiva.service.MotorPriorizacao;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SistemaMonitoramento {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        TrechoRodovia trecho1 = new TrechoRodovia(10, 2.0, 80, true);
        TrechoRodovia trecho2 = new TrechoRodovia(20, 1.2, 60, false);
        TrechoRodovia trecho3 = new TrechoRodovia(30, 0.8, 30, true);

        ArrayList<TrechoRodovia> trechos = new ArrayList<>();
        trechos.add(trecho1);
        trechos.add(trecho2);
        trechos.add(trecho3);

        MotorPriorizacao motorPriorizacao = new MotorPriorizacao();

        int opcao;

        do {

            System.out.println("==== MENU ====");
            System.out.println("1. Gerar Relatorio Geral");
            System.out.println("2. Gerar Relatorio Umidade");
            System.out.println("3. Analisar Trecho por KM");
            System.out.println("4. Testar Sensor Mock");
            System.out.println("0. Encerrar Sistema");
            System.out.println("Opção:.. ");
            opcao = sc.nextInt();

            switch (opcao) {

                case 1:
                    System.out.println("=== RELATORIO GERAL ===");
                    motorPriorizacao.analisarTrechos(trechos);
                    break;

                case 2:
                    System.out.println("=== RELATORIO UMIDADE ===");
                    for (TrechoRodovia trecho : trechos){
                        System.out.println(trecho.getKm() + "km:");
                        if (trecho.isPossuiSensorIoT()){
                            motorPriorizacao.calcularCrescimento(trecho);
                        } else {
                            System.out.println("Sem sensor Iot");
                        }
                    }
                    break;

                case 3:
                    System.out.print("Digite o KM do trecho: ");
                    int km = sc.nextInt();
                    boolean encontrado = false;

                    for (TrechoRodovia trecho : trechos) {
                        if (trecho.getKm() == km) {
                            motorPriorizacao.analisarTrechos(new ArrayList<>(List.of(trecho)));
                            encontrado = true;
                            break;
                        }
                    }
                    if (!encontrado) {
                        System.out.println("Trecho não encontrado.");
                    }
                    break;

                case 4:
                    System.out.println("=== TESTE COM SENSOR MOCK ===");
                    SensorMock mock = new SensorMock(1.8, 75);
                    mock.transmitirDadosSensor();
                    System.out.println(mock.classificarCrescimento());
                    System.out.println(mock.simularEfeitoUmidade());
                    break;

                case 0:
                    System.out.println("Encerrando sistema...");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 0);

        }
    }
