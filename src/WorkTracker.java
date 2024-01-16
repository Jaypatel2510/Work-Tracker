import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.*;

class WorkTracker {
    private Map<String, User> users;
    private Scanner scanner;
    private String userFileName;

    public WorkTracker() {
        this.users = new HashMap<>();
        this.scanner = new Scanner(System.in);
        this.userFileName = "DataBase.txt";
    }

    public void createUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        if (isUsernameAvailable(username)) {
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            User user = new User(username, password);
            users.put(username, user);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(userFileName, true))) {
                writer.write(username + "," + password);
                writer.newLine();
                System.out.println("User account created successfully.");
            } catch (IOException e) {
                System.out.println("Failed to create user account.");
            }
        } else {
            System.out.println("Username already exists. Please choose a different username.");
        }
    }

    private boolean isUsernameAvailable(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(userFileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 2);
                if (parts.length == 2) {
                    String existingUsername = parts[0];
                    if (existingUsername.equals(username)) {
                        return false; // Username already exists
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to check username availability.");
        }
        return true; // Username is available
    }

    public void loadUsers() {
        try (BufferedReader reader = new BufferedReader(new FileReader(userFileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String username = parts[0];
                String password = parts[1];
                User user = new User(username, password);
                users.put(username, user);
            }
        } catch (IOException e) {
            System.out.println("Failed to load user accounts.");
        }
    }

    public void loginUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            System.out.println("Login successful. Welcome, " + user.getUsername() + "!");
            showMenu(user);
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    private void showMenu(User user) {
        int choice;
        do {
            System.out.println("\nMenu:");
            System.out.println("1. Add work description");
            System.out.println("2. View work description");
            System.out.println("3. Update work description");
            System.out.println("4. Delete work description");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    addWorkDescription(user);
                    break;
                case 2:
                    viewWorkDescription(user);
                    break;
                case 3:
                    updateWorkDescription(user);
                    break;
                case 4:
                    deleteWorkDescription(user);
                    break;
                case 5:
                    System.out.println("Logged out. Goodbye, " + user.getUsername() + "!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 5);
    }

    private void addWorkDescription(User user) {
        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.print("Enter work description: ");
        String description = scanner.nextLine();

        user.addWorkDescription(date, description);
        System.out.println("Work description added successfully.");
    }

    private void viewWorkDescription(User user) {
        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = scanner.nextLine();

        String description = user.getWorkDescription(date);
        if (description != null) {
            System.out.println("Work description for " + date + ": " + description);
        } else {
            System.out.println("No work description found for " + date + ".");
        }
    }

    private void updateWorkDescription(User user) {
        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        String description = user.getWorkDescription(date);

        if (description != null) {
            System.out.print("Current work description: " + description);
            System.out.print("\nEnter new work description: ");
            String newDescription = scanner.nextLine();
            user.updateWorkDescription(date, newDescription);
            System.out.println("Work description updated successfully.");
        } else {
            System.out.println("No work description found for " + date + ".");
        }
    }

    private void deleteWorkDescription(User user) {
        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = scanner.nextLine();

        String description = user.getWorkDescription(date);
        if (description != null) {
            user.deleteWorkDescription(date);
            System.out.println("Work description deleted successfully.");
        } else {
            System.out.println("No work description found for " + date + ".");
        }
    }
}
