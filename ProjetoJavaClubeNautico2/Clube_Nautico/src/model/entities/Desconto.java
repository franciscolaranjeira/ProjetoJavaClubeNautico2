package model.entities;

public class Desconto {

    private Integer idDesconto;
    private double percentagemDesconto;

    public Desconto() {
    }


    public Integer getIdDesconto() {
        return idDesconto;
    }

    public void setIdDesconto(Integer idDesconto) {
        this.idDesconto = idDesconto;
    }

    public double getPercentagemDesconto() {
        return percentagemDesconto;
    }

    public void setPercentagemDesconto(double percentagemDesconto) {
        this.percentagemDesconto = percentagemDesconto;
    }

    @Override
    public String toString() {
        return "\n\n  --Deconto--"
                + "\n| ValorDesconto: " + percentagemDesconto;
    }

}
