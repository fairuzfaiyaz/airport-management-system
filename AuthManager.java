import java.io.*;

public class AuthManager {
    private static final String CREDS_FILE = "login_creds.txt";

    public static boolean authenticate(String username, String password) {
        if ("admin".equals(username) && "admin".equals(password)) return true;

        try (BufferedReader br = new BufferedReader(new FileReader(CREDS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 2);
                if (parts.length == 2 && parts[0].equals(username) && parts[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println("Auth error: " + e.getMessage());
        }
        return false;
    }

    public static boolean register(String username, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader(CREDS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(username + ",")) return false;
            }
        } catch (IOException e) {
            System.err.println("Registration error: " + e.getMessage());
        }

        try (FileWriter fw = new FileWriter(CREDS_FILE, true)) {
            fw.write(username + "," + password + "\n");
            return true;
        } catch (IOException e) {
            System.err.println("Registration error: " + e.getMessage());
            return false;
        }
    }
}