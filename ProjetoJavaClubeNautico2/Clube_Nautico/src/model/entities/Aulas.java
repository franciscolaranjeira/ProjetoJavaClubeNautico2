package model.entities;

public class Aulas {
    private Integer idAulas;
    private double precoAula;
   
  
    public Aulas() {
    } 

    public Integer getIdAulas() {
        return idAulas;
    }

    public void setIdAulas(Integer idAulas) {
        this.idAulas = idAulas;
    }

    public double getPrecoAula() {
        return precoAula;
    }

    public void setPrecoAula(double precoAula) {
        this.precoAula = precoAula;
    }

    @Override
    public String toString() {
        return "\n\n  --Aulas--"
                + "\n| Preco Aula: " + precoAula ;
    } 
}
