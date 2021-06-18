package model.dao;

import java.util.List;
import model.entities.EncarregadoEducacao;
import model.entities.Socio;

public interface SocioDao {

    void insert(EncarregadoEducacao encEdu);
    void insertAdultoSenior(Socio socio);
    void insertMenor(Socio socio);
    void update(Socio socio);
    void deleteByID(Integer id);
    
    Socio findById(Integer id);   
    List<Socio> findAll();
    List<EncarregadoEducacao> findAllEncEdu();
    List<EncarregadoEducacao> listEncEdu();
    List<Socio> findByType(Integer id);
}
