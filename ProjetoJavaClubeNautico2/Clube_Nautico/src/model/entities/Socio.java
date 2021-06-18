package model.entities;

import java.io.Serializable;
import java.time.LocalDate;

public class Socio implements Serializable {

    private static final long serialVersionUID = 1L;

    private String codigoSocio;
    private Integer idSocio;
    private String nome;
    private int numeroContribuinte;
    private int anoNascimento;
    private int numeroAulas;
    private EncarregadoEducacao encEdu;
    private Mensalidade mensalidade;
    private Dirigente dirigente;
    private TipoSocio tipoSocio;
    private static int idadeMaximaMenor = 17;
    private static int idadeMaximaAdulto = 59;
    private static int numAulasReferencia = 4;

    public Socio(String nome, int numeroContribuinte, int anoNascimento, int numeroAulas, EncarregadoEducacao encEdu, Mensalidade mensalidade, Dirigente dirigente, TipoSocio tipoSocio) {
        this.nome = nome;
        this.numeroContribuinte = numeroContribuinte;
        this.anoNascimento = anoNascimento;
        this.numeroAulas = numeroAulas;
        this.encEdu = encEdu;
        this.mensalidade = mensalidade;
        this.dirigente = dirigente;
        this.tipoSocio = tipoSocio;
    }

    public Socio(String nome, int numeroContribuinte, int anoNascimento, int numeroAulas, Mensalidade mensalidade, Dirigente dirigente, TipoSocio tipoSocio) {
        this(nome, numeroContribuinte, anoNascimento, numeroAulas, null, mensalidade, dirigente, tipoSocio);

    }

    public Socio() {
    }

    public Integer getIdSocio() {
        return idSocio;
    }

    public void setIdSocio(Integer idSocio) {
        this.idSocio = idSocio;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNumeroContribuinte() {
        return numeroContribuinte;
    }

    public void setNumeroContribuinte(int numeroContribuinte) {
        this.numeroContribuinte = numeroContribuinte;
    }

    public int getAnoNascimento() {
        return anoNascimento;
    }

    public void setAnoNascimento(int anoNascimento) {
        this.anoNascimento = anoNascimento;
    }

    public int getNumeroAulas() {
        return numeroAulas;
    }

    public void setNumeroAulas(int numeroAulas) {
        this.numeroAulas = numeroAulas;
    }

    public EncarregadoEducacao getEncEdu() {
        return encEdu;
    }

    public void setEncEdu(EncarregadoEducacao encEdu) {
        this.encEdu = encEdu;
    }

    public Mensalidade getMensalidade() {
        return mensalidade;
    }

    public void setMensalidade(Mensalidade mensalidade) {
        this.mensalidade = mensalidade;
    }

    public Dirigente getDirigente() {
        return dirigente;
    }

    public void setDirigente(Dirigente dirigente) {
        this.dirigente = dirigente;
    }

    public TipoSocio getTipoSocio() {
        return tipoSocio;
    }

    public void setTipoSocio(TipoSocio tipoSocio) {
        this.tipoSocio = tipoSocio;
    }

    public String getCodigoSocio() {
        return codigoSocio;
    }

    public void setCodigoSocio(String codigoSocio) {
        this.codigoSocio = codigoSocio;
    }

    public static int getIdadeMaximaMenor() {
        return idadeMaximaMenor;
    }

    public static void setIdadeMaximaMenor(int idadeMaximaMenor) {
        Socio.idadeMaximaMenor = idadeMaximaMenor;
    }

    public static int getIdadeMaximaAdulto() {
        return idadeMaximaAdulto;
    }

    public static void setIdadeMaximaAdulto(int idadeMaximaAdulto) {
        Socio.idadeMaximaAdulto = idadeMaximaAdulto;
    }

    public static int getNumAulasReferencia() {
        return numAulasReferencia;
    }

    public static void setNumAulasReferencia(int numAulasReferencia) {
        Socio.numAulasReferencia = numAulasReferencia;
    }

    
    public static double calculoMensalidade(Socio pessoa) {
        LocalDate data = LocalDate.now();
        int ano = data.getYear();
        int idade = ano - pessoa.getAnoNascimento();

        // VERIFICA SE E DIRIGENTE
        if (pessoa.getDirigente().getIdDirigente().equals(2)) {
            return 0.0;

            //CALCULO MENSALIDADE MENOR E ADULTO
        } else if (idade <= Socio.getIdadeMaximaAdulto()) {
            //SE SOCIO FOR MENOR
            if (idade <= Socio.getIdadeMaximaMenor() && pessoa.getNumeroAulas() <= Socio.getNumAulasReferencia()) {
                return pessoa.getMensalidade().getValorReferencia()
                        - (pessoa.getMensalidade().getValorReferencia() * pessoa.getMensalidade().getDesconto().getPercentagemDesconto());
                //SE O SOCIO FOR ADULTO
            } else {
                if (pessoa.getNumeroAulas() <= Socio.getNumAulasReferencia()) {
                    return pessoa.getMensalidade().getValorReferencia();

                } else {
                    //CALCULO SEM O DESCONTO
                    double totalSemDesconto = pessoa.getMensalidade().getValorReferencia()
                            + ((pessoa.getNumeroAulas() - Socio.getNumAulasReferencia()) * pessoa.getMensalidade().getAulas().getPrecoAula());
                    //CALCULO DO DESCONTO
                    double totalDesconto = totalSemDesconto * pessoa.getMensalidade().getDesconto().getPercentagemDesconto();
                    return totalSemDesconto - totalDesconto;
                }
            }
            //CALCULO MENSALIDADE SENIOR        
        } else {
            int resto = idade % 10;
            int decadaIdade = idade - resto;
            //CALCULO DO DESCONTO
            double totalDesconto = Math.round(pessoa.getMensalidade().getValorReferencia()
                    * (decadaIdade * (0.1 * pessoa.getMensalidade().getDesconto().getPercentagemDesconto())));

            return pessoa.getMensalidade().getValorReferencia() - totalDesconto;
        }
    }

    @Override
    public String toString() {
        if (encEdu.getNome() == null) {
            return "|==================================|"
                    +"\n| Codigo Identificador: " + codigoSocio
                           + "\n| Nome: " + nome
                           + "\n| AnoNascimento: " + anoNascimento
                           + "\n| Numero Contribuinte: " + numeroContribuinte
                           + "\n| Numero de Aulas: " + numeroAulas
                           + "\n| Mensalidade a pagar: "+ Socio.calculoMensalidade(this)
                           + dirigente
                           + "\n|==================================|"      
                           +"\n";
        } else {
            return "|==================================|"
                     +"\n| Codigo Identificador: " + codigoSocio
                           + "\n| Nome: " + nome
                           + "\n| AnoNascimento: " + anoNascimento
                           + "\n| Numero Contribuinte: " + numeroContribuinte
                           + "\n| Numero de Aulas: " + numeroAulas
                           + "\n| Mensalidade a pagar: "+ Socio.calculoMensalidade(this)
                           + encEdu
                           + "\n|==================================|"
                           +"\n";
        }
    }
}
