/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.mavenproject2;

import javax.swing.JOptionPane;

/**
 *
 * @author 26175344
 */
public class Mavenproject2 {

    public static void main(String[] args) {
        Carro carro1 = new Carro();
        Carro carro2 = new Carro();
        // Esse Objeto ja Usa o Construtor 2
        Carro carro3 = new Carro("VW Gol Bolinha", "Prata",1500);
        Carro carro4 = new Carro("Astra");
        
        /* carro1.modelo = "Tracker";;;
        carro1.cor = "Prata";
        
        carro2.modelo = "Fox";
        carro2.cor = "Vermelho";
        
        carro4.cor = "Azul";*/
        carro1.setModelo("Tracker");
        carro1.setCor("Prata");
        carro1.setAno(2020);
        
        carro2.setModelo("Fox");
        carro2.setCor("Vermelho");
        carro2.setAno(2018);
     
        
        carro1.acelerar();
        carro2.acelerar();
        carro3.acelerar();
        carro4.acelerar();
        
        
                
        carro2.setCor("Vermelho");
        
        carro3.setCor("Branca");
       
        carro4.setCor("Verde");
        
//      como ja usamos o metodo acelerar ele ja mostra os atributos para usarmos um GETTER, vamos fazer a seguinte linha:
        Carro carro5 = new Carro("Civic");
        carro5.setCor("Preto");
        carro5.setAno( 2020);
        carro5.acelerar();
    }
}
