package model.entities;

public class Mensalidade {

    private Integer idMensalidade;
    private double valorReferencia;
    private Desconto desconto;
    private Aulas aulas;


    public Mensalidade() {

    }

    public Mensalidade(TipoSocio tipoSocio) {
        switch (tipoSocio) {
            case SMenor:
                idMensalidade = 1;
                break;
            case SAdulto:
                idMensalidade = 2;
                break;
            case SSenior:
                idMensalidade = 3;
                break;
        } 
    }

    public Integer getIdMensalidade() {
        return idMensalidade;
    }

    public void setIdMensalidade(Integer idMensalidade) {
        this.idMensalidade = idMensalidade;
    }

    public double getValorReferencia() {
        return valorReferencia;
    }

    public void setValorReferencia(double valorReferencia) {
        this.valorReferencia = valorReferencia;
    }

    public Aulas getAulas() {
        return aulas;
    }

    public void setAulas(Aulas aulas) {
        this.aulas = aulas;
    }
    
    public Desconto getDesconto() {
        return desconto;
    }

    public void setDesconto(Desconto desconto) {
        this.desconto = desconto;
    }

    @Override
    public String toString() {
        return "\n\n  --Mensalidade--"
                + "\n| Valor de Referencia: " + valorReferencia
                + desconto
                + aulas;
    }
}
