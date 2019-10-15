package savepassword.edi.unoesc.edu.br.dao.impl;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import savepassword.edi.unoesc.edu.br.dao.CredencialDAO;
import savepassword.edi.unoesc.edu.br.model.Credencial;

import savepassword.edi.unoesc.edu.br.session.AutenticacaoSession;

/**
 * Classe DAO para interacoes com o db
 * <p>
 * Descricao dos metodos em suas interfaces
 */
public class CredencialDAOImpl extends BaseDaoImpl<Credencial, Integer> implements CredencialDAO {

    public static Dao<Credencial, Integer> genericDAO;

    /**
     * Construtor da classe, responsavel por criar
     * mapeamento para a classe model com ORMLite
     */
    public CredencialDAOImpl(ConnectionSource cs) throws SQLException {

        super(Credencial.class);
        setConnectionSource(cs);
        initialize();

        try {
            genericDAO = DaoManager.createDao(cs, Credencial.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Boolean salvar(Credencial credencial) {

        try {
            genericDAO.create(credencial);
        } catch (SQLException e) {
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

    @Override
    public Boolean editar(Credencial credencial) {

        try {
            genericDAO.update(credencial);
        } catch (SQLException e) {
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

    @Override
    public ArrayList<Credencial> adquirir(String pesquisa) {

        String codigoUsuario = AutenticacaoSession.getInstance().getCodigoUsuarioLogado().toString();
        ArrayList<Credencial> ret = new ArrayList<>();

        try {

            QueryBuilder<Credencial, Integer> queryBuilder = genericDAO.queryBuilder();

            if (pesquisa != null && !pesquisa.trim().isEmpty()) {
                queryBuilder.where().eq("codigoUsuario", codigoUsuario).and().like("descricao", "%" + pesquisa + "%");
            } else {
                queryBuilder.where().eq("codigoUsuario", codigoUsuario);
            }

            ret = (ArrayList<Credencial>) genericDAO.query(queryBuilder.prepare());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }

    @Override
    public Boolean excluir(Integer id) {
        try {
            return genericDAO.deleteById(id) > 0;
        } catch (SQLException e) {
            return Boolean.FALSE;
        }
    }
}
