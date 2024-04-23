import java.io.IOException; 
import java.io.BufferedReader; 
import java.io.InputStreamReader; 
import java.net.Socket; 
import java.net.UnknownHostException; 

/**
 * @Author 
 *  Levi Kuhaulua
 */
public class Client1 {
    public static void main(String[] args) throws IOException{

        try (Socket client1 = new Socket("127.0.0.1", 12345)) {
            // Initial Testing: Trying to receive the IP address of this client. alter later. 
            BufferedReader fromController = new BufferedReader(new InputStreamReader(client1.getInputStream())); 

            System.out.println("IP Address of Me: " + fromController.readLine()); 
            System.out.println("Port that I am using: " + fromController.readLine()); 

            fromController.close(); 
            client1.close(); 
        } catch (UnknownHostException e) {
            System.err.println("Unknown Hostname: " + e.getMessage());
            System.exit(-1); 
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1); 
        }
    }
}