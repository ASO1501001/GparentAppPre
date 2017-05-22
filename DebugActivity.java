package st.asojuku.ac.jp.gparentapppre;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class DebugActivity extends AppCompatActivity{

    private Button gpsBtn,sqlBtn,sendBtn,pidBtn;
    private EditText pidEdtext;
    private TextView gpsText,sqlText,sendText;
    private LinearLayout linearLayout;
    private String textstr;
    private final String[] gpsTexts= {"GPS停止中","GPS起動中"},sendTexts={"GPS待機中","GPS送信中"};
    private int gpsKey,sendKey;
    private DBManager db;
    private Intent backGPS,backSend;
    private ArrayList<Button> childBtns;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);

        new FirstConnect(this);

        db = new DBManager(this);
        gpsBtn = (Button)findViewById(R.id.debug_GPS_button);
        sqlBtn = (Button)findViewById(R.id.debug_SELECT_GPS_button);
        sendBtn = (Button)findViewById(R.id.debug_SEND_GPS_button);
        pidBtn = (Button)findViewById(R.id.debug_PID_button);

        pidEdtext = (EditText) findViewById(R.id.debug_PID_edtext);

        gpsText = (TextView)findViewById(R.id.debug_GPS_text);
        sqlText = (TextView)findViewById(R.id.Debug_SQLtext);
        sendText = (TextView)findViewById(R.id.debug_SEND_GPS_text);

        linearLayout = (LinearLayout)findViewById(R.id.line);

        gpsKey = 0;
        sendKey =0;

        backGPS = new Intent(getApplication(), BackGroundGPS.class);
        backSend = new Intent(getApplication(),SendGPS.class);

        childBtns = new ArrayList<Button>();


        gpsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gpsText.setText(gpsTexts[gpsKey = ++gpsKey%2]);
                if(gpsKey==0){
                    //GPS取得終了
                    stopService(backGPS);
                }else{

                    //GPS取得開始
                    startService(backGPS);
                }
            }
        });

        sqlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList<GPS> gpsList = db.getGPSList();
                if(gpsList == null)return;
                textstr ="";
                for(GPS gps:gpsList){
                    textstr += "---------------\n";
                    textstr += "ChildID:"+ gps.getChildID() +"\n";
                    textstr += "Latitude:"+gps.getLatitude()+"\n";
                    textstr += "Longitude:"+gps.getLongitude()+"\n";
                    textstr += "date:"+gps.getDate()+"\n";
                    textstr += "time:"+gps.getTime()+"\n";
                    textstr += "---------------\n";
                }
                sqlText.setText(textstr);
            }
        });


        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendText.setText(sendTexts[sendKey = ++sendKey%2]);
                if(sendKey==0){
                    stopService(backSend);
                }else{

                    startService(backSend);
                }
            }
        });

        pidBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pid = String.valueOf(pidEdtext.getText());
                SelectChild selectChild = new SelectChild();

                selectChild.getChildIDList(pid);

                ArrayList<Child> childs = selectChild.getChilds();

                addButton(childs);

            }
        });


    }

    private void addButton(ArrayList<Child> childs){
        linearLayout.removeAllViews();
        childBtns.clear();
        for (Child child :childs){
            final Button button = new Button(this);
            button.setId(Integer.parseInt(child.getChildID()));
            button.setText(child.getChildName());

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sqlText.setText(button.getText());
                }
            });

            childBtns.add(button);
            linearLayout.addView(button);

        }
    }

}
