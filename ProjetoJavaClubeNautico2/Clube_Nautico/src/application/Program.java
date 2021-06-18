package application;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.dao.DaoFactory;
import model.dao.SocioDao;
import model.dao.impl.Util;
import model.entities.Aulas;
import model.entities.Desconto;
import model.entities.Dirigente;
import model.entities.Socio;
import model.entities.EncarregadoEducacao;
import model.entities.Mensalidade;
import model.entities.TipoSocio;

public class Program {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Scanner input = new Scanner(System.in);

        SocioDao socioDao = DaoFactory.criarSocioDao();// assim não conhece a implementação só conhece a interface
        LocalDate data = LocalDate.now();
        int ano = data.getYear();

        int opcao = 0;

        do {
            System.out.println();
            System.out.println("|==================================|");
            System.out.println("|     MENSALIDADE CLUBE NAUTICO    |");
            System.out.println("|==================================|");
            System.out.println("|  1 - CRIAR SOCIO                 |");
            System.out.println("|  2 - LISTA DE SOCIOS             |");
            System.out.println("|  3 - INFORMAÇÕES DO SOCIO        |");
            System.out.println("|  4 - ALTERAR SOCIO               |");
            System.out.println("|  5 - ELIMINAR SOCIO              |");
            System.out.println("|  6 - MENSALIDADE POR ID          |");
            System.out.println("|  7 - FATURAÇÃO TOTAL             |");
            System.out.println("|  8 - FATURAÇÃO POR TIPO SOCIO    |");
            System.out.println("|  9 - PESO FATURAÇÃO              |");
            System.out.println("|  10- LISTA ENCARREGADOS EDUCAÇÃO |");
            System.out.println("|==================================|");
            System.out.print("|     Opcão: ");
            opcao = Util.valicacaoInteiro(opcao);
            System.out.println("|==================================|");
            /*=======================================================================================================================*/
            switch (opcao) {
                case 1:
                    //NOME
                    System.out.println("\n");
                    System.out.println("|==================================|");
                    System.out.println("|            NOVO SOCIO            |");
                    System.out.println("|==================================|");

                    String nome = "";
                    do {
                        System.out.println("| Introduza o nome: ");
                        System.out.print("| ");
                        nome = input.nextLine();
                    } while (!(Util.validacaoTexto(nome)));
                    System.out.println("|==================================|");
                    /*==================================================================================*/
                    //NUMERO CONTRIBUINTE
                    System.out.println("| Insira o numero de contribuinte:");
                    System.out.print("| ");
                    int numeroContribuinte = 0;
                    numeroContribuinte = Util.validaNif(numeroContribuinte);
                    System.out.println("|==================================|");
                    /*==================================================================================*/
                    //ANO NASCIMENTO
                    System.out.print("| Insira o ano de nascimento: ");
                    int anoNascimento = 0;
                    anoNascimento = Util.validarAnoNascimento(anoNascimento);
                    System.out.println("|==================================|");
                    /*==================================================================================*/
                    //NUMERO DE AULAS
                    System.out.print("| Adicione o numero de aulas: ");
                    int numeroAulas = 0;
                    numeroAulas = Util.valicacaoInteiro(numeroAulas);
                    System.out.println("|==================================|");
                    /*==================================================================================*/
                    //SE FOR MENOR
                    if (ano - anoNascimento <= Socio.getIdadeMaximaMenor()) {
                        //NOME ENCARREGADO EDUCACAO                       
                        String nomeEncEdu = "";
                        do {
                            System.out.println("| Adicione o nome do \n"
                                    + "| encarregado de educacao: ");
                            System.out.print("| ");
                            nomeEncEdu = input.nextLine();
                        } while (!(Util.validacaoTexto(nomeEncEdu)));
                        System.out.println("|==================================|");
                        /*==================================================================================*/
                        System.out.println("| Adicione o numero de telefone \n"
                                + "|  do encarregado de educacao: ");
                        System.out.print("| ");
                        int telefone = 0;
                        telefone = Util.validaNif(telefone);
                        System.out.println("|==================================|");
                        /*==================================================================================*/
                        //INSERIR NA BASE DADOS
                        Dirigente dirigente = new Dirigente(TipoSocio.SMenor, false);
                        Mensalidade mensalidade = new Mensalidade(TipoSocio.SMenor);

                        List<EncarregadoEducacao> encarregados = socioDao.findAllEncEdu();
                        boolean t = false;
                        for (EncarregadoEducacao enc : encarregados) {

                            if (telefone == enc.getTelefone() && nomeEncEdu.equalsIgnoreCase(enc.getNome())) {
                                socioDao.insertMenor(new Socio(nome, numeroContribuinte, anoNascimento, numeroAulas, enc, mensalidade,
                                        dirigente, TipoSocio.SMenor));
                                t = true;
                                break;
                            }
                        }
                        if (t == false) {
                            EncarregadoEducacao encEdu1 = new EncarregadoEducacao(nomeEncEdu, telefone);
                            socioDao.insert(encEdu1);
                            socioDao.insertMenor(new Socio(nome, numeroContribuinte, anoNascimento, numeroAulas, encEdu1, mensalidade,
                                    dirigente, TipoSocio.SMenor));

                        }

                        System.out.println("|      SOCIO MENOR ADICIONADO!     |");
                        System.out.println("|==================================|");
                        break;

                        /*==================================================================================*/
                        //SE FOR ADULTO
                    } else if (ano - anoNascimento < Socio.getIdadeMaximaAdulto()) {
                        //DIRIGENTE
                        String sDirigente = "";
                        do {
                            System.out.print("| É dirigente (S) ou (N): ");
                            sDirigente = input.nextLine();
                        } while (!(Util.validacaoDirigente(sDirigente)));

                        //transforma o s ou n para true ou false
                        sDirigente = Util.validacaoDirigente2(sDirigente);

                        //string para boleano
                        boolean socioDirigente = Boolean.valueOf(sDirigente);

                        System.out.println(socioDirigente);
                        System.out.println("|==================================|");
                        //INSERIR NA BASE DADOS

                        Dirigente dirigente = new Dirigente(TipoSocio.SAdulto, socioDirigente);
                        Mensalidade mensalidade = new Mensalidade(TipoSocio.SAdulto);

                        Socio socio = new Socio(nome, numeroContribuinte, anoNascimento, numeroAulas, mensalidade, dirigente, TipoSocio.SAdulto);
                        socioDao.insertAdultoSenior(socio);

                        System.out.println("|      SOCIO ADULTO ADICIONADO!    |");
                        System.out.println("|==================================|");
                        /*==================================================================================*/
                        //SE FOR SENIOR
                    } else {
                        //DIRIGENTE
                        String sDirigente = "";
                        do {
                            System.out.print("| É dirigente (S) ou (N): ");
                            sDirigente = input.nextLine();
                        } while (!(Util.validacaoDirigente(sDirigente)));
                        boolean socioDirigente = Boolean.getBoolean(sDirigente);
                        System.out.println("|==================================|");
                        /*==================================================================================*/
                        //INSERIR NO ARRAY LIST
                        Dirigente dirigente = new Dirigente(TipoSocio.SSenior, socioDirigente);
                        Mensalidade mensalidade = new Mensalidade(TipoSocio.SSenior);

                        Socio socio = new Socio(nome, numeroContribuinte, anoNascimento, numeroAulas, mensalidade, dirigente, TipoSocio.SSenior);
                        socioDao.insertAdultoSenior(socio);

                        System.out.println("|      SOCIO SENIOR ADICIONADO!    |");
                        System.out.println("|==================================|");
                    }
                    break;
                /*=======================================================================================================================*/
                case 2:
                    System.out.println("\n|==================================|");
                    System.out.println("|          LISTA DE SOCIOS         |");
                    List<Socio> list;
                    list = socioDao.findAll();
                    for (Socio socio1 : list) {
                        System.out.println(socio1);
                    }
                    break;
                /*=======================================================================================================================*/
                case 3:
                    System.out.println("\n|==================================|");
                    System.out.println("|       INFORMAÇÕES DO SOCIO       |");
                    System.out.println("|==================================|");
                    System.out.print("| Qual o ID do Socio: ");
                    int id = sc.nextInt();
                    Socio socio2 = socioDao.findById(id);
                    System.out.println(socio2);

                    System.out.println("|==================================|");

                    break;
                /*=======================================================================================================================*/
                case 4:

                    System.out.println();
                    System.out.println("|==================================|");
                    System.out.println("|         ALTERAR SOCIO           |");
                    System.out.println("|==================================|");
                    //PROCURAR O ID
                    System.out.println("| Qual o ID do Socio: ");
                    System.out.print("| ");
                    id = sc.nextInt();
                    Socio socio3 = socioDao.findById(id);

                    //MUDAR O NOME
                    System.out.println("| Quer mudar o nome? (s) ou (n)");
                    System.out.print("| ");
                    String resp = input.nextLine();
                    if (resp.equalsIgnoreCase("S")) {
                        System.out.println("| Introduza o nome novo: ");
                        System.out.print("| ");
                        nome = input.nextLine();
                        System.out.println("|==================================|");
                        socio3.setNome(nome);
                    }
                    //MUDAR O NUMERO DE CONTRIBUINTE
                    System.out.println("| Quer mudar o numero contribuinte? "
                            + "\n| (s) ou (n)");
                    System.out.print("| ");
                    resp = input.nextLine();
                    if (resp.equalsIgnoreCase("S")) {
                        System.out.println("| Introduza o contribuinte novo: ");
                        System.out.print("| ");
                        numeroContribuinte = sc.nextInt();
                        System.out.println("|==================================|");
                        socio3.setNumeroContribuinte(numeroContribuinte);
                    }
                    //MUDAR O NUMERO DE AULAS
                    System.out.println("| Quer mudar o numero de aulas? "
                            + "\n| (s) ou (n)");
                    System.out.print("| ");
                    resp = input.nextLine();
                    if (resp.equalsIgnoreCase("S")) {
                        System.out.println("| Introduza o numero de aulas: ");
                        System.out.print("| ");
                        numeroAulas = sc.nextInt();
                        System.out.println("|==================================|");
                        socio3.setNumeroAulas(numeroAulas);
                    }
                    //MUDAR O ANO DE NASCIMENTO
                    System.out.println("| Quer mudar o ano de nascimento? "
                            + "\n| (s) ou (n)");
                    System.out.print("| ");
                    resp = input.nextLine();
                    if (resp.equalsIgnoreCase("S")) {
                        System.out.println("| Introduza novo ano nascimento: ");
                        System.out.print("| ");
                        anoNascimento = sc.nextInt();
                        System.out.println("|==================================|");
                            socio3.setAnoNascimento(anoNascimento);
                            socio3.getMensalidade().setAulas(new Aulas());
                            socio3.getMensalidade().setDesconto(new Desconto());

                        if (ano - anoNascimento <= Socio.getIdadeMaximaMenor()) {
                            socio3.setTipoSocio(TipoSocio.SMenor);
                            socio3.setMensalidade(new Mensalidade(TipoSocio.SMenor));

                        } else if (ano - anoNascimento < Socio.getIdadeMaximaAdulto()) {                          
                            socio3.setTipoSocio(TipoSocio.SAdulto);
                            socio3.setMensalidade(new Mensalidade(TipoSocio.SAdulto));
                           
                        } else {                           
                            socio3.setTipoSocio(TipoSocio.SSenior);
                            socio3.setMensalidade(new Mensalidade(TipoSocio.SSenior));
                            
                        }
                        //UPDATE
                        socioDao.update(socio3);
                    }

                    System.out.println("|==================================|");
                    System.out.println("|        ALTERAÇÃO CONCLUIDA       |");
                    System.out.println("|==================================|");
                    break;
                /*=======================================================================================================================*/
                case 5:
                    System.out.println("\n|==================================|");
                    System.out.println("|         ELIMINAR SOCIO           |");
                    System.out.println("|==================================|");
                    System.out.print("| Qual o ID do Socio a eliminar: ");
                    id = sc.nextInt();
                    socioDao.deleteByID(id);
                    System.out.println("|==================================|");
                    System.out.println("|         SOCIO ELIMINADO!         |");
                    System.out.println("|==================================|");
                    break;
                /*=======================================================================================================================*/
                case 6:
                    System.out.println("\n|==================================|");
                    System.out.println("|         MENSALIDADE POR ID       |");
                    System.out.println("|==================================|");
                    System.out.print("| Qual o ID do Socio: ");
                    int id1 = sc.nextInt();
                    Socio socio4 = socioDao.findById(id1);
                    System.out.print("| Valor a pagar: " + Socio.calculoMensalidade(socio4));
                    System.out.println("\n|==================================|");
                    break;
                /*=======================================================================================================================*/
                case 7:
                    list = socioDao.findAll();
                    double soma = 0;
                    for (Socio socio : list) {
                        soma += Socio.calculoMensalidade(socio);
                    }
                    System.out.println("| Mensalidade Total: " + soma);
                    System.out.println("|==================================|");
                    break;
                /*=======================================================================================================================*/
                case 8:
                    int escolha = 0;
                    do {
                        System.out.println();
                        System.out.println("|==================================|");
                        System.out.println("|      FATURAÇÃO POR TIPO SOCIO    |");
                        System.out.println("|==================================|");
                        System.out.println("| 1 - Soma Mensalidade Tipo MENOR  |");
                        System.out.println("| 2 - Soma Mensalidade Tipo ADULTO |");
                        System.out.println("| 3 - Soma Mensalidade Tipo SENIOR |");
                        System.out.println("| 0 - Sair                         |");
                        System.out.println("|==================================|");
                        System.out.print("|   Opção: ");
                        escolha = Util.valicacaoInteiro(opcao);
                        System.out.println("|==================================|");
                        System.out.println();

                        switch (escolha) {
                            case 1:
                                list = socioDao.findByType(1);

                                for (Socio socio5 : list) {
                                    Util.mostrarTodasMensalidades(socio5);
                                }

                                double somaTipoMenor = 0;
                                for (Socio socio : list) {
                                    somaTipoMenor += Socio.calculoMensalidade(socio);
                                }
                                System.out.println("| Mensalidade Total Menores: " + somaTipoMenor);
                                System.out.println("|==================================|");
                                break;

                            case 2:
                                list = socioDao.findByType(2);

                                for (Socio socio5 : list) {
                                    Util.mostrarTodasMensalidades(socio5);
                                }

                                double somaTipoAdulto = 0;
                                for (Socio socio : list) {
                                    somaTipoAdulto += Socio.calculoMensalidade(socio);
                                }
                                System.out.println("| Mensalidade Total Adultos: " + somaTipoAdulto);
                                System.out.println("|==================================|");
                                break;

                            case 3:
                                list = socioDao.findByType(3);

                                for (Socio socio5 : list) {
                                    Util.mostrarTodasMensalidades(socio5);
                                }

                                double somaTipoSenior = 0;
                                for (Socio socio : list) {
                                    somaTipoSenior += Socio.calculoMensalidade(socio);
                                }
                                System.out.println("| Mensalidade Total Seniores: " + somaTipoSenior);
                                System.out.println("|==================================|");
                                break;
                        }
                    } while (escolha != 0);
                    break;

                case 9:
                    List<Socio> listMenores = socioDao.findByType(1);
                    List<Socio> listAdulto = socioDao.findByType(2);
                    List<Socio> listSenior = socioDao.findByType(3);

                    double somaTipoMenor = 0;
                    double somaTipoAdulto = 0;
                    double somaTipoSenior = 0;

                    for (Socio socio : listMenores) {
                        somaTipoMenor += Socio.calculoMensalidade(socio);
                    }

                    for (Socio socio : listAdulto) {
                        somaTipoAdulto += Socio.calculoMensalidade(socio);
                    }

                    for (Socio socio : listSenior) {
                        somaTipoSenior += Socio.calculoMensalidade(socio);
                    }

                    double somaTotal = somaTipoMenor + somaTipoAdulto + somaTipoSenior;

                    double percentagemMenor = Util.calculoPercentagem(somaTipoMenor, somaTotal);
                    double percentagemAdulto = Util.calculoPercentagem(somaTipoAdulto, somaTotal);
                    double percentagemSenior = Util.calculoPercentagem(somaTipoSenior, somaTotal);

                    System.out.println("\n|==================================|");
                    System.out.println("| Percentagem Menor: " + percentagemMenor + "%");
                    System.out.println("| Percentagem Adulto: " + percentagemAdulto + "%");
                    System.out.println("| Percentagem Senior: " + percentagemSenior + "%");
                    System.out.println("|==================================|");
                    break;

                case 10:
                    List<Socio> listaMenores = socioDao.findAll();
                    List<EncarregadoEducacao> listaEncarregados = socioDao.listEncEdu();

                    int menoresACargo = 0;
                    List<String> nomeMenores = new ArrayList<>();

                    for (EncarregadoEducacao encarregado : listaEncarregados) {
                        for (Socio socio : listaMenores) {

                            if (encarregado.getNome().equals(socio.getEncEdu().getNome())) {
                                encarregado.setTotalMenoresACargo(++menoresACargo);
                                nomeMenores.add(socio.getNome());
                                encarregado.setNomesMenores(nomeMenores);
                            }
                        }
                        encarregado.listaEncarregadosEducacao();
                        nomeMenores.clear();
                        menoresACargo = 0;
                    }
                    break;
                /*=======================================================================================================================*/
                default:
                    if (opcao == 0) {

                        System.out.println("|        OPERACAO TERMINADA.       |");
                        System.out.println("|==================================|");
                        break;
                    } else {
                        System.out.println("|          OPCAO INVALIDA.         |");
                        System.out.println("|==================================|");
                        break;
                    }
            }
        } while (opcao != 0);

        sc.close();
    }
}
