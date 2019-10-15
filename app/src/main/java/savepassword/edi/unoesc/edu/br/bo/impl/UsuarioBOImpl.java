package savepassword.edi.unoesc.edu.br.bo.impl;

import android.app.Activity;

import java.sql.SQLException;

import savepassword.edi.unoesc.edu.br.bo.UsuarioBO;
import savepassword.edi.unoesc.edu.br.dao.Connection;
import savepassword.edi.unoesc.edu.br.dao.UsuarioDAO;
import savepassword.edi.unoesc.edu.br.dao.impl.UsuarioDAOImpl;
import savepassword.edi.unoesc.edu.br.model.Usuario;

/**
 * Classe BO para validacoes e regras de negocio
 *
 * Descricao dos metodos em sua interface
 */
public class UsuarioBOImpl extends Activity implements UsuarioBO {

    private UsuarioDAO usuarioDAO;
    private Connection connection;

    /**
     * Inicializacao para connection com db e DAO
     */
    public UsuarioBOImpl() {

        try {
            this.connection = new Connection(UsuarioBOImpl.this);
            this.usuarioDAO = new UsuarioDAOImpl(connection.getConnectionSource());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Usuario novoUsuario(Usuario usuario) {
        usuarioDAO.novoUsuario(usuario);
        return usuarioDAO.login(usuario.getLogin(), usuario.getSenha());
    }

    @Override
    public Boolean usuarioExiste(String login) {
        return usuarioDAO.usuarioExiste(login);
    }
}
