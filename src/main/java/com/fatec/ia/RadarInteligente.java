package com.fatec.ia;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RadarInteligente {
    public static class Registro{
        int velocidadeMedida;
        boolean houveAcidente;

        public Registro(){}

        public Registro(int velocidadeMedida, boolean houveAcidente){
            this.velocidadeMedida = velocidadeMedida;
            this.houveAcidente = houveAcidente;
        }
    }

    public static class Historico{
        List<Registro> registros;

        public Historico() {
            this.registros = new ArrayList<>();
        }

        public Historico(List<Registro> registros) {
            this.registros = registros;
        }
    }

    public static void main(String[] args){
        Historico historico = new Historico();
        historico.registros.add(new Registro(50, false));
        historico.registros.add(new Registro(60, false));
        historico.registros.add(new Registro(80, true));
        historico.registros.add(new Registro(30, false));
        historico.registros.add(new Registro(50, true));
        historico.registros.add(new Registro(50, false));

        int velocidadeMaximaPermitida = calcularVelocidadeMaximaPermitida(historico);
        while(true){
            System.out.println("\nRADAR INTELIGENTE");
            System.out.println("DIGITE 0 PARA SAIR");
            Registro registro = new Registro();
            Scanner sc = new Scanner(System.in);
            System.out.println("DIGITE A VELOCIDADE MEDIDA: ");
            try{
                registro.velocidadeMedida = sc.nextInt();
                if(registro.velocidadeMedida == 0){
                    break;
                }

                System.out.println("DIGITE SE HOUVE ACIDENTE. 0 (NAO HOUVE) 1 (HOUVE): ");
                int houveAcidente = sc.nextInt();
                registro.houveAcidente = houveAcidente == 1;

                historico.registros.add(registro);
                velocidadeMaximaPermitida = calcularVelocidadeMaximaPermitida(historico);

                if(verificarVelocidadeMedida(registro.velocidadeMedida, velocidadeMaximaPermitida)) {
                    System.out.println("!!! MULTADO !!! VOCÊ PASSOU A " + registro.velocidadeMedida +  " MAS O PERMITIDO ERA "  + velocidadeMaximaPermitida);
                } else{
                    System.out.println("!!! OK !!! VOCÊ PASSOU A " + registro.velocidadeMedida +  " E O PERMITIDO É "  + velocidadeMaximaPermitida);
                }

            } catch (Exception e){
                System.out.println("ERRO! TENTE NOVAMENTE.");
            }

        }
        System.out.println("\nVELOCIDADE MAXIMA PERMITIDA ATUAL: " + velocidadeMaximaPermitida);
    }


    /**
     * MÉTODO PARA CALCULAR A VELOCIDADE MÁXIMA PERMITIDA
     * FAZENDO A MÉDIA DAS VELOCIDADES QUE NÕA HOUVERAM ACIDENTES
     * 1 - SEPARA AS VELOCIDADES, ADICIONANDO EM UMA LISTA
     * 2 - SOMA AS VELOCIDADES DA LISTA E DIVIDE PELO NUMERO DE ITENS DA LISTA
     * EX: (60 + 50 + 50) / 3
     * @param historico HISTÓRICO COM OS REGISTROS DE MEDIÇÕES (velocidadeMedida, houveAcidente)
     * @return VELOCIDADE MÁXIMA PERMITIDA
     */
    public static int calcularVelocidadeMaximaPermitida(Historico historico){
        List<Integer> velocidadesQueNaoHouveramAcidentes = new ArrayList<>();
        for(Registro registro : historico.registros){
            if(!registro.houveAcidente){
                velocidadesQueNaoHouveramAcidentes.add(registro.velocidadeMedida);
            }
        }

        return (velocidadesQueNaoHouveramAcidentes.stream().mapToInt(Integer::intValue).sum()) / velocidadesQueNaoHouveramAcidentes.size();
    }

    /**
     * MÉTODO PARA VERIFICAR SE A VELOCIDADE MEDIDA ESTA DENTRO DO PERMITIDO
     * @param velocidadeMedida
     * @param velocidadeMaximaPermitida
     * @return true PARA ACIMA DO PERMITIDO false PARA DENTRO DO LIMITE PERMITIDO
     */
    public static boolean verificarVelocidadeMedida(int velocidadeMedida, int velocidadeMaximaPermitida){
        System.out.println("VERIFICANDO VELOCIDADE....");
        return velocidadeMedida > velocidadeMaximaPermitida;
    }

    /**
     * O PROBLEMA DESSE MÉTODO, É QUE SE HOUVE UM ACIDENTE COM UMA VELOCIDADE MUITO BAIXA
     * ELE VAI CONSIDERAR ESSA VELOCIDADE, A MÁXIMA PERMITIDA.
     * EX: HOUVE ACIDENTE COM 20KMH, A MÁXIMA PERMITIDA SERÁ 20KMH, O QUE É BAIXO.
     */
    public static int calcularVelocidadeMaximaPermitidaMetodoAlternativo(Historico historico){
        int velocidadeMaximaPermitida = Integer.MAX_VALUE;

        for(Registro registro : historico.registros){
            // SE A VELOCIDADE MAXIMA PERMITIDA FOR 50KM
            // MAS COM 45KM HOUVE ACIDENTE, ENTÃO A VELOCIDADE MAXIMA PERMITIDA
            // PASSA A SER 45KM - 1, OU SEJA, 44KM.
            if(registro.houveAcidente && registro.velocidadeMedida < velocidadeMaximaPermitida){
                velocidadeMaximaPermitida = registro.velocidadeMedida - 1;
            }
        }
        return velocidadeMaximaPermitida;
    }


}
