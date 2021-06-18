package model.entities;

public class Dirigente {
    
    private Integer idDirigente;
    private boolean socioDirigente;

    public Dirigente(TipoSocio tipoSocio, boolean socioDirigente) {
        switch (tipoSocio) {
            case SMenor:
                idDirigente = 1;                
                break;

            case SAdulto:
                if(socioDirigente == true){
                    idDirigente = 2;                   
                }else{
                    idDirigente = 1;
                }
                break;
            case SSenior:               
                if(socioDirigente == true){
                    idDirigente = 2;
                }else{
                    idDirigente = 1;
                }              
                break;
        }
    }

    public Dirigente() {
    }
  
    public Integer getIdDirigente() {
        return idDirigente;
    }

    public void setIdDirigente(Integer idDirigente) {
        this.idDirigente = idDirigente;
    }

    public boolean isSocioDirigente() {
        return socioDirigente;
    }

    public void setSocioDirigente(boolean socioDirigente) {
        this.socioDirigente = socioDirigente;
    }

    @Override
    public String toString() {
        return "\n| SocioDirigente: " + socioDirigente;
    } 
}
