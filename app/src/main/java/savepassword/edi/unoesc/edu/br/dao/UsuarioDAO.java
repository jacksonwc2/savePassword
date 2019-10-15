package savepassword.edi.unoesc.edu.br.dao;


import savepassword.edi.unoesc.edu.br.model.Usuario;

public interface UsuarioDAO {

    /**
     * Metodo que insere no banco de dados um novo usuario
     *
     * @param usuario
     * @return Usuario
     */
    Boolean novoUsuario(Usuario usuario);

    /**
     * Metodo que valida login do usuario.
     *
     * @param login
     * @param senha
     * @return Usuario
     */
    Usuario login(String login, String senha);

    /**
     * Metodo que verifica se o usuario ja existe
     *
     * @param login
     * @return Boolean
     */
    Boolean usuarioExiste(String login);

}
