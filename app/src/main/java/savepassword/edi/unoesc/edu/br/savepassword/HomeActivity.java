package savepassword.edi.unoesc.edu.br.savepassword;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Parcelable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.support.v7.widget.SearchView;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import savepassword.edi.unoesc.edu.br.bo.AutenticacaoBO;
import savepassword.edi.unoesc.edu.br.bo.CredencialBO;
import savepassword.edi.unoesc.edu.br.bo.impl.AutenticacaoBOImpl;
import savepassword.edi.unoesc.edu.br.bo.impl.CredencialBOImpl;
import savepassword.edi.unoesc.edu.br.dao.Connection;
import savepassword.edi.unoesc.edu.br.dao.CredencialDAO;
import savepassword.edi.unoesc.edu.br.dao.impl.CredencialDAOImpl;
import savepassword.edi.unoesc.edu.br.dao.impl.UsuarioDAOImpl;
import savepassword.edi.unoesc.edu.br.model.Credencial;
import savepassword.edi.unoesc.edu.br.util.Util;

public class HomeActivity extends AppCompatActivity {

    private AutenticacaoBO autenticacaoBO;

    private CredencialBO credencialBO;

    private Connection connection;

    //DAO deve ser inicializado no contexto da pagina
    private CredencialDAO credencialDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //inicializacao e registro da page
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //inicializacao do BO utilizado pelo controller
        inicializarAtributos();

        //style actionBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0288d1")));
        actionBar.setTitle("Save Password");

        //Area de testes para a lista de credenciais
        carregarLista(null);

    }

    /**
     * Metodo que inicializa os atributos da tela
     */
    private void inicializarAtributos() {
        autenticacaoBO = new AutenticacaoBOImpl();
        credencialBO = new CredencialBOImpl();

        /* DAOs que serao utilizados pela página devem ser inicializados
         * no contexto da pagina onde os eventos irão ocorrer, caso contrario
         * a connection com db nao é criada corretamente.
         */
        try {
            connection = new Connection(HomeActivity.this);
            credencialDAO = new CredencialDAOImpl(connection.getConnectionSource());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo que carrega a lista de credenciais
     *
     * @param pesquisa
     */
    private void carregarLista(String pesquisa) {

        ArrayList<Credencial> credenciais = credencialBO.adquirir(pesquisa);
        final ListView lista = (ListView) findViewById(R.id.lista);
        CredencialListAdapter adapter = new CredencialListAdapter(this, R.layout.listview, credenciais);
        lista.setAdapter(adapter);

        //Metodo para onclick em item da lista
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Credencial itemClicado = (Credencial) lista.getItemAtPosition(position);
                Intent intent = new Intent(HomeActivity.this, CredencialActivity.class);
                intent.putExtra("credencial", itemClicado);
                startActivity(intent);
            }

        });

    }

    /**
     * Metodo que remove dados do usuario atual da session e volta para tela de login
     */
    private void sair(){
        autenticacaoBO.sair();
        finish();
    }

    /**
     * Metodo que chama tela para adicionar nova credencial
     */
    private void novaCredencial(){
        startActivity(new Intent(HomeActivity.this, CredencialActivity.class));
    }

    /**
     * Metodo que cria um alert com informaçoes sobre o projeto
     */
    private void sobre(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("SOBRE");
        alert.setMessage("SavePassword é um aplicativo utilitário desenvolvido pelos acadêmicos Jackson Willian Carbonera, Luiz Henrique Secco e Fábio Falcade. O projeto foi proposto como atividade avaliativa das matérias curriculares Estrutura de Dados e Laboratório de Estrutura de Dados da Universidade do Oeste de Santa Catarina (UNOESC), e teve como tutuor o professor Roberson Junior Fernandes Alves.");
        alert.setPositiveButton("Voltar", null);
        alert.create().show();
    }

    /**
     * Criacao do menu principal do aplicativo
     *
     * @param menu
     * @return boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        listenersSearch(menu);
        return true;
    }

    /**
     * Cria os listeners para o searchView
     */
    private void listenersSearch(Menu menu) {

        MenuItem ourSearchItem = menu.findItem(R.id.search);
        SearchView sv2 = (SearchView) MenuItemCompat.getActionView(ourSearchItem);
        sv2.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                carregarLista(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                carregarLista(query);
                return false;
            }



        });
    }

    /**
     * Controller para click em itens do menu
     *
     * @param item
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.novaCredencial:
                novaCredencial();
                break;
            case R.id.sobre:
                sobre();
                break;
            case R.id.sair:
                sair();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Carrega a lista
        carregarLista(null);
    }
}
