package st.asojuku.ac.jp.gparentapppre;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Itchy on 2017/05/18.
 */
public abstract class Connect extends AsyncTask<Void,Void,Void>{

    public static final String CONNECT_IP = "163.44.165.36" ;
    private String urlstr,sendstr;

    private HttpURLConnection httpsURLConnection;
    private URL url;

    //filePass は ip以降の"/"から".php"まで 例:/php_debug/php_code/JsonReceive.php
    public Connect(String filePass){
        urlstr = "http://" + CONNECT_IP + filePass;
        sendstr = "";
    }


    @Override
    protected Void doInBackground(Void... voids) {
            try {
            url = new URL(urlstr);
            httpsURLConnection = (HttpURLConnection)url.openConnection();
            httpsURLConnection.setRequestMethod("POST");
            httpsURLConnection.setInstanceFollowRedirects(false);
            httpsURLConnection.setRequestProperty("Accept-Language","jp");
            httpsURLConnection.setDoOutput(true);
            httpsURLConnection.setDoInput(true);
            httpsURLConnection.setRequestProperty("Content-Type","application/json; charset=utf-8");


            OutputStream outputStream = httpsURLConnection.getOutputStream();


            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            writer.write(sendstr);
            writer.flush();
            writer.close();
            outputStream.close();

            int status = httpsURLConnection.getResponseCode();

            switch (status) {
                case HttpURLConnection.HTTP_OK:
                    InputStream is = httpsURLConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                    String httpSource = new String();
                    String str;
                    while (null != (str = reader.readLine())) {

                        messageGetDoing(str);
                    }

                    is.close();
                    break;
                case HttpURLConnection.HTTP_UNAUTHORIZED:
                    break;
                default:
                    break;
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



    return null;
    }

    public void setSendstr(Object object){
        sendstr = String.valueOf(object);
    }

    protected abstract void messageGetDoing(String message);
}
