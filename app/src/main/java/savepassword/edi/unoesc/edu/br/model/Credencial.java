package savepassword.edi.unoesc.edu.br.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "credencial")
public class Credencial implements Serializable {

    @DatabaseField(generatedId = true, columnName = "id")
    private Integer id;

    @DatabaseField(columnName = "codigoUsuario")
    private Integer codigoUsuario;

    @DatabaseField(columnName = "descricao")
    private String descricao;

    @DatabaseField(columnName = "observacao")
    private String observacao;

    @DatabaseField(columnName = "login")
    private String login;

    @DatabaseField(columnName = "senha")
    private String senha;

    @DatabaseField(columnName = "url")
    private String url;

    public Credencial() {
    }

    public Credencial(String descricao, String login, String senha, Integer codigoUsuario, String url) {

        this.descricao = descricao;
        this.login = login;
        this.senha = senha;
        this.codigoUsuario = codigoUsuario;
        this.url = url;

    }

    public String getDescricao() {

        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSenha() {

        return senha;
    }

    public void setSenha(String senha) {

        this.senha = senha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Integer getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(Integer codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
