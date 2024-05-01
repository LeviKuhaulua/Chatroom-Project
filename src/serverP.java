
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 
 *  Somret Say & Levi Kuhaulua
 */
public class serverP implements Runnable {

    private ArrayList<handler> connections;
    private ServerSocket server;
    private boolean done;
    private ExecutorService pool;
    private final ChatLogger SERVERLOGGER; 


    public serverP(){
        // Create logger for the Server
        SERVERLOGGER = new ChatLogger("Server", true); 
        connections = new ArrayList<>();
        done = false;
    }

    


    @Override
    public void run(){
        try{
            server = new ServerSocket(12345);
            SERVERLOGGER.getSocketInformation(server); // Log the server information. 
            pool = Executors.newCachedThreadPool();
            while(!done){
                Socket client = server.accept();
                handler connectionHandle = new handler(client);
                //adds all the clients in an array list
                connections.add(connectionHandle);
                pool.execute(connectionHandle);
            }
            
        }catch(IOException e){
            //TODO: handle
           shutdown();
        }
       


    }
    public void broadcast(String message){
        //broadcast message to all the clients
        for(handler ch : connections) {
            if (ch != null){
                ch.sendMessage(message);
            }
        }
    }


    public void shutdown(){
       try{
        done = true;
        if(!server.isClosed()){
            server.close();
        }
        for(handler ch : connections){
            ch.shutdown();
        }
       }catch (IOException e){
            // ignore 
       }
        

    }

    class handler implements Runnable{

        private Socket client;
        private BufferedReader inFromClient;
        private PrintWriter outToClient;
        private String userName;

        public handler(Socket client){
            this.client = client;

        }

        @Override
        public void run(){
            try{
                //this section deals with the clients.
                //first needs to establish buffered and print writer

                outToClient = new PrintWriter(client.getOutputStream(),true);
                inFromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
                outToClient.println("Welcome, please enter your username");
                userName = inFromClient.readLine(); // whatever the client sends that becomes the username
                SERVERLOGGER.getSocketInformation(this.client, this.userName); // Get IP and Port information from client
                broadcast(userName + " has joined the chat!");
                String message;
                while((message = inFromClient.readLine()) != null){
                    if(message.equalsIgnoreCase("/quit")){
                        broadcast(userName + " has left the chat!"); 
                        // Log when users has left the chat. 
                        SERVERLOGGER.logMessage(userName + " has left the chat"); 
                        shutdown(); 
                    }
                    else {
                        broadcast(userName + ": " + message);
                    }
                }



            
            }catch (IOException e){
                //TODO Handle
                shutdown();
            }

        }

        public void sendMessage(String message){
            outToClient.println(message);
        }

        public void shutdown(){
            try{
                inFromClient.close();
                outToClient.close();
                if(!client.isClosed()){
                    client.close();
                }
                // Log when streams have been closed
                SERVERLOGGER.logMessage("Streams are closed for " + handler.this.userName); 
            }
            catch(IOException e){
                //ignore
            }


            
        }
    }

    public static void main(String[] args) {
        serverP server = new serverP();
        server.run();
    }


    
}

