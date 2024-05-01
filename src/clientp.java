import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class clientp implements Runnable {
//Two threads ; 1 from server and then the next for input
    private Socket client;
    private BufferedReader inFromServer;
    private PrintWriter outToServer;
    private boolean done;
    private ChatLogger CLIENTLOGGER; 
    private String username; 

    
    
    @Override
    public void run() {
        // TODO Auto-generated method stub
        try{
            Socket client = new Socket("192.168.0.7", 12345);
            outToServer = new PrintWriter(client.getOutputStream(),true);
            inFromServer = new BufferedReader(new InputStreamReader(client.getInputStream()));

            Thread inHandler = new Thread(new InputHandler());
            inHandler.start();

            String inMessage;
            while((inMessage = inFromServer.readLine()) != null){
                if (inMessage.equalsIgnoreCase("Welcome, please enter your username")) {
                    username = inMessage; 
                    // Create a new log file if user joins the chat
                    CLIENTLOGGER = new ChatLogger(username); 
                } else {
                    // Log message sent by other users. 
                    CLIENTLOGGER.logMessage(inMessage); 
                }
            }

        }catch(IOException e){
            CLIENTLOGGER.messageException("Error: " + e.getMessage()); 
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
            CLIENTLOGGER.logMessage("Streams have been closed for " + username); 
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
                        CLIENTLOGGER.logMessage(username + " has left the chat"); 
                        shutdown(); 
                    }
                    else{
                        outToServer.println(message);
                        CLIENTLOGGER.logMessage(message); // Log out the message sent by the client. 
                    }
                }

            }catch(IOException e){
                CLIENTLOGGER.messageException(e.getMessage() + "\nClosing the streams"); 
                shutdown();

            }
            
        }

    }

    public static void main(String[] args) {
        clientp client = new clientp();
        client.run();

    }
    
    
}
