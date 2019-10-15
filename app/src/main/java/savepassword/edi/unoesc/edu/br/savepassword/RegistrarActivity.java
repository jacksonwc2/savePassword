package savepassword.edi.unoesc.edu.br.savepassword;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.sql.SQLException;

import savepassword.edi.unoesc.edu.br.bo.UsuarioBO;
import savepassword.edi.unoesc.edu.br.bo.impl.UsuarioBOImpl;
import savepassword.edi.unoesc.edu.br.dao.Connection;
import savepassword.edi.unoesc.edu.br.dao.impl.UsuarioDAOImpl;
import savepassword.edi.unoesc.edu.br.model.Usuario;
import savepassword.edi.unoesc.edu.br.session.AutenticacaoSession;
import savepassword.edi.unoesc.edu.br.util.Util;

/**
 * Classe controller para eventos da pagina activity_registrar.xml
 */
public class RegistrarActivity extends AppCompatActivity {

    private UsuarioBO usuarioBO;

    private Connection connection;

    //DAO deve ser inicializado no contexto da pagina
    private UsuarioDAOImpl usuarioDAOImpl;

    private Button btnRegistrar;

    private EditText txtLogin;

    private EditText txtSenha;

    private EditText txtConfirmarSenha;

    private EditText txtNomeCompleto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //inicializa e registra pagina
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        //inicializacao componentes e atributos
        this.inicializarComponentes();
        this.inicializarAtributos();

        //esconde actionBar
        getSupportActionBar().hide();

        //Ações do botao registrar
        btnRegistrar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                registrar();
            }

        });
    }

    /**
     * Medoto que inicializa os componentes para
     * controller dos seus eventos
     */
    private void inicializarComponentes(){
        btnRegistrar = findViewById(R.id.btnRegistrar);
        txtLogin = findViewById(R.id.txtLogin);
        txtSenha = findViewById(R.id.txtSenha);
        txtConfirmarSenha = findViewById(R.id.txtConfirmarSenha);
        txtNomeCompleto = findViewById(R.id.txtNomeCompleto);
    }

    /**
     * Metodo que inicializa atributos usados pelo
     * controller da pagina.
     */
    private void inicializarAtributos(){

        usuarioBO = new UsuarioBOImpl();

        /* DAOs que serao utilizados pela página devem ser inicializados
         * no contexto da pagina onde os eventos irão ocorrer, caso contrario
         * a connection com db nao é criada corretamente.
         */
        try {
            connection = new Connection(RegistrarActivity.this);
            usuarioDAOImpl = new UsuarioDAOImpl(connection.getConnectionSource());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Metodo chamado ao clicar no botao registr-se
     * responsavel por criar novo usuario.
     */
    private void registrar(){

        //validacoes para criacao do novo usuario
        if(validarCampos()){

            //captura dos valores inseridos pelo usuario
            String login = txtLogin.getText().toString();
            String senha = txtSenha.getText().toString();
            String nome = txtNomeCompleto.getText().toString();

            Usuario usuario = usuarioBO.novoUsuario(new Usuario(login, senha , nome));

            if (usuario != null){
                AutenticacaoSession.getInstance().setCodigoUsuarioLogado(usuario.getId());
                startActivity(new Intent(RegistrarActivity.this, HomeActivity.class));
                finish();
            }
        }
    }

    /**
     * Metodo que verifica os campos e os valida para verifcar
     * se sao validos os nao
     *
     * @return Boolean
     */
    private Boolean validarCampos(){

        //captura dos valores inseridos pelo usuario
        String login = txtLogin.getText().toString();
        String senha = txtSenha.getText().toString();
        String senhaConfirmar = txtConfirmarSenha.getText().toString();
        String nome = txtNomeCompleto.getText().toString();
        String mensagem = "";

        //validacoes dos campos e regras de negocio
        if (login.isEmpty() || senha.isEmpty() || senhaConfirmar.isEmpty() || nome.isEmpty()){
            mensagem = "Preencha os campos!";
        } else if (senha.length() < 6){
            mensagem = "Senha deve conter no mínimo 6 caracteres!";
        } else if (!senha.equals(senhaConfirmar)) {
            mensagem = "Confirmação de senha inválida!";
        } else if (usuarioBO.usuarioExiste(login)) {
            mensagem = "O usuário " + login + " já existe!";
        }

        //retorno ao usuario
        if(!mensagem.isEmpty()){
            Util.toastMensagem(mensagem, getApplicationContext());
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }
}
