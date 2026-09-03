package com.mycompany.mavenproject1;

public class ContaPoupanca extends ContaBancaria {
    private double taxaRendimento;

    public ContaPoupanca() {
        super();
        this.taxaRendimento = 0;
    }

    public ContaPoupanca(int numero, String titular, double taxaRendimento) {
        super(numero, titular);
        this.taxaRendimento = Math.max(0, taxaRendimento);
    }

    public double getTaxaRendimento() {
        return taxaRendimento;
    }

    public boolean aplicarRendimento() {
        if (taxaRendimento <= 0 || getSaldo() <= 0) {
            return false;
        }

        double rendimento = getSaldo() * taxaRendimento / 100;
        return depositar(rendimento);
    }
}
