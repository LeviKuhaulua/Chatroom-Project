import java.net.ServerSocket; 
import java.net.Socket; 
import java.io.PrintWriter; 
import java.io.IOException;
import java.io.BufferedReader; 
import java.io.InputStreamReader;
/**
 * Controller Socket that will accept connection between the two sockets then proceed to send out 
 * the necessary information needed for both clients to accept and communicate with each other. 
 * @author 
 *  Levi Kuhaulua
 */
public class ControllerSocket {
    public static void main(String[] args) throws IOException{
        

        // Open up the controller socket for communication. 
        try (ServerSocket connect = new ServerSocket(12345)) {

            Socket client1 = connect.accept(); 
            Socket client2 = connect.accept(); 

            // Get the IP Address Information and Port number for clients. 
            BufferedReader fromClient1 = new BufferedReader(new InputStreamReader(client1.getInputStream())); 
            BufferedReader fromClient2 = new BufferedReader(new InputStreamReader(client2.getInputStream())); 

            String client1IP = fromClient1.readLine(); 
            String client1Port = fromClient1.readLine();
            
            String client2IP = fromClient2.readLine(); 
            String client2Port = fromClient2.readLine(); 


            // Send the IP and Local Port information to both clients. 
            PrintWriter toClient1 = new PrintWriter(client1.getOutputStream(), true); 
            PrintWriter toClient2 = new PrintWriter(client2.getOutputStream(), true); 

            // Pattern -: IP Address then the Port Number associated with the client.
            
            toClient1.println(client2IP); 
            toClient1.println(client2Port); 

            toClient2.println(client1IP); 
            toClient2.println(client1Port); 

            toClient1.close(); 
            toClient2.close(); 
            client1.close(); 
            client2.close(); 
            
        } catch (IOException e) {
            System.err.println("Error Getting Input: " + e.getMessage());
            System.exit(1); 
        }


    }
}