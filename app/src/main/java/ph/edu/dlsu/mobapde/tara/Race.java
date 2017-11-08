package ph.edu.dlsu.mobapde.tara;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by louis on 11/8/2017.
 */

public class Race {
    private long id;
    private String title;
    private String location;
    private Date date;

    private ArrayList<User> users;

    public Race(String title, String location, Date date) {
        this.title = title;
        this.location = location;
        this.date = date;
        users = new ArrayList<User>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public boolean addUser(User u) {
        if(u == null)
            return false;

        return true;
    }

    // remove user

    public User getUser(int id) {
        return users.get(id);
    }

    @Override
    public String toString() {
        return "Race{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", location='" + location + '\'' +
                ", date=" + date +
                ", users=" + users +
                '}';
    }
}
