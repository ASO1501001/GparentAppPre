package st.asojuku.ac.jp.gparentapppre;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Itchy on 2017/05/18.
 */
public class DBConnecter extends SQLiteOpenHelper {

    private static final String DB = "local.db";
    private static final String CREATE_TABLE_MEMBER = "CREATE TABLE membertbl(name varchar(30) PRIMARY KEY,member varchar(30) NOT NULL);";
    private static final String CREATE_TABLE_GPS = "CREATE TABLE gpstbl(childID varchar(30) NOT NULL , date varchar(30) NOT NULL , time varchar(30) NOT NULL ,latitude varchar(255) NOT NULL,longitude varchar(255) NOT NULL ,sendflg varchar(1) NOT NULL, PRIMARY KEY(childID,date,time));";
    private static final String DROP_TABLE_MEMBER = "DROP TABLE membertbl";
    private static final String DROP_TABLE_GPS = "DROP TABLE gpstbl";

    public DBConnecter(Context context) {
        super(context, DB, null, 1);

    }

    public DBConnecter(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_MEMBER);
        sqLiteDatabase.execSQL(CREATE_TABLE_GPS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_TABLE_MEMBER);
        sqLiteDatabase.execSQL(DROP_TABLE_GPS);
        onCreate(sqLiteDatabase);
    }
}