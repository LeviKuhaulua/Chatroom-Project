import java.io.IOException; 
import java.io.BufferedReader; 
import java.io.InputStreamReader; 
import java.io.PrintWriter; 
import java.net.Socket; 
import java.net.ServerSocket; 
import java.net.UnknownHostException; 

/**
 * @Author 
 *  Levi Kuhaulua
 */
public class Client1 {
    public static void main(String[] args) throws IOException{
        String client2IP = null; 
        int client2Port = 12345; 

        // Initialize a server socket to have client 2 to connect to. 
        ServerSocket connSocket = new ServerSocket(23456); 

        // Get the IP and Port number of the client that you want to communicate with. 
        try (Socket controllerClient = new Socket("127.0.0.1", 12345)) {

            // Send out IP address and port number for client 2 to connect. 
            PrintWriter toController = new PrintWriter(controllerClient.getOutputStream(), true); 
            toController.println(connSocket.getInetAddress().getHostAddress()); 
            toController.println(connSocket.getLocalPort()); 

            BufferedReader fromController = new BufferedReader(new InputStreamReader(controllerClient.getInputStream())); 

            client2IP = fromController.readLine(); 
            client2Port = Integer.valueOf(fromController.readLine()); 
            System.out.println("Client 2 IP: " + client2IP);
            System.out.println("Client 2 Port: " + client2Port);

            fromController.close();
            toController.close();  
            controllerClient.close(); 
        } catch (UnknownHostException e) {
            System.err.println("Unknown Hostname: " + e.getMessage());
            System.exit(-1); 
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1); 
        }

        // Communication with other client section. 
        try (
             Socket client2 = connSocket.accept(); 
             BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        ) {
            
            String socketLine = null; 
            String inputLine = null; 
            BufferedReader fromClient2 = new BufferedReader(new InputStreamReader(client2.getInputStream())); 
            PrintWriter toClient2 = new PrintWriter(client2.getOutputStream(), true); 

            // Continously chat with each other until client (us) disconnects. 
            while ((socketLine = fromClient2.readLine()) != null) {
                System.out.println("Client 1: " + socketLine);
                while ((inputLine = stdIn.readLine()) != null) {
                    if (inputLine.trim().equalsIgnoreCase("bye")) {
                        toClient2.println(inputLine); 
                        break; 
                    } else {
                        toClient2.println(inputLine);
                    }
                }
            }

            // Close connections. 
            fromClient2.close(); 
            stdIn.close(); 
            toClient2.close();
            client2.close(); 
            
        } catch (UnknownHostException e) {
            System.err.println("Unknown Hostname: " + e.getMessage());
            System.exit(-1); 
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1); 
        }

        connSocket.close(); 

    }
}