import java.io.IOException; 
import java.net.UnknownHostException; 
import java.io.BufferedReader; 
import java.io.InputStreamReader;
import java.io.PrintWriter; 
import java.net.Socket; 
/**
 * @author
 *  Levi Kuhaulua
 */
public class Client2 {
    public static void main(String[] args) throws IOException{
        String client1IP = null; 
        int client1Port = 123456;  

        // Communicate with Controller Socket to get IP and Port of other client. 
        try (Socket controllerClient = new Socket("127.0.0.1", 12345)) {

            BufferedReader fromController = new BufferedReader(new InputStreamReader(controllerClient.getInputStream())); 

            client1IP = fromController.readLine(); 
            client1Port = Integer.valueOf(fromController.readLine()); 
            System.out.println("Client 1 IP: " + client1IP);
            System.out.println("Client 1 Port: " + client1Port);


            fromController.close(); 
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
             Socket client1 = new Socket(client1IP, client1Port);
             BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        ) {

            String socketLine = null; 
            String inputLine = null; 
            BufferedReader fromClient1 = new BufferedReader(new InputStreamReader(client1.getInputStream())); 
            PrintWriter toClient1 = new PrintWriter(client1.getOutputStream(), true); 

            // Continously communicate until client (us) disconnects. 
            while ((socketLine = fromClient1.readLine()) != null) {
                System.out.println("Client 2: " + socketLine);
                while ((inputLine = stdIn.readLine()) != null) {
                    if (inputLine.trim().equalsIgnoreCase("bye")) {
                        toClient1.println(inputLine); 
                        break; 
                    } else {
                        toClient1.println(inputLine);
                    }
                }
            }

            // Close connections. 
            fromClient1.close(); 
            toClient1.close(); 
            stdIn.close(); 
            client1.close(); 
        } catch (UnknownHostException e) {
            System.err.println("Unknown Host: " + e.getMessage());
            System.exit(-1); 
        } catch (IOException e) {
            System.err.println("I/O Error: " + e.getMessage());
            System.exit(1); 
        }
    }
}