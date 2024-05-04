import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author 
 *  Somret Say and Levi Kuhaulua
 */
public class clientp implements Runnable {
//Two threads ; 1 from server and then the next for input
    private Socket client;
    private BufferedReader inFromServer;
    private PrintWriter outToServer;
    private boolean done;
    private String username; 
    private ChatLogger CLIENT; 

    
    
    @Override
    public void run() {
        // TODO Auto-generated method stub
        try{
            // Replace with target IP and Port
            Socket client = new Socket("127.0.0.1", 12345);
            outToServer = new PrintWriter(client.getOutputStream(),true);
            inFromServer = new BufferedReader(new InputStreamReader(client.getInputStream()));
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in)); 

            
            Thread inHandler = new Thread(new InputHandler());
            String inMessage;

            // Send username to server then create initial log file with username. 
            System.out.println(inFromServer.readLine());
            username = keyboard.readLine(); 
            outToServer.println(username); 
            CLIENT = new ChatLogger(username); 

            inHandler.start();  // Done later to prevent NullPointerException for the CLIENT ChatLogger

            while((inMessage = inFromServer.readLine()) != null){
                System.out.println(inMessage);
                // Chat format goes - USERNAME: MESSAGE...
                if (inMessage.split(":")[0].equalsIgnoreCase(username)) {
                    // do nothing here to prevent message from being outputted twice
                } else {
                    CLIENT.logMessage(inMessage); 
                }
                
            }

        }catch(IOException e){
            CLIENT.messageException(e.getMessage());
        }finally{
            if(!done){
                shutdown();
            }
        }
    }

    public void shutdown(){
        done= true;
        try{
            inFromServer.close();
            outToServer.close();
            if(client != null && !client.isClosed()){
                client.close();
            } 
        }catch(IOException e){

        }
    }

    class InputHandler implements Runnable {
        @Override
        public void run() {
            try{
                BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
                while(!done){
                    String message = inReader.readLine();
                    if(message.equalsIgnoreCase("/quit")){
                        outToServer.println(message);
                        // Log status on user if they quit
                        CLIENT.logMessage(username + " has left the chat"); 
                        shutdown(); 
                    }
                    else{
                        outToServer.println(message);
                        CLIENT.logMessage(username + ": " + message); // Log out the message sent by the client. 
                    }
                }

            }catch(IOException e){
                CLIENT.messageException(e.getMessage() + "\nClosing the streams"); 
                shutdown();

            }
            
        }

        
    }

    

    public static void main(String[] args) {
        clientp client = new clientp();
        client.run();

    }
    
    
}
