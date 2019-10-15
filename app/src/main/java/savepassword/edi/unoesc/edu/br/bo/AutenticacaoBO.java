package savepassword.edi.unoesc.edu.br.bo;

public interface AutenticacaoBO {

    /**
     * Metodo que valida login e cria uma sessao para o usuario.
     *
     * @param login
     * @param senha
     * @return Boolean
     */
    Boolean login(String login, String senha);

    /**
     * Metodo que encessa a sessao do usuario
     *
     * @return Boolean
     */
    Boolean sair();

}
