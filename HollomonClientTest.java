import java.io.IOException;
import java.util.List;

public class HollomonClientTest {
    public static void main(String[] args) {
        try {
            // Replace these with your actual username/password!
            String server = "netsrv.cim.rhul.ac.uk";
            int port = 1812;
            String username = "yourusername";  // Replace with your username
            String password = "yourpassword";  // Replace with your password

            HollomonClient client = new HollomonClient(server, port);
            
            try {
                List<Card> result = client.login(username, password);

                if (result == null) {
                    System.out.println("Login failed.");
                } else {
                    System.out.println("Login successful. Card list size: " + result.size());
                }
            } finally {
                client.close();
            }
        } catch (Exception e) {  // Changed from IOException to Exception
            e.printStackTrace();
        }
    }
}
