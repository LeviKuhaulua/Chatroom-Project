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

    
    @Override
    public void run() {
        // TODO Auto-generated method stub
        try{
            Socket client = new Socket("127.0.0.1", 1234);
            outToServer = new PrintWriter(client.getOutputStream(),true);
            inFromServer = new BufferedReader(new InputStreamReader(client.getInputStream()));

            Thread inHandler = new Thread(new InputHandler());
            inHandler.start();

            String inMessage;
            while((inMessage = inFromServer.readLine()) != null){
                System.out.println(inMessage);
            }

        }catch(IOException e){
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
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
                    }
                    else{
                        outToServer.println(message);
                    }
                }

                shutdown(); 
            }catch(IOException e){
                shutdown();

            }
            
        }

    }

    public static void main(String[] args) {
        clientp client = new clientp();
        client.run();

    }
    
    
}
