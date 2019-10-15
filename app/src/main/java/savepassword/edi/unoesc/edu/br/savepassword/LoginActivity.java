package savepassword.edi.unoesc.edu.br.savepassword;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.app.AppCompatActivity;
import java.sql.SQLException;

import savepassword.edi.unoesc.edu.br.bo.AutenticacaoBO;
import savepassword.edi.unoesc.edu.br.bo.impl.AutenticacaoBOImpl;
import savepassword.edi.unoesc.edu.br.dao.Connection;
import savepassword.edi.unoesc.edu.br.dao.impl.UsuarioDAOImpl;
import savepassword.edi.unoesc.edu.br.util.Util;

/**
 * Classe controller para eventos da pagina login.xml
 */
public class LoginActivity extends AppCompatActivity {

    private EditText txtLogin;

    private EditText txtSenha;

    private Button btnEntrar;

    private Button btnRegistrar;

    private AutenticacaoBO autenticacaoBO;

    private Connection connection;

    //DAO deve ser inicializado no contexto da pagina
    private UsuarioDAOImpl usuarioDAOImpl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //inicializa e registra pagina
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //inicializacao componentes e atributos
        this.iniciarComponentes();
        this.inicializarAtributos();

        //esconde actionBar
        getSupportActionBar().hide();

        //Ações do botao entrar
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String login = txtLogin.getText().toString();
                String senha = txtSenha.getText().toString();

                //validacao para campos invalidos
                if(login.isEmpty() || senha.isEmpty()){
                    Util.toastMensagem("Preencha os campos!", getApplicationContext());
                    return;
                }

                if(autenticacaoBO.login(login, senha)){
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    limparForm();
                }else{
                    Util.toastMensagem("Login Inválido!", getApplicationContext());
                }
            }
        });

        //Ações do botao registrar
        btnRegistrar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistrarActivity.class));
                limparForm();

            }

        });

    }

    /**
     * Medoto que inicializa os componentes para
     * controller dos seus eventos
     */
    private void iniciarComponentes(){
        txtLogin = findViewById(R.id.txtLogin);
        txtSenha = findViewById(R.id.txtSenha);
        btnEntrar = findViewById(R.id.btnEntrar);
        btnRegistrar = findViewById(R.id.btnRegistrar);
    }
    /**
     * Metodo que inicializa atributos usados pelo
     * controller da pagina.
     */
    private void inicializarAtributos(){

        autenticacaoBO = new AutenticacaoBOImpl();

        /* DAOs que serao utilizados pela página devem ser inicializados
         * no contexto da pagina onde os eventos irão ocorrer, caso contrario
         * a connection com db nao é criada corretamente.
         */
        try {
            connection = new Connection(LoginActivity.this);
            usuarioDAOImpl = new UsuarioDAOImpl(connection.getConnectionSource());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Metodo responsavel por limpar os dados dos campos
     */
    private void limparForm() {
        txtLogin.setText("");
        txtSenha.setText("");
    }

}
