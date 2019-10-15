package savepassword.edi.unoesc.edu.br.dao.impl;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import savepassword.edi.unoesc.edu.br.dao.UsuarioDAO;
import savepassword.edi.unoesc.edu.br.model.Usuario;

/**
 * Classe DAO para interacoes com o db
 *
 * Descricao dos metodos em suas interfaces
 */
public class UsuarioDAOImpl extends BaseDaoImpl<Usuario, Integer> implements UsuarioDAO {

    public static Dao<Usuario, Integer> genericDAO;

    /**
     * Construtor da classe, responsavel por criar
     * mapeamento para a classe model com ORMLite
     */
    public UsuarioDAOImpl(ConnectionSource cs) throws SQLException {

        super(Usuario.class);
        setConnectionSource(cs);
        initialize();

        try {
            genericDAO = DaoManager.createDao(cs, Usuario.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Boolean novoUsuario(Usuario usuario) {

        try {
            return genericDAO.create(usuario) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Boolean.FALSE;
    }

    @Override
    public Usuario login(String login, String senha) {

        List<Usuario> listUsuario = new ArrayList<>();

        //busca registro com login e senha parametrizados
        try {
            QueryBuilder<Usuario, Integer> queryBuilder = genericDAO.queryBuilder();
            queryBuilder.where().eq("login", login).and().eq("senha", senha);
            queryBuilder.limit(1L);

            listUsuario = genericDAO.query(queryBuilder.prepare());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return !listUsuario.isEmpty() ? listUsuario.get(0) : null;
    }

    @Override
    public Boolean usuarioExiste(String login) {

        List<Usuario> listUsuario = new ArrayList<>();

        //busca registro com login parametrizados
        try {
            QueryBuilder<Usuario, Integer> queryBuilder = genericDAO.queryBuilder();
            queryBuilder.where().eq("login", login);
            queryBuilder.limit(1L);

            listUsuario = genericDAO.query(queryBuilder.prepare());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return !listUsuario.isEmpty();
    }
}
