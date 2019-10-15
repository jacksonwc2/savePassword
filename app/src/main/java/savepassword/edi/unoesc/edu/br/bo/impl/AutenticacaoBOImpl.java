package savepassword.edi.unoesc.edu.br.bo.impl;

import android.app.Activity;

import java.sql.SQLException;

import savepassword.edi.unoesc.edu.br.bo.AutenticacaoBO;
import savepassword.edi.unoesc.edu.br.dao.Connection;
import savepassword.edi.unoesc.edu.br.dao.UsuarioDAO;
import savepassword.edi.unoesc.edu.br.dao.impl.UsuarioDAOImpl;
import savepassword.edi.unoesc.edu.br.model.Usuario;
import savepassword.edi.unoesc.edu.br.session.AutenticacaoSession;

/**
 * Classe BO para validacoes e regras de negocio
 *
 * Descricao dos metodos em sua interface
 */
public class AutenticacaoBOImpl extends Activity implements AutenticacaoBO {

    private UsuarioDAO usuarioDAO;
    private Connection connection;

    /**
     * Inicializacao para connection com db e DAO
     */
    public AutenticacaoBOImpl() {

        try {
            this.connection = new Connection(AutenticacaoBOImpl.this);
            this.usuarioDAO = new UsuarioDAOImpl(connection.getConnectionSource());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Boolean login(String login, String senha) {

        Usuario usuario = usuarioDAO.login(login, senha);

        if (usuario != null){
            AutenticacaoSession.getInstance().setCodigoUsuarioLogado(usuario.getId());
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    @Override
    public Boolean sair() {
        AutenticacaoSession.getInstance().setCodigoUsuarioLogado(0);
        return Boolean.TRUE;
    }
}
