import java.io.IOException;
import java.util.List;

public class HollomonClientTest {
    public static void main(String[] args) {
        try {
            // Replace these with your actual username/password!
            String server = "netsrv.cim.rhul.ac.uk";
            int port = 1812;
            String username = "yourusername";
            String password = "yourpassword";

            HollomonClient client = new HollomonClient(server, port);

            List<Card> result = client.login(username, password);

            if (result == null) {
                System.out.println("Login failed.");
            } else {
                System.out.println("Login successful. Card list size: " + result.size());
            }

            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
