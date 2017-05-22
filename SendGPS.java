package st.asojuku.ac.jp.gparentapppre;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class SendGPS extends Service {

    private DBManager db;
    private Connect connect;
    private static final String FILE_PASS = "/php_debug/php_code/jsonReceive.php";
    Timer timer;

    public SendGPS() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        db = new DBManager(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
        timer = null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                send();
            }
        },0,18000000);
        //18000000


        return super.onStartCommand(intent, flags, startId);
    }

    private void send(){
        String gParentID = db.getMember("gparentID");

        db.deleteSentGPS();
        ArrayList<GPS> gpsList = db.getGPSList();
        if(gpsList != null) {
            for (GPS gps : gpsList) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("gparentID", gParentID);
                    jsonObject.put("childID", gps.getChildID());
                    jsonObject.put("date", gps.getDate());
                    jsonObject.put("time", gps.getTime());
                    jsonObject.put("latitude", gps.getLatitude());
                    jsonObject.put("longitude", gps.getLongitude());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Connect connect = new Connect(FILE_PASS) {
                    @Override
                    protected void messageGetDoing(String message) {
                        Log.v("send",message);
                        try {
                            JSONObject jsonObject = new JSONObject(message);

                            GPS gps = new GPS(
                                    jsonObject.getString("childID"),
                                    jsonObject.getString("date"),
                                    jsonObject.getString("time"),
                                    jsonObject.getString("latitude"),
                                    jsonObject.getString("longitude")
                            );

                            db.sentGPS(gps);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                connect.setSendstr(jsonObject);
                connect.execute();
            }
        }


    }
}
