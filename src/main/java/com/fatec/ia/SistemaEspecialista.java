package com.fatec.ia;

import java.util.ArrayList;
import java.util.List;

public class SistemaEspecialista {

    private boolean climaChovendo;
    private boolean temDinheiro;
    private boolean carroDisponivel;
    private boolean namoradaDeixou;
    private boolean vaiBeber;
    private boolean amigosVao;
    private boolean trabalhoAmanha;
    private boolean distanciaDoLocalPerto;

    public SistemaEspecialista(boolean climaChovendo, boolean temDinheiro, boolean carroDisponivel,
                               boolean namoradaDeixou, boolean vaiBeber, boolean amigosVao,
                               boolean trabalhoAmanha, boolean distanciaDoLocalPerto) {
        this.climaChovendo = climaChovendo;
        this.temDinheiro = temDinheiro;
        this.carroDisponivel = carroDisponivel;
        this.namoradaDeixou = namoradaDeixou;
        this.vaiBeber = vaiBeber;
        this.amigosVao = amigosVao;
        this.trabalhoAmanha = trabalhoAmanha;
        this.distanciaDoLocalPerto = distanciaDoLocalPerto;
    }

    // Método para avaliar se a pessoa pode sair ou não
    public String possoSair() {
        // Regra: Se namorada não deixou, então não pode sair
        if (!namoradaDeixou) {
            return "Não pode sair: Namorada não deixou!";
        }

        boolean precisaUber = false;
        // Regra: Se clima = chovendo e carro = não disponivel entao precisa de uber
        if(climaChovendo && !carroDisponivel){
            precisaUber = true;
        }

        // Regra: Se vai beber e carro esta disponivel, precisa de uber, porque se beber não dirija
        if(vaiBeber && carroDisponivel){
            precisaUber = true;
        }

        // Regra: Se carro indisponível, sem dinheiro e precisa de uber, então não pode sair
        if (!carroDisponivel && !temDinheiro && !distanciaDoLocalPerto && precisaUber) {
            return "Não pode sair: Sem carro, sem dinheiro e chovendo!";
        }

        // Regra: Se sem dinheiro e vai beber, então não pode sair
        if (!temDinheiro && vaiBeber) {
            return "Não pode sair: Sem dinheiro e vai beber!";
        }

        if(!climaChovendo && distanciaDoLocalPerto){
            return "Pode sair: Não está chovendo e local é perto!";
        }

        // Regra: Se distância é longe, sem carro e sem dinheiro, então não pode sair
        if (!distanciaDoLocalPerto && !carroDisponivel && !temDinheiro) {
            return "Não pode sair: Distância longe, sem carro e sem dinheiro!";
        }

        // Regra: Se vai beber e tem trabalho amanhã, então não pode sair
        if (vaiBeber && trabalhoAmanha) {
            return "Não pode sair: Vai beber e tem trabalho amanhã!";
        }

        // Regra: Se amigos vão e clima é ensolarado, então pode sair
        if (amigosVao && !climaChovendo) {
            return "Pode sair: Amigos vão e o clima está bom!";
        }

        // Regra: Se tem dinheiro, carro disponível e não precisa de uber (pois não vai beber)
        if (temDinheiro && carroDisponivel && !precisaUber) {
            return "Pode sair: Tudo certo, pode sair!";
        }

        // Regra: Se vai beber não, tem dinheiro e amigos vão, então pode sair
        if (!vaiBeber && temDinheiro && amigosVao) {
            return "Pode sair: Não vai beber, tem dinheiro e amigos vão!";
        }

        // Se nenhuma regra especial foi acionada, vamos por uma decisão genérica
        return "Não pode sair: Não foi possível determinar com as condições atuais.";
    }


    public static void main(String[] args) {
        System.out.println("\nSISTEMA ESPECIALISTA: POSSO SAIR?");

        List<SistemaEspecialista> sistemaEspecialistaList = new ArrayList<>();
        sistemaEspecialistaList.add(
                // CASO 01
                new SistemaEspecialista(
                    true,  // climaChovendo
                    true,  // temDinheiro
                    false, // carroDisponivel
                    true,  // namoradaDeixou
                    false, // vaiBeber
                    true,  // amigosVao
                    false, // trabalhoAmanha
                    false // distanciaDoLocalPerto
                )
        );

        sistemaEspecialistaList.add(
                // CASO 02
                new SistemaEspecialista(
                        false,  // climaChovendo
                        true,  // temDinheiro
                        true, // carroDisponivel
                        false,  // namoradaDeixou
                        false, // vaiBeber
                        true,  // amigosVao
                        false, // trabalhoAmanha
                        true // distanciaDoLocalPerto
                )
        );


        sistemaEspecialistaList.add(
                // CASO 03
                new SistemaEspecialista(
                        true,  // climaChovendo
                        false,  // temDinheiro
                        false, // carroDisponivel
                        true,  // namoradaDeixou
                        true, // vaiBeber
                        true,  // amigosVao
                        false, // trabalhoAmanha
                        false // distanciaDoLocalPerto
                )
        );


        int count = 1;
        for(SistemaEspecialista se : sistemaEspecialistaList){
            System.out.println("CASO " + count + " " + se.possoSair());
            count++;
        }
    }
}

