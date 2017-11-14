package ph.edu.dlsu.mobapde.tara;

/**
 * Created by user on 12/11/2017.
 */

public class Tracking {

    private String email;
    private String uid;
    private String lat;
    private String lng;
    private String status;

    public Tracking(){

    }

    public Tracking(String email, String uid, String lat, String lng, String status) {
        this.email = email;
        this.uid = uid;
        this.lat = lat;
        this.lng = lng;
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
