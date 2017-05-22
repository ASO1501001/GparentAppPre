package st.asojuku.ac.jp.gparentapppre;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Itchy on 2017/05/19.
 */
public class SelectChild {
    private static final String FILE_PASS = "/php_debug/php_code/ReceiveChildID.php";
    private Connect connect;
    private ArrayList<Child> childs;
    private boolean receiveFlg;

    public SelectChild(){
        childs = new ArrayList<Child>();
        connect = new Connect(FILE_PASS){
            @Override
            protected void messageGetDoing(String message){
                Log.v("rec",message);



                try {
                    JSONArray jsonArray = new JSONArray(message);
                    int i=0;
                    for(;i<jsonArray.length();i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Child child = new Child();
                        child.setChildID(jsonObject.getString("ChildID"));
                        child.setChildName(jsonObject.getString("ChildName"));


                        childs.add(child);

                    }
                    if(i == jsonArray.length()){
                        receiveFlg = false;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

    }
    public void getChildIDList(String parentID){
        JSONObject sendJson = new JSONObject();

        try {
            sendJson.put("parentID",parentID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        connect.setSendstr(sendJson);

        connect.execute();

        receiveFlg = true;
    }

    public ArrayList<Child> getChilds() {
        while(receiveFlg);
        return childs;
    }
}
