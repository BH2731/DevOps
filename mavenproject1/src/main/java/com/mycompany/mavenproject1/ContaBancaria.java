package com.mycompany.mavenproject1;

public class ContaBancaria {
    private int numero;
    private String titular;
    private double saldo;

    public ContaBancaria() {
        this.saldo = 0;
    }

    public ContaBancaria(int numero, String titular) {
        this.numero = numero;
        this.titular = titular;
        this.saldo = 0;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public double getSaldo() {
        return saldo;
    }

    public boolean depositar(double valor) {
        if (valor <= 0) {
            return false;
        }

        saldo += valor;
        return true;
    }

    public boolean sacar(double valor) {
        return realizarSaque(valor, saldo);
    }

    /*
     * Permite que subclasses especializem o limite disponível para saque,
     * sem expor o saldo e sem criar um setSaldo().
     */
    protected boolean realizarSaque(double valor, double valorDisponivel) {
        if (valor <= 0 || valor > valorDisponivel) {
            return false;
        }

        saldo -= valor;
        return true;
    }
}
