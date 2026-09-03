package com.mycompany.mavenproject1;

import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.JOptionPane;

public class Mavenproject1 {
    private static final NumberFormat MOEDA = NumberFormat.getCurrencyInstance(
            new Locale("pt", "BR"));

    public static void main(String[] args) {
        boolean executando = true;

        while (executando) {
            String opcao = JOptionPane.showInputDialog(
                    "=== SISTEMA BANCÁRIO ===\n\n"
                    + "1 - Conta Corrente\n"
                    + "2 - Conta Poupança\n"
                    + "0 - Sair");

            if (opcao == null || opcao.equals("0")) {
                executando = false;
            } else if (opcao.equals("1")) {
                criarContaCorrente();
            } else if (opcao.equals("2")) {
                criarContaPoupanca();
            } else {
                mostrarMensagem("Opção inválida.");
            }
        }

        mostrarMensagem("Sistema encerrado.");
    }

    private static void criarContaCorrente() {
        Integer numero = lerInteiro("Informe o número da conta:");
        if (numero == null) {
            return;
        }

        String titular = lerTexto("Informe o nome do titular:");
        if (titular == null) {
            return;
        }

        Double limite = lerDouble("Informe o limite da conta:");
        if (limite == null) {
            return;
        }

        if (limite < 0) {
            mostrarMensagem("O limite não pode ser negativo.");
            return;
        }

        ContaCorrente conta = new ContaCorrente(numero, titular, limite);
        menuContaCorrente(conta);
    }

    private static void criarContaPoupanca() {
        Integer numero = lerInteiro("Informe o número da conta:");
        if (numero == null) {
            return;
        }

        String titular = lerTexto("Informe o nome do titular:");
        if (titular == null) {
            return;
        }

        Double taxa = lerDouble("Informe a taxa de rendimento (%):");
        if (taxa == null) {
            return;
        }

        if (taxa < 0) {
            mostrarMensagem("A taxa de rendimento não pode ser negativa.");
            return;
        }

        ContaPoupanca conta = new ContaPoupanca(numero, titular, taxa);
        menuContaPoupanca(conta);
    }

    private static void menuContaCorrente(ContaCorrente conta) {
        boolean menuAberto = true;

        while (menuAberto) {
            String opcao = JOptionPane.showInputDialog(
                    "=== CONTA CORRENTE ===\n"
                    + "Conta: " + conta.getNumero() + "\n"
                    + "Titular: " + conta.getTitular() + "\n\n"
                    + "1 - Consultar saldo\n"
                    + "2 - Depositar\n"
                    + "3 - Sacar\n"
                    + "4 - Consultar limite\n"
                    + "0 - Sair");

            if (opcao == null || opcao.equals("0")) {
                menuAberto = false;
            } else {
                switch (opcao) {
                    case "1":
                        mostrarMensagem("Saldo atual: " + MOEDA.format(conta.getSaldo()));
                        break;
                    case "2":
                        realizarDeposito(conta);
                        break;
                    case "3":
                        realizarSaque(conta);
                        break;
                    case "4":
                        mostrarMensagem("Limite: " + MOEDA.format(conta.getLimite()));
                        break;
                    default:
                        mostrarMensagem("Opção inválida.");
                }
            }
        }
    }

    private static void menuContaPoupanca(ContaPoupanca conta) {
        boolean menuAberto = true;

        while (menuAberto) {
            String opcao = JOptionPane.showInputDialog(
                    "=== CONTA POUPANÇA ===\n"
                    + "Conta: " + conta.getNumero() + "\n"
                    + "Titular: " + conta.getTitular() + "\n\n"
                    + "1 - Consultar saldo\n"
                    + "2 - Depositar\n"
                    + "3 - Sacar\n"
                    + "4 - Aplicar rendimento\n"
                    + "0 - Sair");

            if (opcao == null || opcao.equals("0")) {
                menuAberto = false;
            } else {
                switch (opcao) {
                    case "1":
                        mostrarMensagem("Saldo atual: " + MOEDA.format(conta.getSaldo()));
                        break;
                    case "2":
                        realizarDeposito(conta);
                        break;
                    case "3":
                        realizarSaque(conta);
                        break;
                    case "4":
                        if (conta.aplicarRendimento()) {
                            mostrarMensagem("Rendimento aplicado.\nNovo saldo: "
                                    + MOEDA.format(conta.getSaldo()));
                        } else {
                            mostrarMensagem("Não foi possível aplicar o rendimento.");
                        }
                        break;
                    default:
                        mostrarMensagem("Opção inválida.");
                }
            }
        }
    }

    private static void realizarDeposito(ContaBancaria conta) {
        Double valor = lerDouble("Informe o valor do depósito:");
        if (valor == null) {
            return;
        }

        if (conta.depositar(valor)) {
            mostrarMensagem("Depósito realizado.\nNovo saldo: "
                    + MOEDA.format(conta.getSaldo()));
        } else {
            mostrarMensagem("Valor de depósito inválido.");
        }
    }

    private static void realizarSaque(ContaBancaria conta) {
        Double valor = lerDouble("Informe o valor do saque:");
        if (valor == null) {
            return;
        }

        if (conta.sacar(valor)) {
            mostrarMensagem("Saque realizado.\nNovo saldo: "
                    + MOEDA.format(conta.getSaldo()));
        } else {
            mostrarMensagem("Valor inválido ou saldo insuficiente.");
        }
    }

    private static Integer lerInteiro(String mensagem) {
        while (true) {
            String entrada = JOptionPane.showInputDialog(mensagem);
            if (entrada == null) {
                return null;
            }

            try {
                return Integer.valueOf(entrada.trim());
            } catch (NumberFormatException e) {
                mostrarMensagem("Digite um número inteiro válido.");
            }
        }
    }

    private static Double lerDouble(String mensagem) {
        while (true) {
            String entrada = JOptionPane.showInputDialog(mensagem);
            if (entrada == null) {
                return null;
            }

            try {
                return Double.valueOf(entrada.trim().replace(',', '.'));
            } catch (NumberFormatException e) {
                mostrarMensagem("Digite um valor numérico válido.");
            }
        }
    }

    private static String lerTexto(String mensagem) {
        while (true) {
            String entrada = JOptionPane.showInputDialog(mensagem);
            if (entrada == null) {
                return null;
            }

            if (!entrada.trim().isEmpty()) {
                return entrada.trim();
            }

            mostrarMensagem("O nome do titular não pode ficar vazio.");
        }
    }

    private static void mostrarMensagem(String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem);
    }
}
