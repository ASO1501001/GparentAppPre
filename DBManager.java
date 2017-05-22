package st.asojuku.ac.jp.gparentapppre;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Itchy on 2017/05/18.
 */
public class DBManager {
    DBConnecter dbConnecter;
    SQLiteDatabase db;

    public DBManager(Context context){
        dbConnecter = new DBConnecter(context);
    }

    //挿入
    public void insertGPS(GPS gps){
        connectWrite();

        ContentValues contentValues = new ContentValues();

        contentValues.put("childID",gps.getChildID());
        contentValues.put("date",gps.getDate());
        contentValues.put("time",gps.getTime());
        contentValues.put("latitude",gps.getLatitude());
        contentValues.put("longitude",gps.getLongitude());
        contentValues.put("sendflg","0");

        db.insert("gpstbl",null,contentValues);

        Log.v("DB","insert");
    }

    //取得
    public ArrayList<GPS> getGPSList(){
        connectWrite();

        Cursor cursor =
                db.rawQuery("SELECT * FROM gpstbl WHERE sendflg = ?;",new String[]{"0"});

        ArrayList<GPS> gpsList = new ArrayList<GPS>();

//        if(cursor.getCount()==0){
//            return null;
//        }

        while (cursor.moveToNext()){

            GPS gps = new GPS(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4));

            gpsList.add(gps);

        }



        cursor.close();
        Log.v("DB","select");

        return gpsList;
    }

    public void sentGPS(GPS gps){
        connectWrite();

        ContentValues contentValues = new ContentValues();
        contentValues.put("sendflg","1");

        String where = "childID = '" +gps.getChildID() + "'AND date = '" + gps.getDate() + "'AND time = '" + gps.getTime()+"'";

        db.update("gpstbl",contentValues,where,null);

        Log.v("DB","update");
    }

    public void deleteSentGPS(){
        connectWrite();

        db.delete("gpstbl","sendflg = 1",null);

        Log.v("DB","delete");
    }


    //挿入
    public void insertMember(String keyName,String member){
        connectWrite();

        ContentValues contentValues = new ContentValues();

        contentValues.put("name",keyName);
        contentValues.put("member",member);

        db.insert("membertbl",null,contentValues);

    }

    //取得
    public String getMember(String keyName){
        connectWrite();

        Cursor cursor =
                db.rawQuery("SELECT * FROM membertbl WHERE name = ?;",new String[]{keyName});

        cursor.moveToNext();

        if(cursor.getCount() == 0){
            return null;
        }else{
            String ret = cursor.getString(1);

            System.out.println(this.getClass().toString()+": return: "+ret);

            return ret;
        }

    }


    private void connectRead(){
        db = dbConnecter.getReadableDatabase();
    }

    private void connectWrite(){
        db = dbConnecter.getWritableDatabase();
    }

}
