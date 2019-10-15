package savepassword.edi.unoesc.edu.br.bo.impl;

import android.app.Activity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import savepassword.edi.unoesc.edu.br.bo.CredencialBO;
import savepassword.edi.unoesc.edu.br.dao.Connection;
import savepassword.edi.unoesc.edu.br.dao.CredencialDAO;
import savepassword.edi.unoesc.edu.br.dao.impl.CredencialDAOImpl;
import savepassword.edi.unoesc.edu.br.model.Credencial;

/**
 * Classe BO para validacoes e regras de negocio
 *
 * Descricao dos metodos em sua interface
 */
public class CredencialBOImpl extends Activity implements CredencialBO {

    private CredencialDAO credencialDAO;
    private Connection connection;

    /**
     * Inicializacao para connection com db e DAO
     */
    public CredencialBOImpl() {

        try {
            this.connection = new Connection(CredencialBOImpl.this);
            this.credencialDAO = new CredencialDAOImpl(connection.getConnectionSource());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Boolean salvar(Credencial credencial) {
        return credencialDAO.salvar(credencial);
    }

    @Override
    public Boolean editar(Credencial credencial) {
        return credencialDAO.editar(credencial);
    }

    @Override
    public ArrayList<Credencial> adquirir(String pesquisa) {
        return credencialDAO.adquirir(pesquisa);
    }

    @Override
    public Boolean excluir(Integer id) {
        return credencialDAO.excluir(id);
    }
}
