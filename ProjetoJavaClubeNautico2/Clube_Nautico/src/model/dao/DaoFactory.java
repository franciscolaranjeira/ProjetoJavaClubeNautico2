package model.dao;

import db.DB;
import model.dao.impl.SocioDaoJDBC;

public class DaoFactory {

    public static SocioDao criarSocioDao() {
        return new SocioDaoJDBC(DB.getConnection());
    }
}
