package ph.edu.dlsu.mobapde.tara;


/**
 * Created by louis on 11/8/2017.
 */

public class User {
    private String id;
    private String email;
    private String username;
    private String password;

    private int numEarly;
    private int numOnTime;
    private int numLate;
    private int numCancelled;

    private boolean isActive;

    private Race currentRace;

    public User() {}

    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
        currentRace = null;
    }

    public void initializeStats() {
        numEarly = 0;
        numOnTime = 0;
        numLate = 0;
        numCancelled = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public int getNumEarly() {
        return numEarly;
    }

    public void setNumEarly(int numEarly) {
        this.numEarly = numEarly;
    }

    public int getNumOnTime() {
        return numOnTime;
    }

    public void setNumOnTime(int numOnTime) {
        this.numOnTime = numOnTime;
    }

    public int getNumLate() {
        return numLate;
    }

    public void setNumLate(int numLate) {
        this.numLate = numLate;
    }

    public int getNumCancelled() {
        return numCancelled;
    }

    public void setNumCancelled(int numCancelled) {
        this.numCancelled = numCancelled;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Race getCurrentRace() {
        return currentRace;
    }

    public void setCurrentRace(Race currentRace) {
        this.currentRace = currentRace;
    }

    public void addEarly() {
        numEarly++;
    }

    public void addOnTime() {
        numOnTime++;
    }

    public void addLate() {
        numLate++;
    }

    public void addCancelled() {
        numCancelled++;
    }
}
