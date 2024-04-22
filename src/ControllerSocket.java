import java.net.ServerSocket; 
import java.net.Socket; 
import java.io.PrintWriter; 
import java.io.IOException;
/**
 * Controller Socket that will accept connection between the two sockets then proceed to send out 
 * the necessary information needed for both clients to accept and communicate with each other. 
 * @author 
 *  Levi Kuhaulua
 */
public class ControllerSocket {
    public static void main(String[] args) {
        

        // Open up the controller socket for communication. 
        try (ServerSocket connect = new ServerSocket(12345)) {

            Socket client1 = connect.accept(); 
            Socket client2 = connect.accept(); 

            // Get the IP and port number of both clients
            String client1IP = client1.getInetAddress().getHostAddress(); 
            int client1Port = client1.getLocalPort();  

            String client2IP = client2.getInetAddress().getHostAddress(); 
            int client2Port = client2.getLocalPort(); 


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