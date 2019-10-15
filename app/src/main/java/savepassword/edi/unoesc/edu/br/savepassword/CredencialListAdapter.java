package savepassword.edi.unoesc.edu.br.savepassword;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import savepassword.edi.unoesc.edu.br.model.Credencial;

public class CredencialListAdapter extends ArrayAdapter<Credencial> {

    private Context mContext;
    public int mResource;

    /**
     * Constructor
     *
     * @param context
     * @param resource
     * @param objects
     */
    public CredencialListAdapter(Context context, int resource, ArrayList<Credencial> objects) {

        super(context, resource, objects);
        mContext = context;
        mResource = resource;

    }

    @NonNull
    @Override
    public View getView(int position, View contentView, ViewGroup parent) {

        String descricao = getItem(position).getDescricao();
        String login = getItem(position).getLogin();

        Credencial credencial = getItem(position);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        contentView = inflater.inflate(mResource, parent, false);

        TextView txtDescricao = (TextView) contentView.findViewById(R.id.txtDescricao);
        TextView txtLogin = (TextView) contentView.findViewById(R.id.txtLogin);

        txtDescricao.setText(descricao);
        txtLogin.setText(login);

        return contentView;
    }
}
