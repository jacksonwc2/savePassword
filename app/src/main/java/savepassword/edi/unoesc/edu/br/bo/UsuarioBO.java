package savepassword.edi.unoesc.edu.br.bo;

import savepassword.edi.unoesc.edu.br.model.Usuario;

public interface UsuarioBO {

    /**
     * Metodo que insere no banco de dados um novo usuario
     *
     * @param usuario
     * @return Usuario
     */
    Usuario novoUsuario(Usuario usuario);

    /**
     * Metodo que verifica se o usuario ja existe
     *
     * @param login
     * @return Boolean
     */
    Boolean usuarioExiste(String login);
}
