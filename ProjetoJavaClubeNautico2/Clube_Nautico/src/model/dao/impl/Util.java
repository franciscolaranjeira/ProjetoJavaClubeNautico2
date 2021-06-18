package model.dao.impl;

import java.time.LocalDate;
import java.util.Scanner;
import model.entities.Socio;

public class Util {

    static Scanner sc = new Scanner(System.in);
    static LocalDate data = LocalDate.now();
    static int ano = data.getYear();

    public static boolean validacaoTexto(String input) {
        boolean eTexto;
        if (input.matches("[a-zA-Z\\s\'\"]+")) {
            eTexto = true;
        } else {
            System.out.println("|==================================|");
            System.out.println("| Erro: Não introduziu um nome     |\n"
                    + "| valido, tente  novamente!        |");
            System.out.println("|==================================|");
            eTexto = false;
        }
        return eTexto;
    }

    public static int validaNif(int nif) {
        nif = 0;
        boolean isNumber;
        do {
            if (sc.hasNextInt()) {
                nif = sc.nextInt();
                String number = String.valueOf(nif);
                if (number.length() == 9) {
                    nif = Integer.parseInt(number);
                    isNumber = false;
                } else {
                    System.out.println("|==================================|");
                    System.out.println("| Erro: Numero que introduziu não  |\n"
                            + "| tem 9 digitos, tente novamente!  |");
                    System.out.println("|==================================|");
                    System.out.print("| ");
                    isNumber = true;
                }
            } else {
                System.out.println("|==================================|");
                System.out.println("| Erro: Não introduziu um numero   |\n"
                        + "| valido, tente novamente!         |");
                System.out.println("|==================================|");
                System.out.print("| ");
                isNumber = true;
                sc.next();
            }
        } while (isNumber);
        return nif;
    }

    public static int validarAnoNascimento(int input) {
        input = 0;
        boolean isNumber;
        do {
            if (sc.hasNextInt()) {
                input = sc.nextInt();

                String ano1 = String.valueOf(input);

                if (input < ano - 3 && input >= ano - 120) {
                    input = Integer.parseInt(ano1);
                    isNumber = false;
                } else {
                    System.out.println("|==================================|");
                    System.out.println("| Erro: O ano que inseriu não é    |\n"
                            + "| valido, tente novamente!         |");
                    System.out.println("|==================================|");
                    System.out.print("| ");
                    isNumber = true;
                }

            } else {
                System.out.println("|==================================|");
                System.out.println("| Erro: Não introduziu um numero   |\n"
                        + "| valido, tente novamente!         |");
                System.out.println("|==================================|");
                System.out.print("| ");
                isNumber = true;
                sc.next();
            }
        } while (isNumber);
        return input;
    }

    public static int valicacaoInteiro(int input) {
        input = 0;
        boolean isNumber;
        do {
            if (sc.hasNextInt()) {
                input = sc.nextInt();
                isNumber = true;
            } else {
                System.out.println("|==================================|");
                System.out.println("| Erro: Não introduziu um numero   |\n"
                        + "| valido, tente novamente!         |");
                System.out.println("|==================================|");
                System.out.print("| ");
                isNumber = false;
                sc.next();
            }
        } while (!(isNumber));
        return input;
    }

    public static boolean validacaoDirigente(String input) {
        boolean eDirigente = false;

        if (input.matches("[a-zA-Z\\s\'\"]+")) {
            switch (input) {
                case "s":
                    eDirigente = true;
                    break;
                case "n":
                    eDirigente = true;
                    break;
                default:
                    System.out.println("|==================================|");
                    System.out.println("| Erro: Insira (S) para dirigente  |\n"
                            + "| ou (N) se não for dirigente!     |");
                    System.out.println("|==================================|");
                    System.out.print("| ");

                    break;
            }
        } else {
            System.out.println("|==================================|");
            System.out.println("| Erro: Não introduziu um caracter |\n"
                    + "| valido, tente novamente!         |");
            System.out.println("|==================================|");
            System.out.print("| ");
        }
        return eDirigente;
    }

    public static String validacaoDirigente2(String input) {
        switch (input) {
            case "s":
                input = "true";
                break;
            case "n":
                input = "false";
                break;
        }
        return input;
    }

    public static double calculoPercentagem(double somaTipo, double total) {
        double soma = Math.round(somaTipo * 100 / total);
        return soma;
    }

    public static void mostrarTodasMensalidades(Socio socio) {
        System.out.println("|==================================|");
        System.out.println("| Nome: " + socio.getNome() + "\n"
                + "| Numero Contribuinte: " + socio.getNumeroContribuinte() + "\n"
                + "| Valor a Pagar: " + Socio.calculoMensalidade(socio));
        System.out.println("|==================================|");
    }
}
