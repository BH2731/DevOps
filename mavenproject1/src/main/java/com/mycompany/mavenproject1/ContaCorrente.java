package com.mycompany.mavenproject1;

public class ContaCorrente extends ContaBancaria {
    private double limite;

    public ContaCorrente() {
        super();
        this.limite = 0;
    }

    public ContaCorrente(int numero, String titular, double limite) {
        super(numero, titular);
        this.limite = Math.max(0, limite);
    }

    public double getLimite() {
        return limite;
    }

    @Override
    public boolean sacar(double valor) {
        double valorDisponivel = getSaldo() + limite;
        return realizarSaque(valor, valorDisponivel);
    }
}
