package savepassword.edi.unoesc.edu.br.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class Util {

    /**
     * Metodo que mostra em tela um toast com mensagem parametrizada
     *
     * @param mensagem
     * @param context
     */
    public static void toastMensagem(String mensagem, Context context){
        Toast toast = Toast.makeText(context, mensagem, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
        toast.show();
    }

}
