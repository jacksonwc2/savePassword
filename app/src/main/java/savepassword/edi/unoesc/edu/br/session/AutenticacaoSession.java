package savepassword.edi.unoesc.edu.br.session;

/**
 * Classe utilitaria para guardar dados do usuario
 * e da sessao de login atual
 */
public final class AutenticacaoSession {

    private static Integer codigoUsuarioLogado;

    private static final AutenticacaoSession instance = new AutenticacaoSession();

    private AutenticacaoSession() {

    }

    public static Integer getCodigoUsuarioLogado() {
        return codigoUsuarioLogado;
    }

    public static void setCodigoUsuarioLogado(Integer codigo) {
        codigoUsuarioLogado = codigo;
    }

    public static AutenticacaoSession getInstance(){
        return instance;
    }

}
