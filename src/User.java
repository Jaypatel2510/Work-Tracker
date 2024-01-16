import java.util.HashMap;
import java.util.Map;

class User {
    private String username;
    private String password;
    private Map<String, String> workDescriptions;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.workDescriptions = new HashMap<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void addWorkDescription(String date, String description) {
        workDescriptions.put(date, description);
    }

    public String getWorkDescription(String date) {
        return workDescriptions.get(date);
    }

    public void updateWorkDescription(String date, String description) {
        workDescriptions.put(date, description);
    }

    public void deleteWorkDescription(String date) {
        workDescriptions.remove(date);
    }
}