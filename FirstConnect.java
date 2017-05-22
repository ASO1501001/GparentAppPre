package st.asojuku.ac.jp.gparentapppre;

import android.content.Context;

/**
 * Created by Itchy on 2017/05/18.
 */
public class FirstConnect{

    private static final String FILE_PASS = "/php_debug/php_code/ParentFirstConnect.php";
    public static final String MEMBER_NAME = "gparentID";
    private DBManager dbManager;
    private Connect connect;
    private String gParentID;


    public FirstConnect(Context context){
        connect = new Connect(FILE_PASS){
            @Override
            protected void messageGetDoing(String message){
                dbManager.insertMember(MEMBER_NAME,message);
            }
        };
        dbManager = new DBManager(context);

        if(checkFirstConnect()){
            connect.execute();
        }
    }

    private boolean checkFirstConnect(){
        String member = dbManager.getMember(MEMBER_NAME);

        if(member==null){
            return true;
        }else{
            this.gParentID = member;
            return false;
        }
    }

}