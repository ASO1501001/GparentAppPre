package st.asojuku.ac.jp.gparentapppre;

/**
 * Created by Itchy on 2017/05/19.
 */
public class Child {
    private String childID;
    private String childName;

    public Child(String childID, String childName) {
        this.childID = childID;
        this.childName = childName;
    }

    public Child() {
    }

    public String getChildID() {
        return childID;
    }

    public void setChildID(String childID) {
        this.childID = childID;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }
}
