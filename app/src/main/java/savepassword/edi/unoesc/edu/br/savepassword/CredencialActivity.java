package savepassword.edi.unoesc.edu.br.savepassword;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.sql.SQLException;

import savepassword.edi.unoesc.edu.br.bo.CredencialBO;
import savepassword.edi.unoesc.edu.br.bo.impl.CredencialBOImpl;
import savepassword.edi.unoesc.edu.br.dao.Connection;
import savepassword.edi.unoesc.edu.br.dao.CredencialDAO;
import savepassword.edi.unoesc.edu.br.dao.impl.CredencialDAOImpl;
import savepassword.edi.unoesc.edu.br.dao.impl.UsuarioDAOImpl;
import savepassword.edi.unoesc.edu.br.model.Credencial;
import savepassword.edi.unoesc.edu.br.model.Usuario;
import savepassword.edi.unoesc.edu.br.session.AutenticacaoSession;
import savepassword.edi.unoesc.edu.br.util.Util;

public class CredencialActivity extends AppCompatActivity {

    private CredencialBO credencialBO;

    private EditText txtDescricao;

    private EditText txtLogin;

    private EditText txtSenha;

    private EditText txtUrl;

    private Button btnExcluir;

    private Button btnSalvar;

    private Connection connection;

    private  Boolean editar = Boolean.FALSE;

    //DAO deve ser inicializado no contexto da pagina
    private CredencialDAO credencialDAO;

    private Credencial credencialEditar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credencial);

        //get parametros editar
        credencialEditar = (Credencial) getIntent().getSerializableExtra("credencial");
        editar = credencialEditar != null;

        //style actionBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0288d1")));
        actionBar.setTitle( editar ? "Editar Credencial" : "Nova Credencial");

        //inicializacao dos atributos
        inicializarAtributos();

        //inicializar componentes
        inicializarComponentes();

    }

    /**
     * Incicializacao do BO e do DAO que serao utilizados na pagina
     */
    private void inicializarAtributos() {

        credencialBO = new CredencialBOImpl();

        /* DAOs que serao utilizados pela página devem ser inicializados
         * no contexto da pagina onde os eventos irão ocorrer, caso contrario
         * a connection com db nao é criada corretamente.
         */
        try {
            connection = new Connection(CredencialActivity.this);
            credencialDAO = new CredencialDAOImpl(connection.getConnectionSource());
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    /**
     * Inicializacao dos componentes
     */
    private void inicializarComponentes() {

        txtDescricao = findViewById(R.id.txtDescricao);
        txtLogin = findViewById(R.id.txtLogin);
        txtSenha = findViewById(R.id.txtSenha);
        txtUrl = findViewById(R.id.txtUrl);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnExcluir = findViewById(R.id.btnExcluir);

        if(editar){
            txtDescricao.setText(credencialEditar.getDescricao());
            txtLogin.setText(credencialEditar.getLogin());
            txtSenha.setText(credencialEditar.getSenha());
            txtUrl.setText(credencialEditar.getUrl());

            btnExcluir.setVisibility(View.VISIBLE);
            btnExcluir.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(CredencialActivity.this);
                    alert.setTitle("ATENÇÃO!");
                    alert.setMessage("Deseja excluir a credencial?");
                    alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            excluirCredencial();
                        }
                    } );
                    alert.setNegativeButton("Não", null);
                    alert.create().show();
                }

            });
        }

        btnSalvar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(editar){
                    editarCredencial();
                }else{
                    novaCredencial();
                }
                finish();
            }

        });

    }

    /**
     * metodo que cria uma nova credencial e finaliza a activity
     */
    private void novaCredencial() {

        Boolean ret = credencialBO.salvar(new Credencial(txtDescricao.getText().toString(), txtLogin.getText().toString(), txtSenha.getText().toString(), AutenticacaoSession.getInstance().getCodigoUsuarioLogado(), txtUrl.getText().toString()));

        if(ret){
            Util.toastMensagem("Credencial criada com Sucesso!", getApplicationContext());
            finish();
        } else {
            Util.toastMensagem("Falha ao salvar!", getApplicationContext());
        }
    }

    /**
     * metodo que edita a credencial e finaliza a activity
     */
    private void editarCredencial() {

        Credencial editado = credencialEditar;
        editado.setDescricao(txtDescricao.getText().toString());
        editado.setSenha(txtSenha.getText().toString());
        editado.setLogin(txtLogin.getText().toString());
        editado.setUrl(txtUrl.getText().toString());

        Boolean ret = credencialBO.editar(editado);

        if(ret){
            Util.toastMensagem("Credencial editada com Sucesso!", getApplicationContext());
            finish();
        } else {
            Util.toastMensagem("Falha ao editar!", getApplicationContext());
        }
    }

    /**
     * metodo que exclui a credencial e finaliza a activity
     */
    private void excluirCredencial() {

        Boolean ret = credencialBO.excluir(credencialEditar.getId());

        if(ret){
            Util.toastMensagem("Credencial excluida com Sucesso!", getApplicationContext());
            finish();
        } else {
            Util.toastMensagem("Falha ao excluir!", getApplicationContext());
        }
    }

}
