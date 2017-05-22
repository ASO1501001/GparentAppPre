package st.asojuku.ac.jp.gparentapppre;

/**
 * Created by Itchy on 2017/05/18.
 */
public class GPS {
    private String childID;
    private String date;
    private String time;
    private String latitude;
    private String longitude;

    public GPS(String childID, String date, String time ,String latitude, String longitude) {
        this.childID = childID;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
        this.time = time;
    }

    public String getChildID() {
        return childID;
    }

    public void setChildID(String childID) {
        this.childID = childID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
