package savepassword.edi.unoesc.edu.br.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import savepassword.edi.unoesc.edu.br.model.Credencial;
import savepassword.edi.unoesc.edu.br.model.Usuario;

public class Connection extends OrmLiteSqliteOpenHelper {

	private static final String NAME = "banco.db";
	private static final int VERSION = 4;

	public Connection(Context context) {
		super(context, NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase sd, ConnectionSource cs) {

		try {
			TableUtils.createTableIfNotExists(cs, Credencial.class);
			TableUtils.createTableIfNotExists(cs, Usuario.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase sd, ConnectionSource cs, int i, int i1) {
		onCreate(sd, cs);
	}

	@Override
	public void close(){
		super.close();
	}
}
