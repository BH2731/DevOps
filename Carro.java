/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject2;

import javax.swing.JOptionPane;

/**
 *
 * @author 26175344
 */
public class Carro {
    private String modelo;
    private String cor;
    private int ano;
            
////////////////////////////////////////////////////////////////////////
    //Construtor 1
    public Carro(){
        
    }
    // Construtor 2
    public Carro (String modelo, String cor, int ano) {
        
     this.modelo = modelo;
     this.cor = cor;
     if(ano < 1900){
         this.ano = 1900;
     }
     else{
         this.ano = ano;
     }
    }
    //Construtor 3
    public Carro(String modelo){
        this.modelo = modelo;
    }
///////////////////////////////////////////////////////////////////////
//    aqui vamos começar com os setters e gatters para o Modelo
    public String getModelo(){
        return modelo;
    }

    public void setModelo(String modelo){
        this.modelo = modelo;
    }
//   Setters e Getters para a Cor
    public String getCor(){
        return cor;
    }
    
    public void setCor(String cor){
        this.cor = cor;
    }
    
    //   Setters e Getters para a Cor
    public int getAno(int ano){
        return ano;
    }
    
    public void setAno(int ano){
        this.ano = ano;
    }
///////////////////////////////////////////////////////////////////////        
    void acelerar()
    {
        JOptionPane.showMessageDialog(null, "O "+this.modelo+" "+this.cor+" de "+this.ano+" Esta Acelerando!");
        System.out.println("O "+this.modelo+" "+this.cor+" de "+this.ano+" Esta Acelerando!");
    }
}
