package ph.edu.dlsu.mobapde.tara;


/**
 * Created by louis on 11/8/2017.
 */

public class User {
    private long id;
    private String email;
    private String username;
    private String password;

    private Race currentRace;

    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
        currentRace = null;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Race getCurrentRace() {
        return currentRace;
    }

    public void setCurrentRace(Race currentRace) {
        this.currentRace = currentRace;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", currentRace=" + currentRace +
                '}';
    }
}
