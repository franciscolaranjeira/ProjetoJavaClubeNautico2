package model.entities;

public enum TipoSocio {

    SMenor(1),
    SAdulto(2),
    SSenior(3),;

    private final Integer id;
    
    TipoSocio(int opcao) {
        id = opcao;
    }

    public Integer getId() {
        return id;       
    }
}
