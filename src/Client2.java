import java.io.IOException; 
import java.io.BufferedReader; 
import java.io.InputStreamReader;
import java.io.PrintWriter; 
import java.net.Socket; 
import java.net.ServerSocket; 
import java.net.UnknownHostException; 
/**
 * @author
 *  Levi Kuhaulua
 */
public class Client2 extends Thread {
    private static boolean bye = false; 
    
    public static void main(String[] args) throws IOException {
        String client1IP = null; 
        int client1Port = 12345;  
        
        // Initialize a server socket to have client 2 to connect to. 
        ServerSocket connSocket = new ServerSocket(23456); 

        // Communicate with Controller Socket to get IP and Port of other client. Replace hostname with target IP. 
        try (Socket controllerClient = new Socket("192.168.0.8", 12345)) {
            
            // Send out it's own information to Controller client. 
            PrintWriter toController = new PrintWriter(controllerClient.getOutputStream(), true); 
            toController.println(controllerClient.getLocalAddress().getHostAddress()); 
            toController.println(connSocket.getLocalPort()); 

            BufferedReader fromController = new BufferedReader(new InputStreamReader(controllerClient.getInputStream())); 

            client1IP = fromController.readLine(); 
            client1Port = Integer.valueOf(fromController.readLine()); 
            System.out.println("Client 1 IP: " + client1IP);
            System.out.println("Client 1 Port: " + client1Port);


            fromController.close(); 
            toController.close(); 
            controllerClient.close(); 
        } catch (UnknownHostException e) {
            System.err.println("Unknown Host: " + e.getMessage());
            System.exit(-1); 
        } catch (IOException e) {
            System.err.println("Unable to connect: " + e.getMessage());
            System.exit(1); 
        }

        // Communicate with the other client. Need to debug / test. 
        try (
             Socket connClient1 = new Socket(client1IP, client1Port); 
             Socket client1 = connSocket.accept(); 
             BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        ) {
 
            String inputLine = null; 
            BufferedReader fromClient1 = new BufferedReader(new InputStreamReader(connClient1.getInputStream())); 
            PrintWriter toClient1 = new PrintWriter(client1.getOutputStream(), true); 

            
            // Handle reading the messages. 
            Thread readMsg = new Thread(() -> {
                String socketLine = null; 
                try {
                    while ((socketLine = fromClient1.readLine()) != null) {
                        if (socketLine.equalsIgnoreCase("Bye")) {
                            System.out.println("Client is leaving the chat.");
                            bye = true; 
                            break;
                        } else {
                            System.out.println("Client 1: " + socketLine);
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Error reading input. " + e.getMessage());
                }
            });
            
            // Start the process of reading and sending out messages. 
            readMsg.start(); 
            while ((inputLine = stdIn.readLine()) != null && !bye) {
                if (inputLine.trim().equalsIgnoreCase("Bye")) {
                    toClient1.println(inputLine);
                    break; 
                } else {
                    toClient1.println(inputLine);
                }
            }
            

            // Close connections. 
            fromClient1.close(); 
            toClient1.close(); 
            stdIn.close(); 
            client1.close(); 
            connClient1.close(); 
        } catch (UnknownHostException e) {
            System.err.println("Unknown Host: " + e.getMessage());
            System.exit(-1); 
        } catch (IOException e) {
            System.err.println("I/O Error: " + e.getMessage());
            System.exit(1); 
        }

        connSocket.close(); 
    }
}