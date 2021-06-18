package model.dao.impl;

import db.DB;
import db.DbException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.dao.SocioDao;
import model.entities.Aulas;
import model.entities.Desconto;
import model.entities.Dirigente;
import model.entities.EncarregadoEducacao;
import model.entities.Mensalidade;
import model.entities.Socio;

public class SocioDaoJDBC implements SocioDao {

    private Connection conn;

    public SocioDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    public SocioDaoJDBC() {

    }

    /*============================================================================*/
    //INSERIR NOVO ENCARREGADO
    @Override
    public void insert(EncarregadoEducacao enc) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO encarregado_educacao "
                    + "(nome, telefone) "
                    + "VALUES "
                    + "(?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setString(1, enc.getNome());
            st.setInt(2, enc.getTelefone());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    enc.setIdEncarregadoEducacao(id);
                }
            } else {
                throw new DbException("Unexpected error! No rows affected!");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }

    }

    /*============================================================================*/
    //INSERIR NOVO SOCIO
    @Override
    public void insertAdultoSenior(Socio socio) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                    "INSERT INTO socio "
                    + "(id_socio, nome, numero_contribuinte, ano_nascimento, numero_aulas, encarregado_educacao_id, dirigente_id, tipo_socio_id, mensalidade_id) "
                    + "VALUES "
                    + "(NULL, ?, ?, ?, ?, NULL, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setString(1, socio.getNome());
            st.setInt(2, socio.getNumeroContribuinte());
            st.setInt(3, socio.getAnoNascimento());
            st.setInt(4, socio.getNumeroAulas());
            st.setInt(5, socio.getDirigente().getIdDirigente());
            st.setInt(6, socio.getTipoSocio().getId());
            st.setInt(7, socio.getMensalidade().getIdMensalidade());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    socio.setIdSocio(id);
                }
                DB.closeResultSet(rs);
            } else {
                throw new DbException("Unexpected error! No rows affected!");
            }
        } catch (SQLException ex) {
            throw new DbException(ex.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    /*============================================================================*/
    //INSERIR MENOR
    @Override
    public void insertMenor(Socio socio) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                    "INSERT INTO socio "
                    + "(id_socio, nome, numero_contribuinte, ano_nascimento, numero_aulas , mensalidade_id, "
                    + "dirigente_id, encarregado_educacao_id ,tipo_socio_id) "
                    + "VALUES "
                    + "(NULL, ?, ?, ?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setString(1, socio.getNome());
            st.setInt(2, socio.getNumeroContribuinte());
            st.setInt(3, socio.getAnoNascimento());
            st.setInt(4, socio.getNumeroAulas());
            st.setInt(5, socio.getMensalidade().getIdMensalidade());
            st.setInt(6, socio.getDirigente().getIdDirigente());
            st.setInt(7, socio.getEncEdu().getIdEncarregadoEducacao());
            st.setInt(8, socio.getTipoSocio().getId());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    socio.setIdSocio(id);
                }
                DB.closeResultSet(rs);
            } else {
                throw new DbException("Unexpected error! No rows affected!");
            }
        } catch (SQLException ex) {
            throw new DbException(ex.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    /*============================================================================*/
    //UPDATE AO SOCIO
    @Override
    public void update(Socio socio) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                    "UPDATE socio "
                    + "SET nome = ?, numero_contribuinte = ?, ano_nascimento = ?, numero_aulas = ?, tipo_socio_id = ?, mensalidade_id = ? "
                    + "WHERE id_socio = ?");

            st.setString(1, socio.getNome());
            st.setInt(2, socio.getNumeroContribuinte());
            st.setInt(3, socio.getAnoNascimento());
            st.setInt(4, socio.getNumeroAulas());
            st.setInt(5, socio.getTipoSocio().getId());
            st.setInt(6, socio.getMensalidade().getIdMensalidade());
            st.setInt(7, socio.getIdSocio());

            st.executeUpdate();

        } catch (SQLException ex) {
            throw new DbException(ex.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    /*============================================================================*/
    //ELIMINAR POR ID
    @Override
    public void deleteByID(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM socio WHERE id_socio = ?");

            st.setInt(1, id);

            int rows = st.executeUpdate();

            if (rows == 0) {
                throw new DbException("Unexpected error! No rows affected!");
            }
        } catch (SQLException ex) {
            throw new DbException(ex.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    /*============================================================================*/
    //ENCONTRAR SOCIO POR ID
    @Override
    public Socio findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "SELECT socio.*, CONCAT(tipo_socio.tipo_socio, '-', socio.id_socio) AS codigo, encarregado_educacao.nome AS nome_encEdu, "
                    + "encarregado_educacao.telefone, "
                    + "tipo_socio.*, "
                    + "dirigente.*, "
                    + "mensalidade.*, "
                    + "desconto.*, "
                    + "aula.* "
                    + "FROM socio LEFT JOIN encarregado_educacao "
                    + "ON socio.encarregado_educacao_id = encarregado_educacao.id_encarregado_educacao "
                    + "LEFT JOIN tipo_socio "
                    + "ON socio.tipo_socio_id = tipo_socio.id_tipo_socio "
                    + "LEFT JOIN dirigente "
                    + "ON socio.dirigente_id = dirigente.id_dirigente "
                    + "LEFT JOIN mensalidade "
                    + "ON socio.mensalidade_id = mensalidade.id_mensalidade "
                    + "LEFT JOIN aula "
                    + "ON mensalidade.aula_id = aula.id_aula "
                    + "LEFT JOIN desconto "
                    + "ON mensalidade.desconto_id = desconto.id_desconto "
                    + "WHERE socio.id_socio = ? ");

            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                Aulas aulas = instantiateAulas(rs);
                Dirigente dirigente = instantiateDirigente(rs);
                EncarregadoEducacao encEdu = instantiateEncarregadoEducacao(rs);
                Desconto desconto = instantiateDesconto(rs);
                Mensalidade mens = instantiateMensalidade(rs, desconto, aulas);
                Socio socio = instantiateSocio(rs, mens, dirigente, encEdu);
                return socio;
            }
            return null;
        } catch (SQLException ex) {
            throw new DbException(ex.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    /*============================================================================*/
    //ENCONTRAR TODOS OS SOCIOS
    @Override
    public List<Socio> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "SELECT socio.*,CONCAT(tipo_socio.tipo_socio, '-', socio.id_socio) AS codigo, encarregado_educacao.nome AS nome_encEdu, encarregado_educacao.telefone, "
                    + "dirigente.*, "
                    + "tipo_socio.*, "
                    + "mensalidade.*, "
                    + "desconto.*, "
                    + "aula.* "
                    + "FROM socio LEFT JOIN encarregado_educacao "
                    + "ON socio.encarregado_educacao_id = encarregado_educacao.id_encarregado_educacao "
                    + "LEFT JOIN tipo_socio "
                    + "ON socio.tipo_socio_id = tipo_socio.id_tipo_socio "
                    + "LEFT JOIN dirigente "
                    + "ON socio.dirigente_id = dirigente.id_dirigente "
                    + "LEFT JOIN mensalidade "
                    + "ON socio.mensalidade_id = mensalidade.id_mensalidade "
                    + "LEFT JOIN aula "
                    + "ON mensalidade.aula_id = aula.id_aula "
                    + "LEFT JOIN desconto "
                    + "ON mensalidade.desconto_id = desconto.id_desconto "
                    + "ORDER BY nome");

            rs = st.executeQuery();

            List<Socio> list = new ArrayList<>();

            while (rs.next()) {
                EncarregadoEducacao enc = instantiateEncarregadoEducacao(rs);
                Dirigente dirigente = instantiateDirigente(rs);
                Aulas aulas = instantiateAulas(rs);
                Desconto desconto = instantiateDesconto(rs);
                Mensalidade mens = instantiateMensalidade(rs, desconto, aulas);
                Socio socio = instantiateSocio(rs, mens, dirigente, enc);
                list.add(socio);
            }
            return list;

        } catch (SQLException ex) {
            throw new DbException(ex.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    /*============================================================================*/
    //ENCONTRAR POR TIPO DE SOCIO
    @Override
    public List<Socio> findByType(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "SELECT socio.*,CONCAT(tipo_socio.tipo_socio, '-', socio.id_socio) AS codigo, encarregado_educacao.nome AS nome_encEdu, encarregado_educacao.telefone, "
                    + "dirigente.*, "
                    + "tipo_socio.*, "
                    + "mensalidade.*, "
                    + "desconto.*, "
                    + "aula.* "
                    + "FROM socio LEFT JOIN encarregado_educacao "
                    + "ON socio.encarregado_educacao_id = encarregado_educacao.id_encarregado_educacao "
                    + "LEFT JOIN tipo_socio "
                    + "ON socio.tipo_socio_id = tipo_socio.id_tipo_socio "
                    + "LEFT JOIN dirigente "
                    + "ON socio.dirigente_id = dirigente.id_dirigente "
                    + "LEFT JOIN mensalidade "
                    + "ON socio.mensalidade_id = mensalidade.id_mensalidade "
                    + "LEFT JOIN aula "
                    + "ON mensalidade.aula_id = aula.id_aula "
                    + "LEFT JOIN desconto "
                    + "ON mensalidade.desconto_id = desconto.id_desconto "
                    + "WHERE socio.tipo_socio_id = ?");

            st.setInt(1, id);
            rs = st.executeQuery();
            List<Socio> list = new ArrayList<>();

            while (rs.next()) {
                Aulas aulas = instantiateAulas(rs);
                Dirigente dirigente = instantiateDirigente(rs);
                EncarregadoEducacao encEdu = instantiateEncarregadoEducacao(rs);
                Desconto desconto = instantiateDesconto(rs);
                Mensalidade mens = instantiateMensalidade(rs, desconto, aulas);
                Socio socio = instantiateSocio(rs, mens, dirigente, encEdu);
                list.add(socio);
            }
            return list;

        } catch (SQLException ex) {
            throw new DbException(ex.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    /*============================================================================*/
    //ENCONTRAR TODOS OS ENCAREGADOS
    @Override
    public List<EncarregadoEducacao> listEncEdu() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM encarregado_educacao");
            rs = st.executeQuery();

            List<EncarregadoEducacao> list = new ArrayList<>();

            while (rs.next()) {
                EncarregadoEducacao enc = instantiateEncarregadoEducacao2(rs);
                list.add(enc);
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    /*============================================================================*/
    //ENCONTRAR TODOS OS ENCAREGADOS
    @Override
    public List<EncarregadoEducacao> findAllEncEdu() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT socio.*,CONCAT(tipo_socio.tipo_socio, '-', socio.id_socio) AS codigo, encarregado_educacao.nome AS nome_encEdu, encarregado_educacao.telefone, "
                    + "dirigente.*, "
                    + "tipo_socio.*, "
                    + "mensalidade.*, "
                    + "desconto.*, "
                    + "aula.* "
                    + "FROM socio LEFT JOIN encarregado_educacao "
                    + "ON socio.encarregado_educacao_id = encarregado_educacao.id_encarregado_educacao "
                    + "LEFT JOIN tipo_socio "
                    + "ON socio.tipo_socio_id = tipo_socio.id_tipo_socio "
                    + "LEFT JOIN dirigente "
                    + "ON socio.dirigente_id = dirigente.id_dirigente "
                    + "LEFT JOIN mensalidade "
                    + "ON socio.mensalidade_id = mensalidade.id_mensalidade "
                    + "LEFT JOIN aula "
                    + "ON mensalidade.aula_id = aula.id_aula "
                    + "LEFT JOIN desconto "
                    + "ON mensalidade.desconto_id = desconto.id_desconto "
                    + "ORDER BY nome");

            rs = st.executeQuery();

            List<EncarregadoEducacao> list = new ArrayList<>();

            while (rs.next()) {
                EncarregadoEducacao enc = instantiateEncarregadoEducacao(rs);
                list.add(enc);
            }

            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    /*============================================================================*/
    //INSTANCIACOES
    private Aulas instantiateAulas(ResultSet rs) throws SQLException {
        Aulas aulas = new Aulas();
        aulas.setIdAulas(rs.getInt("id_aula"));
        aulas.setPrecoAula(rs.getDouble("preco_aula"));

        return aulas;
    }

    private Dirigente instantiateDirigente(ResultSet rs) throws SQLException {
        Dirigente dirigente = new Dirigente();
        dirigente.setIdDirigente(rs.getInt("dirigente_id"));
        dirigente.setSocioDirigente(rs.getBoolean("socio_dirigente"));

        return dirigente;
    }

    private Desconto instantiateDesconto(ResultSet rs) throws SQLException {
        Desconto desc = new Desconto();
        desc.setIdDesconto(rs.getInt("id_desconto"));
        desc.setPercentagemDesconto(rs.getDouble("percentagem_desconto"));

        return desc;
    }

    private Mensalidade instantiateMensalidade(ResultSet rs, Desconto desconto, Aulas aulas) throws SQLException {
        Mensalidade mensal = new Mensalidade();
        mensal.setIdMensalidade(rs.getInt("mensalidade_id"));
        mensal.setDesconto(desconto);
        mensal.setAulas(aulas);
        mensal.setValorReferencia(rs.getDouble("valor_referencia"));

        return mensal;
    }

    private EncarregadoEducacao instantiateEncarregadoEducacao(ResultSet rs) throws SQLException {
        EncarregadoEducacao encEdu = new EncarregadoEducacao();
        encEdu.setIdEncarregadoEducacao(rs.getInt("encarregado_educacao_id"));
        encEdu.setNome(rs.getString("nome_encEdu"));
        encEdu.setTelefone(rs.getInt("telefone"));

        return encEdu;
    }

    private EncarregadoEducacao instantiateEncarregadoEducacao2(ResultSet rs) throws SQLException {
        EncarregadoEducacao encEdu = new EncarregadoEducacao();
        encEdu.setNome(rs.getString("nome"));

        return encEdu;
    }

    private Socio instantiateSocio(ResultSet rs, Mensalidade mens, Dirigente dirigente, EncarregadoEducacao encEdu) throws SQLException {
        Socio socio = new Socio();
        socio.setIdSocio(rs.getInt("id_socio"));
        socio.setCodigoSocio(rs.getString("codigo"));
        socio.setNome(rs.getString("nome"));
        socio.setNumeroContribuinte(rs.getInt("numero_contribuinte"));
        socio.setAnoNascimento(rs.getInt("ano_nascimento"));
        socio.setNumeroAulas(rs.getInt("numero_aulas"));
        socio.setMensalidade(mens);
        socio.setDirigente(dirigente);
        socio.setEncEdu(encEdu);

        return socio;
    }
    /*============================================================================*/
}
