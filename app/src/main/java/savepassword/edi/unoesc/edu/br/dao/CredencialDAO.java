package savepassword.edi.unoesc.edu.br.dao;

import java.util.ArrayList;
import java.util.List;

import savepassword.edi.unoesc.edu.br.model.Credencial;

public interface CredencialDAO {

    /**
     * Metodo que insere no banco de dados uma nova credencial
     *
     * @param credencial
     * @return Boolean
     */
    Boolean salvar(Credencial credencial);

    /**
     * Metodo que edita valores de uma credencial
     *
     * @param credencial
     * @return Boolean
     */
    Boolean editar(Credencial credencial);

    /**
     * Metodo que adquire a lista de credenciais de acordo com filtro.
     *
     * @param pesquisa
     * @return List<Credencial>
     */
    ArrayList<Credencial> adquirir(String pesquisa);

    /**
     * Metodo que exclui uma credencial
     * @param id
     * @return Boolean
     */
    Boolean excluir(Integer id);

}
